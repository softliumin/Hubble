package cc.sharper.util.interceptor;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 主要进行统计sql执行中的执行时间，生产环境要关闭
 * Created by liumin3 on 2016/1/5.
 */
//@Intercepts(@Signature(type=StatementHandler.class,method = "query",args = {Object.class}))
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})})

//@Intercepts({
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
//})
public class SqlRunTimeInterceptor implements Interceptor
{

    public Object intercept(Invocation invocation) throws Throwable
    {
        try
        {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            MetaObject metaobject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);

            StatementHandler delegate = (StatementHandler) metaobject.getValue("delegate");
            BoundSql boundSql = delegate.getBoundSql();

            String sql = boundSql.getSql();

            System.out.println(sql);//组装之前的sql  不行
            System.out.println();


//            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//            Object parameterObject = null;
//            if (invocation.getArgs().length > 1) {
//                parameterObject = invocation.getArgs()[1];
//            }
//
//            String statementId = mappedStatement.getId();
//            BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
//            Configuration configuration = mappedStatement.getConfiguration();
//            String sql = getSql(boundSql, parameterObject, configuration);
//
//            System.out.println("sql=================="+sql);


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return invocation.proceed();
    }

    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties)
    {

    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration)
    {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
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

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
