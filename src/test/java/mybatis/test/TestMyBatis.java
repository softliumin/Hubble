package mybatis.test;

import cc.sharper.bean.User;
import cc.sharper.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by liumin3 on 2016/1/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestMyBatis
{
    @Resource(name="userServiceImpl")
    private UserService userService;
    @Test
    public void test()
    {
        User user  = new User();
        user.setAge(12);
        user.setNickname("shit");
        user.setAddress("亦庄");
        user.setEmail("11@qq.com");
        user.setId("85242");
        user.setJob("PE");
        user.setTel("15929476892");
        userService.addUser(user);
    }
}
