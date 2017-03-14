package com.weixin.utils.util.messages;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
public interface Consumer {
    public void execute(Message message) throws JMSException;
}
