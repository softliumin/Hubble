package rpc.test;

import org.junit.Test;

import cc.sharper.sword.common.status.Status;
import cc.sharper.sword.common.status.StatusChecker;
import cc.sharper.sword.common.status.support.LoadStatusChecker;
import cc.sharper.sword.common.status.support.MemoryStatusChecker;

/**
 * @author lizhtiao 系统状态检查测试
 */
public class CheckerTest {
	@Test
	public void testMemeory() {
		StatusChecker checker = new MemoryStatusChecker();
		Status status = checker.check();
		System.out.println(status.getLevel() + " " + status.getMessage() + " "
				+ status.getDescription());
	}
	
	@Test
	public void testLoad(){
		StatusChecker checker = new LoadStatusChecker();
		Status status = checker.check();
		System.out.println(status.getLevel() + " " + status.getMessage() + " "
				+ status.getDescription());
	}
}
