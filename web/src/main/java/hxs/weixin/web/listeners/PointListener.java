package hxs.weixin.web.listeners;

import com.weixin.utils.sys.message.MessageQueueName;
import com.weixin.utils.util.messages.ConsumerProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by :Guozhihua
 * Date： 2017/3/8.
 */
@Component
@EnableJms
public class PointListener implements SessionAwareMessageListener<ObjectMessage> {
    //当收到消息后，自动调用该方法,spring配置默认监听器，负责接收消息
    private final Logger logger = LoggerFactory.getLogger(PointListener.class);


    @Override
    @JmsListener(containerFactory= MessageQueueName.JSM_CONNECTION_FACTORY,destination = MessageQueueName.textMessageQueue)
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        try {
            //todo 根据消息类型做响应的业务处理
            ConsumerProxyFactory.getConsumer(objectMessage.getJMSType()).execute(objectMessage);
            if(session.getTransacted()){
                session.commit();
            }
        } catch (JMSException e) {
            if(session.getTransacted()){
                session.rollback();
            }
           logger.error("JMS消息处理出错，"+e.getErrorCode()+"---"+e.getCause());
        }catch (Exception ex){
            if(session.getTransacted()){
                session.rollback();
            }
            logger.error("Listener消息处理出错，"+ex);
        }

    }


}
