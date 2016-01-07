package cc.sharper.sword.registry;

import cc.sharper.sword.common.URL;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by lizhitao on 16-1-4.
 */
public class ZookeeperRegistry implements Registry {
    private static final String ROOT = "/sword";

    private ZkClient zkClient;

    public ZookeeperRegistry() {
        zkClient = new ZkClient("localhost:2181", 1000);
    }


    public void register(URL url) {
        if (!zkClient.exists(ROOT)) {// rpc注册中心根节点
            zkClient.createPersistent(ROOT);
        }

        String servicePath = ROOT + "/" + url.getPath();// 服务接口节点
        String serviceProvidersPath = servicePath + "/providers";// 服务提供者节点
        String serviceConsumersPath = servicePath + "/consumers";// 服务消费者节点
        if (!zkClient.exists(serviceProvidersPath)) {// 接口服务地址是否存在，不存在则创建
            zkClient.createPersistent(serviceProvidersPath, true);
        }

        if (!zkClient.exists(serviceConsumersPath)) {// 接口服务地址是否存在，不存在则创建
            zkClient.createPersistent(serviceConsumersPath, true);
        }

        String providerNode = serviceProvidersPath + "/" + url.getHost() + ":" + url.getPort();
        if (!zkClient.exists(providerNode)) {
            zkClient.createEphemeral(providerNode);
        }
    }

    public void unRegister(URL url) {

    }

    public void destroy() {
        if (null != zkClient) {
            zkClient.close();
        }
    }

    public static void main(String[] args) {
        ZookeeperRegistry registry = new ZookeeperRegistry();
        URL url = new URL();
        url.setHost("loalhost");
        url.setPath("rpc.test.TestService");
        url.setPort(12222);
        registry.register(url);
        try {
            Thread.sleep(10000);
            registry.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
