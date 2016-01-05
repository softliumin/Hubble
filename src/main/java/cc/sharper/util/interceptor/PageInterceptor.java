package cc.sharper.util.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.CollectionWrapper;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

/**
 * Created by liumin3 on 2015/12/24.
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class }) })
public class PageInterceptor implements Interceptor
{
    private String test;

    public Object intercept(Invocation invocation) throws Throwable
    {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
        MetaObject metaObject2 = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 配置文件中SQL语句的ID
        String id = mappedStatement.getId();
        if (id.matches(".+ByParam$"))
        {
            BoundSql boundSql = statementHandler.getBoundSql();
            // 原始的SQL语句
            String sql = boundSql.getSql();

            // 查询总条数的SQL语句
            String countSql = "select count(*) from (" + sql + ")";

            //第一个参数，数据库连接
            Connection connection = (Connection) invocation.getArgs()[0];

            //s
            PreparedStatement countStatement = connection.prepareStatement(countSql);

            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");

            parameterHandler.setParameters(countStatement);

            ResultSet rs = countStatement.executeQuery();

            Map<?, ?> parameter = (Map<?, ?>) boundSql.getParameterObject();
            Page page = (Page) parameter.get("forPage");//
            if (rs.next())
            {
                page.setTotalNumber(rs.getInt(1));
            }

            // 改造后带分页查询的SQL语句
            String pageSql = sql + " limit " + page.getDbIndex() + "," + page.getDbNumber();
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        return invocation.proceed();
    }

    public Object plugin(Object target)
    {
        System.out.println(this.test);
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties)
    {
        this.test = properties.getProperty("test");

    }
}
