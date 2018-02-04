package xchat.controller;

import com.alibaba.fastjson.JSONObject;
import xchat.controller.task.HuangjinDarenAnswer;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by 志华 on 2018/2/4.
 */
@ServerEndpoint("/websocket")
public class WebSocketController {


    private static List<Session> sessions = new ArrayList<>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        boolean getNewQuestion = false;
        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        long sleepTime = 1000L;
        boolean begin = false;
        if (i == 20 || i == 12 || i == 19) {
            begin = true;
        }
        while (sessions.contains(session)) {
            System.out.println("开始获取题目 sessionId size： " + sessions.size());
//            String questins = HuangjinDarenAnswer.getQuestins();
            String questins = null;
            Map<String, String> mes = new HashMap<>();
            if (questins == null || "000000".equals(questins)) {
                mes.clear();
                mes.put("type", "1");
                mes.put("val", "题目快来了");
            } else {
                mes.clear();
                mes.put("type", "2");
                mes.put("val", questins);
                if (!HuangjinDarenAnswer.allQuestions.contains(questins)) {
                    HuangjinDarenAnswer.allQuestions.add(questins);
                    getNewQuestion = true;
                }
            }
            String msg = mes.get("type").toString().concat("@").concat(mes.get("val").toString());
            try {
                if (getNewQuestion) {
                    sendMessage(session, msg);
                    Thread.sleep(12000);
                } else {
                    sendMessage(session, msg);
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                onClose(session);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("触发关闭socket");
        sessions.remove(session);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("有一连接关闭！当前在线人数为" + sessions.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        if ("8888".equals(message)) {
            sessions.remove(session
            );
        } else {

        }

    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }




}
