package cc.sharper.util.interceptor;

import cc.sharper.util.redis.RedisUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 主要进行统计sql执行中的执行时间，生产环境要关闭
 * Created by liumin3 on 2016/1/5.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class SqlRunTimeInterceptor implements Interceptor
{
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Object intercept(Invocation invocation) throws Throwable
    {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs().length > 1)
        {
            parameterObject = invocation.getArgs()[1];
        }
        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = getSql(boundSql, parameterObject, configuration);

        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long end = System.currentTimeMillis();
        long timing = end - start;
        System.out.println();
        System.out.println("耗时：" + timing + " ms" + " - id:" + statementId + " - Sql:" + sql);
        Jedis redis = RedisUtil.getClient();
        redis.zadd("sqltime2",timing,sql);
        System.out.println();
        return result;
    }

    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties)
    {

    }

    //将参数和sql进行组装 返回具体的执行sql
    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration)
    {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " "); //替换掉空白字符
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null)
        {
            for (int i = 0; i < parameterMappings.size(); i++)
            {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT)
                {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName))
                    {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null)
                    {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
                    {
                        value = parameterObject;
                    } else
                    {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    sql = replacePlaceholder(sql, value);
                }
            }
        }
        return sql;
    }

    /**
     * 将占位符？换成真正的参数
     * @param sql
     * @param propertyValue
     * @return
     */
    private String replacePlaceholder(String sql, Object propertyValue)
    {
        String result;
        if (propertyValue != null)
        {
            if (propertyValue instanceof String)
            {
                result = "'" + propertyValue + "'";
            } else if (propertyValue instanceof Date)
            {
                result = "'" + DATE_FORMAT.format(propertyValue) + "'";
            } else
            {
                result = propertyValue.toString();
            }
        } else
        {
            result = "null";
        }
        return sql.replaceFirst("\\?", result);
    }
}
