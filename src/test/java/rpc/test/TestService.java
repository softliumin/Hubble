package rpc.test;

import java.util.HashMap;

/**
 * Created by lizhitao on 16-1-4.
 */
public interface TestService {
    String echo(String msg);

    Integer add(Integer a, Integer b);

    Dazhuang changeDazhuangAge(Dazhuang dazhuang);
}
