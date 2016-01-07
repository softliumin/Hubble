package cc.sharper.util;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

/**
 * Created by liumin3 on 2016/1/7.
 */
public class SqlMapper2
{
    private final MSUtils msUtils;
    private final SqlSession sqlSession;

    public SqlMapper2(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.msUtils = new MSUtils(sqlSession.getConfiguration());
    }

    public int delete(String sql)
    {
        String msId = msUtils.delete(sql);
        return sqlSession.delete(msId);
    }

    private class MSUtils
    {
        private Configuration configuration;
        private LanguageDriver languageDriver;

        private MSUtils(Configuration configuration)
        {
            this.configuration = configuration;
            languageDriver = configuration.getDefaultScriptingLanuageInstance();
        }

        private String newMsId(String sql, SqlCommandType sqlCommandType)
        {
            StringBuilder msIdBuilder = new StringBuilder(sqlCommandType.toString());
            msIdBuilder.append(".").append(sql.hashCode());
            return msIdBuilder.toString();
        }

        private boolean hasMappedStatement(String msId) {
            return configuration.hasStatement(msId, false);
        }

        private void newUpdateMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType)
        {
            MappedStatement ms = new MappedStatement.Builder(configuration, msId, sqlSource, sqlCommandType)
                    .resultMaps(new ArrayList<ResultMap>() {
                        {
                            add(new ResultMap.Builder(configuration, "defaultResultMap", int.class, new ArrayList<ResultMapping>(0)).build());
                        }
                    })
                    .build();
            //缓存
            configuration.addMappedStatement(ms);
        }

        private String delete(String sql)
        {
            String msId = newMsId(sql, SqlCommandType.DELETE);
            if (hasMappedStatement(msId))
            {
                return msId;
            }
            StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
            newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
            return msId;
        }
    }
}
