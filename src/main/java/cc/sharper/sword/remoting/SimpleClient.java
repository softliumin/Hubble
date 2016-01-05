package cc.sharper.sword.remoting;

import cc.sharper.sword.rpc.Invocation;
import cc.sharper.sword.rpc.Result;

import java.io.*;
import java.net.Socket;

/**
 * Created by lizhitao on 16-1-5.
 */
public class SimpleClient {
    private Socket socket;
    private static final int PORT = 12222;

    public SimpleClient() {
        try {
            socket = new Socket("localhost", PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Result remoteCall(Invocation invocation) {
        OutputStream os = null;
        ObjectOutputStream oos = null;
        InputStream is = null;
        ObjectInputStream ois = null;
        try {
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(invocation);

            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            Result result = (Result) ois.readObject();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                oos.close();
                is.close();
                ois.close();
//                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
