package cc.sharper.util.redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by liumin3 on 2015/12/25.
 */
public class RedisTest
{
    public static void main(String[] args)
    {
        Jedis redis = new Jedis("www.demo.cc");
        redis.auth("liumin110");

        redis.set("dalian","lian");

        redis.set("dalian","lian","xx","ex",100);
//        NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
//        XX ：只在键已经存在时，才对键进行设置操作。

        System.out.println(redis.get("dalian"));

        System.out.println(redis.getSet("dalian","shzi"));

        redis.set("huijia","huijia");
        redis.setex("huijia",800,"shzi");

        redis.psetex("huijia",80000,"miao");

        System.out.println(redis.strlen("huijia") + "");

        redis.setrange("huijia",2,"shabi");

        redis.append("huijia","nimei");
        System.out.println(redis.getrange("huijia", 2, 5));

        redis.set("shuzi","100");
        redis.incr("shuzi");

        redis.decr("shuzi");
        redis.decr("shuzi");

        redis.incrBy("shuzi",85);

        redis.decrBy("shuzi",52);
        redis.incrByFloat("shuzi",85.22);
        //==============================================

//        for(int x=1;x<10000;x++)
//        {
//            redis.set(x+"",x+"");
//        }
        System.out.println(redis.info("Memory"));

        redis.sadd("book","java","c++","python");
        redis.sadd("book2","js","node","c");

        redis.sadd("book3","");
        redis.sadd("book4","");
        redis.smove("book","book2","c++");
        redis.srem("book2","c++");

        redis.spop("book2");//随机删除一个元素


        System.out.println(redis.scard("book"));// 获取个数

        Set<String> set =  redis.smembers("book");
        System.out.println(set);


        System.out.println(redis.sismember("book","java"));

        System.out.println(redis.srandmember("book"));//随机返回其中的一个元素

       // redis.sscan("book","");

        System.out.println("sdiff:"+redis.sdiff("book","book2"));
        System.out.println(redis.sdiffstore("book4","book","book2"));

        System.out.println(redis.sunion("book","book2"));//合并

        System.out.println(redis.sunionstore("book3","book","book2"));//合并放到一个其他集合

        System.out.println(redis.sinter("book","book2"));


        //=====================散列（ hash）==============================

        redis.hset("ha","name","liumin");
        redis.hsetnx("ha","age","88");//仅在散列中的给定键尚未有值的情况下，为该键设置值
        redis.hsetnx("ha","address","北京");
        System.out.println(redis.hget("ha","age"));


        System.out.println(redis.hmget("ha","age","name"));//返回一个list

        System.out.println(redis.hgetAll("ha"));//返回所有信息，Map类型
        System.out.println(redis.hkeys("ha"));//返回所有的键  set类型
        System.out.println(redis.hvals("ha"));  //返回所有键的值

        System.out.println(redis.hexists("ha","age"));
        System.out.println(redis.hlen("ha"));//键的个数

       // System.out.println(redis.hdel("ha","age","address"));//删除多个键值

        System.out.println(redis.hincrBy("ha","age",80));

        System.out.println(redis.hincrByFloat("ha","age",80.00));


        //=================有序集合（ sorted set）====================

        System.out.println(redis.zadd("ss",10,"hehe"));
        System.out.println(redis.zadd("ss",11,"yy"));
        System.out.println(redis.zadd("ss",11,"we"));
        System.out.println(redis.zadd("ss",11,"yerey"));
        System.out.println(redis.zadd("ss",11,"rtf"));
        System.out.println(redis.zadd("ss",11,"dfd"));
        System.out.println(redis.zadd("ss",11,"ydfdy"));
        System.out.println(redis.zadd("ss",11,"yy"));
        System.out.println(redis.zadd("ss",113,"43"));

        System.out.println(redis.zadd("ss",131,"2343"));

        System.out.println(redis.zadd("ss",111,"y43rewfdsy"));


        System.out.println(redis.zincrby("ss",85,"hehe"));
        System.out.println(redis.zscore("ss","hehe"));
        System.out.println(redis.zcard("ss"));//元素个数
        System.out.println(redis.zrank("ss","hehe"));//返回排名  从小到大
        System.out.println(redis.zrevrank("ss", "hehe"));  //从大到小

        System.out.println(redis.zcount("ss",95,111));
        System.out.println(redis.zrange("ss",2,4));//从小到大

        System.out.println(redis.zrevrange("ss", 2, 4));//从大到小

        System.out.println(redis.zrangeWithScores("ss", 2, 4));  //到了分数 ？？？？？


        //System.out.println(redis.zrem("ss","we","yy")); //删除成员

        //System.out.println(redis.zremrangeByRank("ss",2,4));
        //System.out.println(redis.z);
        System.out.println(redis.zremrangeByScore("ss",2,200));//移除分值在范围内的成员·


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();



























    }
}
