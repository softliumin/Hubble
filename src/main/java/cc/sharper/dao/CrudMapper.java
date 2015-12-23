package cc.sharper.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liumin3 on 2015/12/23.
 */
public interface CrudMapper<T extends Serializable>
{
    Integer insert(T entity);

    Integer update(T entity);

    Integer delete(T entity);

    T findById(Long id);

    Long count(T entity);

    List<T> queryByParam(T entity);

    List<T> queryAllByParam(T entity);
}
