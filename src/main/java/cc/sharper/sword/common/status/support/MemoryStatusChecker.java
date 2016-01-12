package cc.sharper.sword.common.status.support;

import cc.sharper.sword.common.status.Status;
import cc.sharper.sword.common.status.StatusChecker;

/**
 * 内存检查
 * 
 * @author lizhitao
 */
public class MemoryStatusChecker implements StatusChecker {
	private static final int _1MB = 1024 * 1024;// 1MB的字节数

	public Status check() {
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory();// 剩余内存
		long totalMemory = runtime.totalMemory();// 总共的内存
		long maxMemory = runtime.maxMemory();// 最大内存

		boolean ok = (maxMemory - (totalMemory - freeMemory) > 2 * _1MB);// 剩余内存小于2MB报警
		String msg = "max:" + (maxMemory / _1MB) + "M,total:"
				+ (totalMemory / _1MB) + "M,used:"
				+ ((totalMemory / _1MB) - (freeMemory / _1MB)) + "M,free:"
				+ (freeMemory / _1MB) + "M";
		return new Status(ok ? Status.Level.OK : Status.Level.WARN, msg);
	}
}
