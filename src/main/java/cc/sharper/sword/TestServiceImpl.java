package cc.sharper.sword;

import cc.sharper.sword.TestService;

/**
 * Created by lizhitao on 16-1-4.
 */
public class TestServiceImpl implements TestService{
    public String echo(String msg) {
        return "hello: " + msg;
    }
}
