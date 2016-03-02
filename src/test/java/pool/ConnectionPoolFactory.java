package pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.net.Socket;

/**
 * Created by lizhitao on 16-3-2.
 */
public class ConnectionPoolFactory {
    private GenericObjectPool<Socket> pool;

    public ConnectionPoolFactory(String ip, int port) {
        pool = new GenericObjectPool<Socket>(new ConnectionFactory(ip, port), new GenericObjectPoolConfig());
    }

    public ConnectionPoolFactory(String ip, int port, GenericObjectPoolConfig config) {
        pool = new GenericObjectPool<Socket>(new ConnectionFactory(ip, port), config);
    }


    public Socket getConnection() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void releaseConnection(Socket socket) {
        pool.returnObject(socket);
    }
}
