package cc.sharper.sword.remoting;

import cc.sharper.sword.rpc.Invocation;
import cc.sharper.sword.rpc.Result;
import cc.sharper.sword.rpc.RpcResult;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by lizhitao on 16-1-5.
 */
public class ServerHandler implements Runnable {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        handle();
    }

    public Object handle() {
        InputStream is = null;
        ObjectInputStream ois = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            Invocation call = (Invocation) ois.readObject();
            Class<?> c = Class.forName(call.getInvoker().getInterface().getName() + "Impl");
            Method method = c.getMethod(call.getMethodName(), call.getArgumentsTypes());
            Object result = method.invoke(c.newInstance(), call.getArguments());
            Result rpcResult = new RpcResult(result);

            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(rpcResult);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                ois.close();
                os.close();
                oos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
