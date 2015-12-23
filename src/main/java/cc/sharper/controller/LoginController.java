package cc.sharper.controller;

import cc.sharper.base.BaseController;
import cc.sharper.bean.User;
import cc.sharper.service.UserService;
import cc.sharper.util.Pagination;
import cc.sharper.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liumin3 on 2015/12/22.
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController
{
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String  index()
    {
        return "/index/index";
    }


    @RequestMapping("/login")
    public String  login()
    {
        return "/index/main";
    }



    @RequestMapping("/showUser")
    public String  showUser(Model model,User user)
    {
        try
        {
            Result<Pagination<User>> re = userService.list(user);
            if(re.isSuccess())
            {
                model.addAttribute("users",re.getData());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return "/user/index";
    }





}
