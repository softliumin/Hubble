package thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lizhitao on 16-2-1.
 */
public class DaemonThreadTest {
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new DaemonThreadFactory("Test"));

    public DaemonThreadTest() {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaa");
            }
        });
    }


    public static void main(String[] args) {
        DaemonThreadTest test = new DaemonThreadTest();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DaemonThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        final String nameSuffix = "]";

        public DaemonThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "JMX Monitor " + poolName + " Pool [Thread-";
        }

        public DaemonThreadFactory(String poolName, ThreadGroup threadGroup) {
            group = threadGroup;
            namePrefix = "JMX Monitor " + poolName + " Pool [Thread-";
        }

        public ThreadGroup getThreadGroup() {
            return group;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement() + nameSuffix, 0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
