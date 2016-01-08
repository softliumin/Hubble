package rpc.test;

import cc.sharper.sword.rpc.DefaultInvoker;
import cc.sharper.sword.rpc.proxy.JavassistProxyFacoty;
import cc.sharper.sword.rpc.proxy.JdkProxyFactory;
import cc.sharper.sword.rpc.proxy.ProxyFactory;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lizhitao on 16-1-4. 提供 rpc 服务
 */
public class Test {
    public enum ProxyFactoryEnum {JDK, JAAVASSIST}

    public static void main(String[] args) {
        Test test = new Test();
        TestService testService = test.getProxyFactory(ProxyFactoryEnum.JAAVASSIST).getProxy(new DefaultInvoker<TestService>(TestService.class));
        test.test(testService);
    }

    /*@org.junit.Test
    public void testByJdkProxy() {
        TestService testService = getProxyFactory(ProxyFactoryEnum.JDK).getProxy(new DefaultInvoker<TestService>(TestService.class));
        test(testService);
    }

    @Ignore
    @org.junit.Test
    public void testByJavassistProxy() {
        TestService testService = getProxyFactory(ProxyFactoryEnum.JAAVASSIST).getProxy(new DefaultInvoker<TestService>(TestService.class));
        test(testService);
    }*/

    private ProxyFactory getProxyFactory(ProxyFactoryEnum proxyFactoryEnum) {
        switch (proxyFactoryEnum) {
            case JDK:
                return new JdkProxyFactory();
            case JAAVASSIST:
                return new JavassistProxyFacoty();
        }
        throw new IllegalArgumentException("proxyFactory is not exists");
    }

    private void test(final TestService testService) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
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
