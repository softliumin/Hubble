package cc.sharper.service;

import java.util.List;

/**
 * Created by liumin3 on 2016/1/5.
 */
public interface SqlService
{
    int update(String sql);
    int delete(String sql);
    int insert(String sql);
    List select(String sql);
}
