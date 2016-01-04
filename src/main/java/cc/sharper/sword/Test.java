package cc.sharper.sword;

import cc.sharper.sword.rpc.proxy.DefaultInvoker;
import cc.sharper.sword.rpc.proxy.JdkProxy;

/**
 * Created by lizhitao on 16-1-4. 提供 rpc 服务
 */
public class Test {
	public static void main(String[] args) {
		TestService testService = new JdkProxy<TestService>().getProxy(
				new DefaultInvoker<TestService>(),
				new Class<?>[]{TestService.class});
		System.out.println(testService.echo("bbb"));
	}
}
