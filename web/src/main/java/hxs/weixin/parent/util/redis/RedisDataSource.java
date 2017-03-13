package hxs.weixin.parent.util.redis;

import redis.clients.jedis.ShardedJedis;


public interface RedisDataSource {

    /**
     * 取得redis的客户端
     * @return
     */
    public abstract ShardedJedis getRedisClient();

    /**
     * 将资源返还给pool
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);

    /**
     *出现异常后，将资源返还给pool
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken);


}