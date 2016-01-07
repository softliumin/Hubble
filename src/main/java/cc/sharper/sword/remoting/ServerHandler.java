package cc.sharper.sword.remoting;

import cc.sharper.sword.common.URL;
import cc.sharper.sword.registry.Registry;
import cc.sharper.sword.registry.ZookeeperRegistry;
import cc.sharper.sword.rpc.Invocation;
import cc.sharper.sword.rpc.Result;
import cc.sharper.sword.rpc.RpcResult;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhitao on 16-1-5.
 */
public class ServerHandler implements Runnable {
    private Socket socket;
    private Registry registry = new ZookeeperRegistry();
    private List<URL> urls = new ArrayList<URL>();
    private Map<String, Object> serviceImpls = new HashMap<String, Object>();

    {
        try {
            Class<?> c = Class.forName("rpc.test.TestServiceImpl");
            serviceImpls.put("rpc.test.TestService", c.newInstance());
            URL url = new URL();
            url.setHost("loalhost");
            url.setPath("rpc.test.TestService");
            url.setPort(12222);
            registry.register(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


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
            Object impl = serviceImpls.get(call.getClassName());
            Method method = impl.getClass().getMethod(call.getMethodName(), call.getArgumentsTypes());
            Object result = method.invoke(impl, call.getArguments());
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
