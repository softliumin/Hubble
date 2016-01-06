package rpc.test;

import cc.sharper.sword.rpc.DefaultInvoker;
import cc.sharper.sword.rpc.proxy.JdkProxyFactory;
import com.alibaba.fastjson.JSON;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lizhitao on 16-1-4. 提供 rpc 服务
 */
public class Test {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final TestService testService = new JdkProxyFactory().getProxy(new DefaultInvoker<TestService>(TestService.class));
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(testService.echo("bbb"));
                    System.out.println("1+33的执行结果为：" + testService.add(1, 33));

                    Dazhuang dazhuang = new Dazhuang();
                    dazhuang.setName("小壮");
                    dazhuang.setAge(11);
                    dazhuang.setAddress("北京大兴");

                    dazhuang = testService.changeDazhuangAge(dazhuang);

                    System.out.println(JSON.toJSONString(dazhuang));
                }
            });
        }
        threadPool.shutdown();
    }
}
