package cc.sharper.service.impl;

import cc.sharper.service.SqlService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liumin3 on 2016/1/5.
 */
@Service
public class SqlServiceImpl implements SqlService
{
    @Override
    public List select(String sql)
    {
        return null;
    }

    @Override
    public int update(String sql)
    {
        return 0;
    }

    @Override
    public int delete(String sql)
    {
        return 0;
    }

    @Override
    public int insert(String sql)
    {
        return 0;
    }
}
