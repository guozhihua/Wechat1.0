package hxs.weixin.parent.test.redis;

import hxs.weixin.parent.test.BaseTest;
import com.weixin.utils.util.redis.RedisClientTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by :Guozhihua
 * Date： 2016/12/2.
 */
public class RedisTest extends BaseTest {

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Test
    public  void  testRedis(){
        redisClientTemplate.del("rsat-12313543");
        redisClientTemplate.set("rsat-12313543","2347234234-------563453") ;
        System.out.println("2423");
        System.out.println(redisClientTemplate.get("rsat-12313543"));
    }


}
