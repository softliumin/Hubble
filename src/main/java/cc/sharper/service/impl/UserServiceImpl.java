package cc.sharper.service.impl;

import cc.sharper.bean.User;
import cc.sharper.dao.UserMapper;
import cc.sharper.service.UserService;
import cc.sharper.util.Pagination;
import cc.sharper.util.Result;
import cc.sharper.util.ResultCodeEnum;
import cc.sharper.util.interceptor.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Result<Pagination<User>> listPage(User user)
    {
        Result<Pagination<User>> result = Result.getSuccessResult();

        Pagination<User> p = new Pagination<User>();
        try
        {
            Long count =  mapper.count(user);
            p.setDataCount(count);

            List<User> users =  mapper.queryByParamOfPage(user);
            p.setData(users);
            p.setPageSize(user.getPageSize());
            p.setPageNum(user.getPageNum());
            result.setData(p);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  result;
    }


    @Override
    public Result<List<User>> onPage(User user, Page page)
    {
        Result<List<User>> result = Result.getSuccessResult();
        List<User> li;
        try
        {
            Map<String,Object> parameter = new HashMap<String, Object>();
            parameter.put("page",page);
            parameter.put("user",user);
            li =  mapper.onPage(parameter);
            page.count();
            result.setData(li);
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setEnum(ResultCodeEnum.ERROR);
        }
        return  result;

    }
}
