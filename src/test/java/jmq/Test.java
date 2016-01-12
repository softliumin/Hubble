package jmq;

import org.junit.Before;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;

/**
 * Created by liumin3 on 2016/1/12.
 */
public class Test
{

    @Before
    public void setUp() throws IOException
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.auth("liumin110");
        jedis.flushAll();
        jedis.disconnect();
    }


    @org.junit.Test
    public void test1()
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.auth("liumin110");
        jedis.set("11","11");
        jedis.disconnect();
    }

    @org.junit.Test
    public void test2()
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.auth("liumin110");

        Transaction tx = jedis.multi();
        for (int x=0;x<100;x++)
        {
            tx.set(x+"",x+"");
        }
        tx.exec();
        jedis.disconnect();
    }

    @org.junit.Test
    public void  test3()
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.auth("liumin110");

        Pipeline pipeline =  jedis.pipelined();
        for (int x=0;x<100;x++)
        {
            pipeline.set(x+"",x+"");
        }
        List<Object> results = pipeline.syncAndReturnAll();
        jedis.disconnect();
    }




}
