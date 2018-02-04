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

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;



    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();

    private static Set<String> sessionIds =new HashSet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);//加入set中
        String id = session.getId();
        sessionIds.add(id);
        addOnlineCount();           //在线数加1
        boolean getNewQuestion=false;

        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        long sleepTime=1000L;
        boolean  begin=false;
        if(i==20||i==12||i==19){
            begin=true;
        }
        while (sessionIds.contains(id)){
            System.out.println("开始获取题目 sessionId size： "+ sessionIds.size());
            String questins = HuangjinDarenAnswer.getQuestins();
            Map<String,String> mes=new HashMap<>();
            if(questins==null||"000000".equals(questins)){
                mes.clear();
                mes.put("type","1");
                mes.put("val","题目快来了");
            }else{
                mes.clear();
                mes.put("type","2");
                mes.put("val",questins);
                if(!HuangjinDarenAnswer.allQuestions.contains(questins)){
                    HuangjinDarenAnswer.allQuestions.add(questins);
                    getNewQuestion=true;
                }
            }
            String msg= mes.get("type").toString().concat("@").concat(mes.get("val").toString());
            try {
                if(getNewQuestion){
                    sendMessage(msg);
                    Thread.sleep(12000);
                }else{
                    sendMessage(msg);
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        System.out.println("触发关闭socket");
        sessionIds.remove(this.session
                .getId());
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();//在线数减1
        try {
            this.session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        if("8888".equals(message)){
            sessionIds.remove(this.session
                    .getId());
            webSocketSet.remove(this);  //从set中删除
            subOnlineCount();//在线数减1
        }else{
            //群发消息
        for(WebSocketController item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        }

    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }




}
