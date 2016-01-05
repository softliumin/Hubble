package cc.sharper.sword.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lizhitao on 16-1-5.
 * 命名线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {
    // 递增计数器
    private AtomicInteger counter = new AtomicInteger(0);
    // 线程名称
    private String name;
    // 是否为守护线程
    private boolean daemon;

    public NamedThreadFactory(String name) {
        if(null == name || name.isEmpty()){
            throw new IllegalArgumentException("NamedThreadFactory name is null or empty");
        }
        this.name = name;
    }

    public NamedThreadFactory(String name, boolean daemon) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("NamedThreadFactory name is null or empty");
        }
        this.name = name;
        this.daemon = daemon;
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(name + "_" + counter.incrementAndGet());
        if (daemon) {
            thread.setDaemon(daemon);
        }
        return thread;
    }
}
