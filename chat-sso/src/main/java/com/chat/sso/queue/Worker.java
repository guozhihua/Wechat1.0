package com.chat.sso.queue;


/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public interface Worker {
    public void execute(MsgEvent msgEvent) throws Exception;
}
