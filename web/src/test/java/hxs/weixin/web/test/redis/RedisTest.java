package hxs.weixin.web.test.redis;

import com.weixin.cache.redis.RedisClientTemplate;
import com.weixin.queue.message.MessageQueueName;
import com.weixin.queue.message.MessageType;
import com.weixin.queue.message.QueueMessageUtils;
import hxs.weixin.web.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by :Guozhihua
 * Date： 2016/12/2.
 */
public class RedisTest extends BaseTest {

    @Autowired
    private RedisClientTemplate redisClientTemplate;
//    @Autowired
//    private QueueMessageUtils queueMessageUtils;

    @Test
    public void testRedis() {
        redisClientTemplate.del("rsat-12313543");
        redisClientTemplate.set("rsat-12313543", "2347234234-------563453");
        System.out.println("2423");
        System.out.println(redisClientTemplate.get("rsat-12313543"));
    }
//
//    @Test
//    public void testMsg() {
//        HashMap<String,Object> messageDate=new HashMap<>();
//        messageDate.put("le","234");
//        queueMessageUtils.sendSerializableMessage(MessageQueueName.textMessageQueue,messageDate, MessageType.CLASS_MODULE);
//    }

}
