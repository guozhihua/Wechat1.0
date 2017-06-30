package com.hua.sys;

import com.alibaba.dubbo.container.Main;

/**
 * Created by :Guozhihua
 * Date： 2017/6/29.
 */
public class AppServer {

    public static void main(String[] args) {
        System.out.println("=======================dubbox===========server will start!");
        System.setProperty(Main.SHUTDOWN_HOOK_KEY,Boolean.TRUE.toString());
         Main.main(args);
        System.out.println("=======================dubbox===========server  start success!");
    }

}
