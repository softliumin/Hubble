package cc.sharper.util.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by liumin3 on 2016/1/12.
 */
public class RedisTest2
{
    public static void main(String[] args)
    {
        Jedis redis =  RedisUtil.getClient();
        redis.auth("liumin110");


        //生产者
//        for(int i =0;i<100;i++)
//        {
//            redis.lpush("user",i+"");
//        }


//        for(int i =0;i<100;i++)
//        {
//            redis.lpush("userr", i + "");
//        }


//        for(int i =0;i<100;i++)
//        {
//            System.out.println(redis.rpop("userr"));
//        }

//        for(int x=0;x<9999;x++)
//        {
//            redis.del(x+"");
//        }

        redis.del(9999+"");






        //消费者




    }
}
