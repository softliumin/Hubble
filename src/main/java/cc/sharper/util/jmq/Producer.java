package cc.sharper.util.jmq;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

/**
 * Created by liumin3 on 2016/1/12.
 */

public class Producer
{
    private Jedis jedis;
    private Nest topic;
    private Nest subscriber;

    public Producer(final Jedis jedis, final String topic, String auth)
    {
        this.jedis = jedis;
        this.topic = new Nest("topic:" + topic, jedis);
        this.subscriber = new Nest(this.topic.cat("subscribers").key(), jedis);
        this.jedis.auth(auth);

    }

    public void publish(final String message)
    {
        publish(message, 0);
    }

    protected Integer getNextMessageId()
    {
        final String slastMessageId = topic.get();
        Integer lastMessageId = 0;
        if (slastMessageId != null)
        {
            lastMessageId = Integer.parseInt(slastMessageId);
        }
        lastMessageId++;
        return lastMessageId;
    }

    public void clean()
    {
        Set<Tuple> zrangeWithScores = subscriber.zrangeWithScores(0, 1);
        Tuple next = zrangeWithScores.iterator().next();
        Integer lowest = (int) next.getScore();
        topic.cat("message").cat(lowest).del();
    }

    /**
     * @param message menssage
     * @param seconds expiry time
     */
    public void publish(String message, int seconds)
    {
        List<Object> exec = null;
        Integer lastMessageId = null;
        do
        {
            topic.watch();
            lastMessageId = getNextMessageId();
            Transaction trans = jedis.multi();//开启事务
            String msgKey = topic.cat("message").cat(lastMessageId).key(); //组装key
            trans.set(msgKey, message); //设置消息
            trans.set(topic.key(), lastMessageId.toString()); //设置消息的记录（消息总数等）
            if (seconds > 0)
                trans.expire(msgKey, seconds);//设置超时时间
            exec = trans.exec();//执行，返回的每一条记录的执行结果
        } while (exec == null);
    }
}