package jmq;

import java.io.IOException;

import cc.sharper.util.jmq.Consumer;
import cc.sharper.util.jmq.Producer;
import cc.sharper.util.jmq.Callback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * Created by liumin3 on 2016/1/12.
 */
public class ProducerTest extends Assert
{
    @Before
    public void setUp() throws IOException
    {
        Jedis jedis = new Jedis("www.demo.cc");
        jedis.auth("liumin110");
        jedis.flushAll();
        jedis.disconnect();
    }


    @Test
    public void publishAndConsume()
    {
        Producer p = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
        //Producer p2 = new Producer(new Jedis("www.demo.cc"), "foo2","liumin110");
        Consumer c = new Consumer(new Jedis("www.demo.cc"), "a subscriber", "foo","liumin110");
        //Consumer c2 = new Consumer(new Jedis("www.demo.cc"), "a subscriber2", "foo","liumin110");

        p.publish("hello world!");
//        p.publish("hello world2!");
//        p.publish("hello world3!");
//        p.publish("hello world4!");
        assertEquals("hello world!", c.consume());

//        c2.consume();
//        c.consume();
//        c.consume();
    }

    @Test
    public void publishAndRead()
    {
        Producer p = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
        Consumer c = new Consumer(new Jedis("www.demo.cc"), "a subscriber", "foo","liumin110");

        p.publish("hello world!");
        assertEquals("hello world!", c.read());
        assertEquals("hello world!", c.read());
    }

    //为读取的信息的测试
    @Test
    public void unreadMessages()
    {
        Producer p = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
        Consumer c = new Consumer(new Jedis("www.demo.cc"), "a subscriber", "foo","liumin110");

        assertEquals(0, c.unreadMessages());
        p.publish("hello world!");
        assertEquals(1, c.unreadMessages());
        p.publish("hello world!");



        assertEquals(2, c.unreadMessages());
        c.consume();
        assertEquals(1, c.unreadMessages());
    }


    @Test
    public void raceConditionsWhenPublishing() throws InterruptedException
    {
        Producer slow = new SlowProducer(new Jedis("www.demo.cc"), "foo");
        Consumer c = new Consumer(new Jedis("www.demo.cc"), "a subscriber", "foo","liumin110");

        slow.publish("a");
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                Producer fast = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
                fast.publish("b");
            }
        });
        t.start();
        t.join();

        assertEquals("a", c.consume());
        assertEquals("b", c.consume());
    }

    //消除历史消息
    @Test
    public void eraseOldMessages()
    {
        Producer p = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
        Consumer c = new Consumer(new Jedis("www.demo.cc"), "a subscriber", "foo","liumin110");

        p.publish("a");
        p.publish("b");

        assertEquals("a", c.consume());

        p.clean();

        Consumer nc = new Consumer(new Jedis("www.demo.cc"), "new subscriber","foo","liumin110");

        assertEquals("b", c.consume());
        assertEquals("b", nc.consume());
        assertNull(c.consume());
        assertNull(nc.consume());
    }

    class SlowProducer extends Producer
    {
        private long sleep;

        public SlowProducer(Jedis jedis, String topic)
        {
            this(jedis, topic, 500L);
        }

        public SlowProducer(Jedis jedis, String topic, long sleep)
        {
            super(jedis, topic,"liumin110");
            this.sleep = sleep;
        }

        protected Integer getNextMessageId()
        {
            //在获取id的时候，暂停线程0.5秒
            Integer nextMessageId = super.getNextMessageId();
            sleep(sleep);
            return nextMessageId;
        }
    }

    class SlowConsumer extends Consumer
    {
        private long sleep;

        public SlowConsumer(Jedis jedis, String id, String topic)
        {
            this(jedis, id, topic, 500L);
        }

        public SlowConsumer(Jedis jedis, String id, String topic, long sleep)
        {
            super(jedis, id, topic,"liumin110");
            this.sleep = sleep;
        }


        /**
         * 消费的时候都进行线程的等待，模拟业务
         * @return
         */
        @Override
        public String consume()
        {
            sleep(sleep);
            return super.consume();
        }

        @Override
        public void consume(Callback callback)
        {
            sleep(sleep);
            super.consume(callback);
        }
    }

    @Test
    public void expiredMessages() throws InterruptedException
    {
        Consumer c = new SlowConsumer(new Jedis("www.demo.cc"), "a consumer",
                "foo", 2000L);
        Producer p = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
        p.publish("un mensaje", 1);
        assertNull(c.consume());
    }

    private void sleep(long sleep)
    {
        try
        {
            Thread.sleep(sleep);
        } catch (InterruptedException e)
        {
        }
    }

    @Test
    public void firstMessageExpired() throws InterruptedException
    {
        Consumer c = new SlowConsumer(new Jedis("www.demo.cc"), "a consumer","foo", 2000L);
        Producer p = new Producer(new Jedis("www.demo.cc"), "foo","liumin110");
        p.publish("1", 1);
        p.publish("2", 0);

        assertEquals("2", c.consume());
    }
}