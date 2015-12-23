package cc.sharper.service.impl;

import cc.sharper.bean.User;
import cc.sharper.dao.UserMapper;
import cc.sharper.service.UserService;
import cc.sharper.util.Pagination;
import cc.sharper.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liumin3 on 2015/12/23.
 */
@Service
public class UserServiceImpl implements UserService
{
    @Resource
    private UserMapper mapper;

    public Result<Pagination<User>> list(User user)
    {
        Result<Pagination<User>> result = Result.getSuccessResult();
        Pagination<User> p = new Pagination<User>();
        try
        {
            Long count =   mapper.count(user);
            p.setPageCount(count);
            List<User> users =  mapper.queryAllByParam(user);
            p.setData(users);
            result.setData(p);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  result;
    }
}
