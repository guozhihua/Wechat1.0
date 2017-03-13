package hxs.weixin.parent.sys;

import org.apache.activemq.ScheduledMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.HashMap;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public class CustomScheduledMessage implements MessageCreator {
    //消息数据
    protected HashMap<String,Object> messageData;
    //消息类型
    private MessageType messageType;
    //延迟投送的时间 （秒）
    private int time=1;
    //间隔时间
    private int period = 0;
    //重复投递次数
    private int repeat = 0;


    //定时时间 "0 * * * *"
    private  String cronTime;


    public CustomScheduledMessage(HashMap<String, Object> messageData, MessageType messageType, int time) {
        this.messageData = messageData;
        this.messageType = messageType;
        this.time = time;
    }

    /**
     * 指定cronTime方式进行重复投送
     * @param messageData
     * @param messageType
     * @param time
     * @param period
     * @param repeat
     * @param cronTime
     */
    public CustomScheduledMessage(HashMap<String, Object> messageData, MessageType messageType, int time, int period, int repeat, String cronTime) {
        this.messageData = messageData;
        this.messageType = messageType;
        this.time = time;
        this.period = period;
        this.repeat = repeat;
        this.cronTime = cronTime;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        // TODO Auto-generated method stub
        ObjectMessage messages = session.createObjectMessage();
        messages.setObject(messageData);
        messages.setJMSType(messageType.getTypeCode());
        messages.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time*1000);
        messages.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period*1000);
        messages.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
        if(StringUtils.isNotEmpty(cronTime)){
            messages.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON,cronTime);
        }
        return messages;
    }

}
