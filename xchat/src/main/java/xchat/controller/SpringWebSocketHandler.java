package xchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xchat.workers.HJDRWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2018/2/5.
 */
@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> users= Collections.synchronizedList(new ArrayList<WebSocketSession>());//这个会出现性能问题，最好用Map来存储，key用userid
    private static Logger logger = LoggerFactory.getLogger(SpringWebSocketHandler.class);

    @Autowired
    private HJDRWorker hjdrWorker;




    public SpringWebSocketHandler() {
        logger.info("SpringWebSocketHandler construct ....");
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        //黄金答人注册
        registerHjDRWorker(session);

        System.out.println("connect to the websocket success......当前数量:"+users.size());

    }

    /**
     * 监听黄金答人的题目
     * @param session
     */
    private void registerHjDRWorker(WebSocketSession session){
        hjdrWorker.addSocketSession(session);
        hjdrWorker.startWorker();
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        session.close();
        users.remove(session);
        System.out.println("剩余在线用户"+users.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("receive msg:"+message.toString());
        TextMessage text=new TextMessage("1@你好！题目在准备中了");
        session.sendMessage(text);

    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){session.close();}
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    public boolean supportsPartialMessages() {
        logger.info("supportsPartialMessages");
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        logger.info("sendMessageToUser");
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        logger.info("sendMessageToUsers");
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
