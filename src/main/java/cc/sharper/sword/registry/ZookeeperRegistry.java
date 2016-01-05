package cc.sharper.sword.registry;

import cc.sharper.sword.common.URL;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * Created by lizhitao on 16-1-4.
 */
public class ZookeeperRegistry implements Registry{
    private ZkClient zkClient;

    public ZookeeperRegistry(){
        zkClient = new ZkClient("localhost:2181", 1000);
    }


    public void register(URL url) {
        zkClient.create("/asdfsdfsdf", "111111", CreateMode.PERSISTENT);
    }

    public void unRegister(URL url) {

    }

    public static void main(String[] args) {
        ZookeeperRegistry registry = new ZookeeperRegistry();
        registry.register(null);
    }
}
