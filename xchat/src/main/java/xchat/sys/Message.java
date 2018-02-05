package xchat.sys;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/9/22.
 */
public class Message implements Serializable {

    private  long  messageId ;
    private MessageType messageType;

    //消息标题
    private  String title ;

    //消息内容
    private  String content;

    //状态
    private  int status;

    private Date createrTime;

    /**
     * 执行时间
     */
    private  Date executeTime;


    /**
     * 发送的类型  1-学生 2-老师 3-所有用户
     */
    private int  sendType;

    /**
     * 业务数据
     */
    private Map<String,Object> datas = new HashMap<>();



    public Message(long messageId, String title, String content, int status) {
        this.messageId = messageId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message(long messageId, MessageType messageType, String title, String content, int status) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Message() {
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Message(long messageId) {
        this.messageId = messageId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Date getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }



    public void  addDatas(String key,Object val) {
        this.datas.put(key,val);
    }
    public void  delDatas(String key) {
        this.datas.remove(key);
    }
}
