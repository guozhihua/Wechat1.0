package xchat.listeners;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

;

/**
 * Created by :Guozhihua
 * Date： 2017/5/11.
 **/
@Component("msgListener")
 public class MsgListener implements SmartApplicationListener {

    private  static Logger logger=Logger.getLogger(MsgListener.class);

    private MsgQueue msgQueue=MsgQueue.getInstance();




    /**
     * Determine whether this listener actually supports the given event type.
     *
     * @param eventType
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType==MsgEvent.class;
    }

    /**
     * Determine whether this listener actually supports the given source type.
     *
     * @param sourceType
     */
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType==String.class;
    }

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        MsgEvent msgEvent=(MsgEvent) event;
        try{
//            messageRecordService.add(new MessageRecord(msgEvent.getMsgId(),msgEvent.getMsgType(),"",0));
            msgQueue.putIn(msgEvent);
        }catch (Exception ex){
            logger.error("msgListener 放入消息失败",ex);
        }
    }

    /**
     * Return the order value of this object, with a
     * higher value meaning greater in terms of sorting.
     * <p>Normally starting with 0, with {@code Integer.MAX_VALUE}
     * indicating the greatest value. Same order values will result
     * in arbitrary positions for the affected objects.
     * <p>Higher values can be interpreted as lower priority. As a
     * consequence, the object with the lowest value has highest priority
     * (somewhat analogous to Servlet "load-on-startup" values).
     *
     * @return the order value
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
