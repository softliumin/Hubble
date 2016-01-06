package cc.sharper.sword.remoting;

import cc.sharper.sword.common.util.NamedThreadFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lizhitao on 16-1-5.
 */
public class SimpleServer {
    private ServerSocket serverSocket;
    private ExecutorService threadPool = Executors.newCachedThreadPool(new NamedThreadFactory("sword-threadpool-"));

    public SimpleServer() {
        try {
            serverSocket = new ServerSocket(12222);
            System.out.println("服务器启动成功，监听端口为：" + 12222);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void service() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ServerHandler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
