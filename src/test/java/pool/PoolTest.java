package pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lizhitao on 16-3-2.
 */
public class PoolTest {
    public static void main(String[] args) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(30);

        final ConnectionPoolFactory poolFactory = new ConnectionPoolFactory("127.0.0.1", 8080, config);
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    Socket socket = poolFactory.getConnection();
                    System.out.println(socket);
                }
            }
        });

        /*threadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    poolFactory.releaseConnection();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        threadPool.shutdown();
    }
}
