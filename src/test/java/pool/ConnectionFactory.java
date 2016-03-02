package pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by lizhitao on 16-3-2.
 */
public class ConnectionFactory extends BasePooledObjectFactory<Socket> {
    private InetSocketAddress inetSocketAddress;

    public ConnectionFactory(String ip, int port) {
        inetSocketAddress = new InetSocketAddress(ip, port);
    }

    @Override
    public Socket create() throws Exception {
        Socket socket = new Socket();
        socket.connect(inetSocketAddress);
        return socket;
    }

    @Override
    public PooledObject<Socket> wrap(Socket obj) {
        return new DefaultPooledObject<Socket>(obj);
    }

    @Override
    public void destroyObject(PooledObject<Socket> p) throws Exception {
        p.getObject().close();
    }


    @Override
    public boolean validateObject(PooledObject<Socket> p) {
        Socket socket = p.getObject();
        if (!socket.isConnected()) {
            return false;
        }
        if (socket.isClosed()) {
            return false;
        }
        return true;
    }
}
