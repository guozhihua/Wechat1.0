package xchat.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import xchat.listeners.MsgEvent;
import xchat.sys.MessageType;

import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/6/12.
 */
@Component("queueUtils")
public class QueueUtils {
    @Autowired
    protected ApplicationContext applicationContext;

    public void sendMsg(Map<String,Object> map,MessageType messageType){
            applicationContext.publishEvent(new MsgEvent("msg",map, messageType.getTypeCode()));
    }


}
