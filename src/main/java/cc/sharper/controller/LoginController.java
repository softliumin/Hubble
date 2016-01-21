package cc.sharper.controller;

import cc.sharper.base.BaseController;
import cc.sharper.bean.User;
import cc.sharper.service.SqlService;
import cc.sharper.service.UserService;
import cc.sharper.util.Pagination;
import cc.sharper.util.Result;
import cc.sharper.util.SqlMapper;
import cc.sharper.util.interceptor.Page;
import com.alibaba.fastjson.JSON;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by liumin3 on 2015/12/22.
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController
{
    @Autowired
    private UserService userService;

    @Autowired
    private SqlService sqlService;

    @Autowired
    public SqlSessionTemplate sqlSession;



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

    private final Logger log = LoggerFactory.getLogger(getClass());

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

    @RequestMapping("/showUser2")
    public String showUser2(Model model,User user,Long pageNum)
    {
        try
        {
            if(pageNum == null)
            {
                pageNum =1L;
            }
            user.setPageNum(pageNum);
            user.setAge(18);
            Result<Pagination<User>> result = userService.listPage(user);

            if(result.isSuccess())
            {
                model.addAttribute("users",result.getData());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return "/user/index2";
    }

    @RequestMapping("/showUser2ByAjax")
    public String showUser2ByAjax(Model model,HttpServletRequest request)
    {
        try
        {
            User user = new User();
            user.setPageNum(Long.parseLong(request.getParameter("pageNum")));
            Result<Pagination<User>> result = userService.listPage(user);
            if(result.isSuccess())
            {
                model.addAttribute("users",result.getData());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return "/user/pageUser";
    }

    //分页查询数据
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView doPage(@RequestParam(value = "page", required = false ,defaultValue="1") int currentPage)
    {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        ModelAndView mv = new ModelAndView();
//        List<User> list = userService.showPersonByPage(page);
//        mv.addObject("page",page);
//        mv.setViewName("index");
//        mv.addObject("list", list);
        return mv;
    }


    /**
     *
     * 使用mybatis的拦截器来实现分页功能
     * @return
     */
    @RequestMapping("/showUser3")
    public String showUser3(Model model,HttpServletRequest request)
    {
        try
        {
            log.info("=================info======================");
            log.error("-----------------error----------------------");
            //int sWs = 7/0;


            System.out.println("11111111111111111111111");


            User user= new User();
//            user.setAge(16);
//            user.setAddress("北京大兴区");
            Page page = new Page();
            String pagreNum =  request.getParameter("pageNum");
            if (pagreNum == null)
            {
                pagreNum = "1";
            }

            page.setCurrentPage(Integer.parseInt(pagreNum));
            page.setPageNumber(5);

            Result<List<User>> result = userService.onPage(user, page);
            if(result.isSuccess())
            {
                model.addAttribute("list",result.getData());
                model.addAttribute("page",page);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return  "/user/index3";
    }

    /**
     * 直接使用sql字符串来对数据库的操作
     * @return
     */
    @RequestMapping("/stringSql")
    public String useSqlString(HttpServletRequest request)
    {
        try
        {
//            String type = request.getParameter("sqlType");
//            String sql = request.getParameter("sql");

//             String sql = "update user set age=20 where age=29;";
//
//            String sql2 = "select age from user where age = 20;";
//
//            SqlMapper sqlMapper = new SqlMapper(sqlSession);
//
//            int num =  sqlMapper.update(sql);




            User user  = new User();
            user.setAge(12);
            user.setNickname("shabi");
            user.setAddress("亦庄");
            user.setEmail("11wewe@qq.com");
            user.setId(UUID.randomUUID().toString());
            user.setId("3d2dffab-05ef-4062-af3f-52253d34b976");
            user.setJob("PE");
            user.setTel("18670656767");
            userService.addUser(user);
            //sqlMapper.selectList(sql2);
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error("直接使用sql字符串来对数据库的操作出现异常",e);
        }
        return  "/user/usrSqlString";
    }






}
