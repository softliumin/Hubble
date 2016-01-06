package rpc.test;

import cc.sharper.sword.remoting.SimpleServer;

/**
 * Created by lizhitao on 16-1-5.
 */
public class TestServer {
    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.service();
    }
}
