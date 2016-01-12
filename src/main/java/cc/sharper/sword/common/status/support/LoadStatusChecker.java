package cc.sharper.sword.common.status.support;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import cc.sharper.sword.common.status.Status;
import cc.sharper.sword.common.status.StatusChecker;

/**
 * 系统负载状态检查
 * 
 * @author lizhitao
 */
public class LoadStatusChecker implements StatusChecker {

	@Override
	public Status check() {
		OperatingSystemMXBean systemMXBean = ManagementFactory.getOperatingSystemMXBean();
		
		double load = systemMXBean.getSystemLoadAverage();
		int cpu = systemMXBean.getAvailableProcessors();
		
		return new Status(load < 0 ? Status.Level.UNKNOWN : (load < cpu ? Status.Level.OK : Status.Level.WARN), (load < 0 ? "" : "load:" + load + ",") + "cpu:" + cpu);
	}

}
