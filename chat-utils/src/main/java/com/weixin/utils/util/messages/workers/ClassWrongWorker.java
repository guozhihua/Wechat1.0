package com.weixin.utils.util.messages.workers;

import com.alibaba.fastjson.JSON;
import com.weixin.utils.util.messages.Consumer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
@Component("classWrongWorker")
@Scope("prototype")
public class ClassWrongWorker implements Consumer {
    @Override
    public void execute(Message message) throws JMSException {
        System.out.println("testWorker2消息类型  ："+message.getJMSType()+ JSON.toJSONString(((ObjectMessage)message).getObject()));
    }
}