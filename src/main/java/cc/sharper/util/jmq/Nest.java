package cc.sharper.util.jmq;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.TransactionBlock;
import redis.clients.jedis.Tuple;

/**
 * Created by liumin3 on 2016/1/12.
 */
public class Nest
{
    private static final String COLON = ":";
    private StringBuilder sb;
    private String key;
    private Jedis jedis;

    public Nest(String key, Jedis jedis)
    {
        this.key = key;
        this.jedis = jedis;
    }

    public String key()
    {
        prefix();
        String generatedKey = sb.toString();
        generatedKey = generatedKey.substring(0, generatedKey.length() - 1);
        sb = null;
        return generatedKey;
    }

    //增加前缀
    private void prefix()
    {
        if (sb == null)
        {
            sb = new StringBuilder();
            sb.append(key);
            sb.append(COLON);
        }
    }

    public Nest cat(int id)
    {
        prefix();
        sb.append(id);
        sb.append(COLON);
        return this;
    }

    public Nest cat(String field)
    {
        prefix();
        sb.append(field);
        sb.append(COLON);
        return this;
    }

    // Redis Common Operations
    public String set(String value)
    {
        Jedis jedis = getResource();
        String set = jedis.set(key(), value);
        returnResource(jedis);
        return set;
    }

    public String get()
    {
        Jedis jedis = getResource();
        String string = jedis.get(key());
        returnResource(jedis);
        return string;
    }

    public Long incr()
    {
        Jedis jedis = getResource();
        Long incr = jedis.incr(key());
        returnResource(jedis);
        return incr;
    }

    public List<Object> multi(TransactionBlock transaction)
    {
        Jedis jedis = getResource();
        List<Object> multi = jedis.multi(transaction);
        returnResource(jedis);
        return multi;
    }

    public Long del()
    {
        Jedis jedis = getResource();
        Long del = jedis.del(key());
        returnResource(jedis);
        return del;
    }

    public boolean exists()
    {
        Jedis jedis = getResource();
        Boolean exists = jedis.exists(key());
        returnResource(jedis);
        return exists;
    }

    // Redis Hash Operations
    public String hmset(Map<String, String> hash)
    {
        Jedis jedis = getResource();
        String hmset = jedis.hmset(key(), hash);
        returnResource(jedis);
        return hmset;
    }

    public Map<String, String> hgetAll()
    {
        Jedis jedis = getResource();
        Map<String, String> hgetAll = jedis.hgetAll(key());
        returnResource(jedis);
        return hgetAll;
    }

    public String hget(String field)
    {
        Jedis jedis = getResource();
        String value = jedis.hget(key(), field);
        returnResource(jedis);
        return value;
    }

    public Long hdel(String field)
    {
        Jedis jedis = getResource();
        Long hdel = jedis.hdel(key(), field);
        returnResource(jedis);
        return hdel;
    }

    public Long hlen()
    {
        Jedis jedis = getResource();
        Long hlen = jedis.hlen(key());
        returnResource(jedis);
        return hlen;
    }

    public Set<String> hkeys()
    {
        Jedis jedis = getResource();
        Set<String> hkeys = jedis.hkeys(key());
        returnResource(jedis);
        return hkeys;
    }

    // Redis Set Operations
    public Long sadd(String member)
    {
        Jedis jedis = getResource();
        Long reply = jedis.sadd(key(), member);
        returnResource(jedis);
        return reply;
    }

    public Long srem(String member)
    {
        Jedis jedis = getResource();
        Long reply = jedis.srem(key(), member);
        returnResource(jedis);
        return reply;
    }

    public Set<String> smembers()
    {
        Jedis jedis = getResource();
        Set<String> members = jedis.smembers(key());
        returnResource(jedis);
        return members;
    }

    // Redis List Operations
    public Long rpush(String string)
    {
        Jedis jedis = getResource();
        Long rpush = jedis.rpush(key(), string);
        returnResource(jedis);
        return rpush;
    }

    public String lset(int index, String value)
    {
        Jedis jedis = getResource();
        String lset = jedis.lset(key(), index, value);
        returnResource(jedis);
        return lset;
    }

    public String lindex(int index)
    {
        Jedis jedis = getResource();
        String lindex = jedis.lindex(key(), index);
        returnResource(jedis);
        return lindex;
    }

    public Long llen()
    {
        Jedis jedis = getResource();
        Long llen = jedis.llen(key());
        returnResource(jedis);
        return llen;
    }

    public Long lrem(int count, String value)
    {
        Jedis jedis = getResource();
        Long lrem = jedis.lrem(key(), count, value);
        returnResource(jedis);
        return lrem;
    }

    public List<String> lrange(int start, int end)
    {
        Jedis jedis = getResource();
        List<String> lrange = jedis.lrange(key(), start, end);
        returnResource(jedis);
        return lrange;
    }

    // Redis SortedSet Operations
    public Set<String> zrange(int start, int end)
    {
        Jedis jedis = getResource();
        Set<String> zrange = jedis.zrange(key(), start, end);
        returnResource(jedis);
        return zrange;
    }

    public Set<Tuple> zrangeByScoreWithScores(double min, double max,
                                              int offset, int count)
    {
        Jedis jedis = getResource();
        Set<Tuple> zrange = jedis.zrangeByScoreWithScores(key(), min, max,
                offset, count);
        returnResource(jedis);
        return zrange;
    }

    public Set<Tuple> zrangeWithScores(int start, int end)
    {
        Jedis jedis = getResource();
        Set<Tuple> zrange = jedis.zrangeWithScores(key(), start, end);
        returnResource(jedis);
        return zrange;
    }

    public Long zadd(float score, String member)
    {
        Jedis jedis = getResource();
        Long zadd = jedis.zadd(key(), score, member);
        returnResource(jedis);
        return zadd;
    }

    public Long zcard()
    {
        Jedis jedis = getResource();
        Long zadd = jedis.zcard(key());
        returnResource(jedis);
        return zadd;
    }

    public Double zscore(String member)
    {
        Jedis jedis = getResource();
        Double zadd = jedis.zscore(key(), member);
        returnResource(jedis);
        return zadd;
    }

    public Double zincrby(double score, String member)
    {
        Jedis jedis = getResource();
        Double zincrby = jedis.zincrby(key(), score, member);
        returnResource(jedis);
        return zincrby;
    }

    private void returnResource(final Jedis jedis)
    {
    }

    private Jedis getResource()
    {
        return jedis;
    }

    public String watch()
    {
        Jedis jedis = getResource();
        String result = jedis.watch(key());
        returnResource(jedis);
        return result;
    }

    public Long publish(String message)
    {
        Jedis jedis = getResource();
        Long result = jedis.publish(key(), message);
        returnResource(jedis);
        return result;
    }
}