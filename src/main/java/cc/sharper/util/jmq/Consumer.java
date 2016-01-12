package cc.sharper.util.jmq;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Created by liumin3 on 2016/1/12.
 */


public class Consumer
{
    private Nest topic;
    private Nest subscriber;
    private String id;



    public Consumer(final Jedis jedis, final String id, final String topic,String auth)
    {
        this.topic = new Nest("topic:" + topic, jedis);
        jedis.auth(auth);
        this.subscriber = new Nest(this.topic.cat("subscriberss").key(), jedis);
        this.id = id;
    }

    private void waitForMessages()
    {
        try
        {
            Thread.sleep(500);
        } catch (InterruptedException e)
        {
        }
    }

    public void consume(Callback callback)
    {
        while (true)
        {
            String message = readUntilEnd();
            if (message != null)
                callback.onMessage(message);
            else
                waitForMessages();
        }
    }

    public String consume()
    {
        return readUntilEnd();
    }

    private String readUntilEnd()
    {
        while (unreadMessages() > 0)
        {
            String message = read();
            goNext();
            if (message != null)
                return message;
        }

        return null;
    }

    private void goNext()
    {
        subscriber.zincrby(1, id);
    }

    private int getLastReadMessage()
    {
        Double lastMessageRead = subscriber.zscore(id);
        if (lastMessageRead == null)
        {
            Set<Tuple> zrangeWithScores = subscriber.zrangeWithScores(0, 1);
            if (zrangeWithScores.iterator().hasNext())
            {
                Tuple next = zrangeWithScores.iterator().next();
                Integer lowest = (int) next.getScore() - 1;
                subscriber.zadd(lowest, id);
                return lowest;
            } else
            {
                return 0;
            }
        }
        return lastMessageRead.intValue();
    }

    private int getTopicSize()
    {
        String stopicSize = topic.get();
        int topicSize = 0;
        if (stopicSize != null)
        {
            topicSize = Integer.valueOf(stopicSize);
        }
        return topicSize;
    }

    public String read()
    {
        int lastReadMessage = getLastReadMessage();
        return topic.cat("message").cat(lastReadMessage + 1).get();
    }

    public int unreadMessages()
    {
        return getTopicSize() - getLastReadMessage();
    }
}