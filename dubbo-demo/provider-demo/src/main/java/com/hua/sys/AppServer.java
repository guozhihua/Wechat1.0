package com.hua.sys;

/**
 * Created by :Guozhihua
 * Date： 2017/6/29.
 */
public class AppServer {

    public static void main(String[] args) {
        System.out.println("=======================dubbox===========server will start!");
        com.alibaba.dubbo.container.Main.main(args);
        System.out.println("=======================dubbox===========server  start success!");
    }

}
