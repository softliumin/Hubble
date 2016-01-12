package shutdownHookTest;

import org.junit.Test;

/**
 * Created by lizhitao on 16-1-7.
 */
public class RuntimeShutdown {
    @Test
    public void testShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("I am dead!!!");
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
