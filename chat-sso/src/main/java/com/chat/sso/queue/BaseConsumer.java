package com.chat.sso.queue;

/**
 * Created by :Guozhihua
 * Date： 2017/5/11.
 */
public  abstract  class BaseConsumer implements Runnable {
    protected MsgQueue msgQueue =null;

    public BaseConsumer(MsgQueue msgQueue) {
        this.msgQueue = msgQueue;
    }
}
