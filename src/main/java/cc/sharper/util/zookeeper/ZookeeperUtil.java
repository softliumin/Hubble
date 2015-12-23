package cc.sharper.util.zookeeper;

import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * Created by liumin3 on 2015/12/23.
 */
public class ZookeeperUtil
{
    public ZkClient getzkClient()
    {
        ZkClient zk = new ZkClient("www.demo.cc",2181);

        return zk;
    }

    public static void main(String[] args)
    {
        ZkClient zk = new ZkClient("www.demo.cc",2181);
        List<String> li =  zk.getChildren("/");
        for(String str :li)
        {
            System.out.println(str);
        }
    }
}
