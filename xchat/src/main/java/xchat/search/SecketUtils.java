package xchat.search;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.sys.SessionBucket;

import java.util.Collection;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2018/2/9.
 */
public class SecketUtils {

     public static void sendMsgToAll(String messageType,String content){
         try{
             TextMessage textMessage=new TextMessage(messageType.concat("@").concat(content));
             Map<String, WebSocketSession> allsessionMap = SessionBucket.getInstance().getAllsessionMap();
             Collection<WebSocketSession> values = allsessionMap.values();
             for (WebSocketSession value : values) {
                 value.sendMessage(textMessage);
             }
         }catch (Exception ex){
             System.out.println("SecketUtils 发消息出错了！"+ex.getCause());
         }

     }

}
