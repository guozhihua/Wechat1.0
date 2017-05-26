package com.chat.sso.queue;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/5/11.
 */
public class MsgEvent extends ApplicationEvent {
    private long msgId;
    private Map<String,Object> datas;
    /**
     * 业务更加消息类型不同做不同处理
     * 1-发送家长短信 2-其他待加
     */
    private  String msgType;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MsgEvent(Object source) {
        super(source);
    }

    public MsgEvent(Object source, Map<String, Object> datas, String msgType) {
        super(source);
        this.msgId = System.currentTimeMillis();
        this.datas = datas;
        this.msgType = msgType;
    }

    public long getMsgId() {
        return msgId;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public String getMsgType() {
        return msgType;
    }
}
