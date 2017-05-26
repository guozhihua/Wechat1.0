package com.chat.sso.queue;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by :Guozhihua
 * Date： 2017/5/12.
 */

public class MsgConsumerIniter {
    private  static  final int consumerSize=5;

    public static ExecutorService executorService=null;

    public static  boolean flag =true;

    /**
     * 初始化消费者线程
     */
    public void initConsumer(){
        System.out.printf("consuemer init...");
        executorService= Executors.newFixedThreadPool(consumerSize);
       for(int i=0;i<consumerSize;i++){
           BaseConsumer baseConsumer= new MsgEventConsumer(MsgQueue.getInstance(),"msgConsumer["+i+"]");
           executorService.submit(baseConsumer);
       }
    }
    /**
     * 容器销毁时，销毁线程池
     */
    public static void  shutdown(){
        if(executorService!=null&&!executorService.isShutdown()){
            flag=false;
            executorService.shutdownNow();
            System.out.println("线程池当前是否关闭："+executorService.isShutdown());
        }
    }

}
