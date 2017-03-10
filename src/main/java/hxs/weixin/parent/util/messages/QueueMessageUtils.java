package hxs.weixin.parent.util.messages;


import hxs.weixin.parent.sys.CustomScheduledMessage;
import hxs.weixin.parent.sys.MessageComm;
import hxs.weixin.parent.sys.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 * Created by :Guozhihua
 * Date： 2017/3/8.
 */

@Component("messageUtils")
public class QueueMessageUtils {

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     *  发送消息到指定的队列(目标)
     * @Title: send
     * @param queueName 队列名称
     * @param message 消息内容
     * @return void
     * @author gzh
     * @date 2016年11月30日 上午11:57:43
     * @throws
     */
    public void sendSerializableMessage(final  String queueName,final  HashMap<String,Object> message,final MessageType type){
//        jmsTemplate.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        jmsTemplate.send(queueName, new MessageComm(message,type));
    }

    /**
     * 延迟或者定时投送任务
     * @param queueName
     * @param  message
     */
    public void sendScheduledMsg(final String queueName,final CustomScheduledMessage message ){
        jmsTemplate.send(queueName, message);
    }


}
