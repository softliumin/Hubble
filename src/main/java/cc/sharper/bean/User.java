package cc.sharper.bean;

import cc.sharper.base.BaseBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by liumin3 on 2015/12/23.
 */
@ToString
@Getter
@Setter
public class User extends BaseBean
{
    private String id;
    private String nickname;
    private int age;
    private String address;
    private String email;
    private String tel;
    private String job;
    private String password;





}
