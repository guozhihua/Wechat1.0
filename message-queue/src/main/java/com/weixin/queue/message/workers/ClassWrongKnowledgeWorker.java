package com.weixin.queue.message.workers;

import com.alibaba.fastjson.JSON;
import com.weixin.queue.message.Consumer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
@Component("classWrongKnowledgeWorker")
@Scope("prototype")
public class ClassWrongKnowledgeWorker implements Consumer {
    @Override
    public void execute(Message message) throws JMSException {
        System.out.println("testWorker2消息类型  ："+message.getJMSType()+ JSON.toJSONString(((ObjectMessage)message).getObject()));
    }
}
