package cc.sharper.sword.test;

/**
 * Created by lizhitao on 16-1-5.
 */
public class TestServiceImpl implements TestService{
    public String echo(String msg) {
        return "hello,我是服务端返回的结果：" + msg;
    }

    public Integer add(Integer a, Integer b) {
        return a + b;
    }
}
