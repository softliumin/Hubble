package jmq;

import java.io.IOException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import cc.sharper.util.jmq.Consumer;
import cc.sharper.util.jmq.Producer;

/**
 * Created by liumin3 on 2016/1/12.
 */
public class PublishConsumeBenchmarkTest extends Assert
{
    @Before
    public void setUp() throws IOException
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.flushAll();
        jedis.disconnect();

    }


    @Test
    public void publish()
    {
        final String topic = "foo";
        final String message = "hello world!";
        final int MESSAGES = 10000;
        Producer p = new Producer(new Jedis("www.demo.cc"), topic,"liumin110");

        long start = Calendar.getInstance().getTimeInMillis();
        for (int n = 0; n < MESSAGES; n++)
        {
            p.publish(message);
        }
        long elapsed = Calendar.getInstance().getTimeInMillis() - start;
        System.out.println(((1000 * MESSAGES) / elapsed) + " ops");
    }

    @Test
    public void consume()
    {
        final String topic = "foo";
        final String message = "hello world!";
        final int MESSAGES = 10000;
        Producer p = new Producer(new Jedis("www.demo.cc"), topic,"liumin110");
        Consumer c = new Consumer(new Jedis("www.demo.cc"), "consumer", topic,"liumin110");
        for (int n = 0; n < MESSAGES; n++)
        {
            p.publish(message);
        }

        long start = Calendar.getInstance().getTimeInMillis();
        String m = null;
        do
        {
            m = c.consume();
        } while (m != null);
        long elapsed = Calendar.getInstance().getTimeInMillis() - start;

        System.out.println(((1000 * MESSAGES) / elapsed) + " ops");
    }
}
