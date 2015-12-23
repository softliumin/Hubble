package cc.sharper.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;

/**
 * Created by liumin3 on 2015/12/22.
 */
public class RedisUtil
{

    public Jedis getClient()
    {
        Jedis redis = new Jedis("www.demo.cc");
        redis.auth("liumin110");
        return  redis;
    }

    public  boolean checkStatus(Jedis redis)
    {
        if(redis.ping().equals("PONG"))
        {
            return  true;
        }else
        {
            return  false;
        }
    }

    public  String test(Jedis redis)
    {
        return "";
    }



    public static void main(String[] args)
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.auth("liumin110");
        String ss  =  jedis.ping();

        JedisMonitor monitor = new JedisMonitor()
        {
            @Override
            public void onCommand(String s)
            {
                //这里是自己的业务逻辑
                System.out.println(s);
            }
        };
        while(true)
        {
            jedis.monitor(monitor);
        }



//        System.out.println(ss);
//        System.out.println( jedis.clientList());
    }
}
