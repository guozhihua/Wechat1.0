package xchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xchat.controller.task.HuangjinDarenAnswer;
import xchat.sys.SessionBucket;
import xchat.workers.HJDRWorker;

import java.io.IOException;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2018/2/5.
 */
@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(SpringWebSocketHandler.class);

    @Autowired
    private HJDRWorker hjdrWorker;

    private SessionBucket sessionBucket = SessionBucket.getInstance();


    public SpringWebSocketHandler() {
        logger.info("SpringWebSocketHandler construct ....");
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (sessionBucket.getPepleNum() == 0) {
            HJDRWorker.start = false;
        }
        Map<String, Object> attributes = session.getAttributes();
        if (attributes != null && attributes.size() > 0 && attributes.containsKey("token")) {
            sessionBucket.addSession(attributes.get("token").toString(), session);
            //黄金答人注册
            try{
                registerHjDRWorker(session);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            TextMessage textMessage = new TextMessage(HuangjinDarenAnswer.getQuestion ? "3@1" : "3@0");
            session.sendMessage(textMessage);
            logger.info("connect to the websocket success......当前数量:" + sessionBucket.getPepleNum());
        } else {
            session.close();
        }

    }

    /**
     * 监听黄金答人的题目
     *
     * @param session
     */
    private void registerHjDRWorker(WebSocketSession session) {
        hjdrWorker.startWorker();
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        sessionBucket.removeSessionId(session.getId());
        System.out.println("剩余在线用户" + sessionBucket.getPepleNum());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("receive msg:" + message.toString());
        TextMessage text = new TextMessage("1@你好！题目在准备中了");
        session.sendMessage(text);

    }

    /**
     * 发生错误关闭socket
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("websocket connection closed......");
        sessionBucket.removeSessionId(session.getId());
        exception.printStackTrace();
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
        for (WebSocketSession user : sessionBucket.getAllsessionMap().values()) {
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
        for (WebSocketSession user : sessionBucket.getAllsessionMap().values()) {
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
