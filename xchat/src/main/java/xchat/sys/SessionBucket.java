package xchat.sys;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by :Guozhihua
 * Date： 2018/2/5.
 */
public class SessionBucket implements Serializable {


    //todo 单利模式要考虑集群怎么处理？
 private  static SessionBucket  instance;
    private  static  Object lock =new Object();

    public  static SessionBucket getInstance() {
        if (instance == null) {
            synchronized (lock){
                if(instance==null){
                    instance = new SessionBucket();
                }
            }
        }
        return instance;
    }

    private static final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private static final Map<String, String> sessionIdList = new ConcurrentHashMap<>();

    /**
     * 添加session 关系
     *
     * @param uuid
     * @param session
     */
    public void addSession(String uuid, WebSocketSession session) {
        removeSessionUUId(uuid);
        sessionIdList.put(session.getId(), uuid);
        sessionMap.put(uuid, session);
    }

    /**
     * 根据uuid 获取删除session
     *
     * @param uuid
     */
    public void removeSessionUUId(String uuid) {
        WebSocketSession session = sessionMap.get(uuid);
        if (session != null) {
             if(session.isOpen()){
                 try {
                     session.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
            sessionIdList.remove(session.getId());
            sessionMap.remove(uuid);
        }
    }

    /**
     * 根据userID 删除session
     *
     * @param sessionId
     */
    public void removeSessionId(String sessionId) {
        String uuid = sessionIdList.get(sessionId);
        if (StringUtils.isNotBlank(uuid)) {
            sessionIdList.remove(sessionId);
        }
        WebSocketSession session = sessionMap.get(uuid);
        if(session!=null&&session.isOpen()){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sessionMap.remove(uuid);
        }
    }

    /**
     * 关闭session
     *
     * @param sessionId
     */
    public void closeSession(String sessionId) {
        String uuid = sessionIdList.get(sessionId);
        if (StringUtils.isNotBlank(uuid)) {
            WebSocketSession session = sessionMap.get(uuid);
            try {
                if (session != null) session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sessionIdList.remove(sessionId);
        }

    }
    /**
     * 获取总人数
     * @return
     */
    public  int getPepleNum() {
        return sessionIdList.size();
    }

   public WebSocketSession getSession(String uuid){
       return  sessionMap.get(uuid);
   }

    public Map<String,WebSocketSession> getAllsessionMap(){
        return  sessionMap;
    }

}
