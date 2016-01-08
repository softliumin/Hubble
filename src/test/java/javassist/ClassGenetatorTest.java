package javassist;

import cc.sharper.sword.common.bytecode.ClassGenerator;
import org.junit.Test;
import rpc.test.TestService;

/**
 * Created by lizhitao on 16-1-8.
 */
public class ClassGenetatorTest {
    /**
     * 测试使用javassist生成接口代理对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void testGenerator() throws IllegalAccessException, InstantiationException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ClassGenerator classGenerator = ClassGenerator.newInstance(classLoader);
        classGenerator.setClassName("dynamic.TestServiceImpl").addInterface(TestService.class).addMethod("public String echo(String msg){return \"hello:\" + msg;}");
        Class<?> generatorClazz = classGenerator.toClass();
        TestService service = (TestService) generatorClazz.newInstance();
        System.out.println(service.echo("aaaa"));
        classGenerator.release();
    }
}
