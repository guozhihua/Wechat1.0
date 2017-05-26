package com.chat.sso.listener;


import com.chat.sso.queue.MsgConsumerIniter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by :Guozhihua
 * Date： 2017/5/12.
 */
public class MsgPoolDestroyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("MsgPoolDestroyListener  容器即将销毁，关闭消息队列线程池");
        MsgConsumerIniter.shutdown();
    }
}
