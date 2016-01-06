package cc.sharper.service;

import cc.sharper.bean.User;
import cc.sharper.util.Pagination;
import cc.sharper.util.Result;
import cc.sharper.util.interceptor.Page;

import java.util.List;

/**
 * Created by liumin3 on 2015/12/23.
 */
public interface UserService
{
    @SuppressWarnings("unchecked")
    Result<Pagination<User>> list(User user);
    @SuppressWarnings("unchecked")
    Result<Pagination<User>> listPage(User user);


    @SuppressWarnings("unchecked")
    Result<List<User>>  onPage(User user,Page page);
}
