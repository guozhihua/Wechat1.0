package com.chat.sso.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * 消息队里
 * Created by :Guozhihua
 * Date： 2017/5/11.
 */
public class MsgQueue {
    private static BlockingQueue<MsgEvent> queue =null;
    public static class MsgHoder{
        private static  MsgQueue msgQueue= new MsgQueue();
        static {
            msgQueue.queue=new LinkedBlockingDeque(10000);
        }
    }

    public void putIn(MsgEvent msg) throws InterruptedException {
        queue.put(msg);
    }

    public MsgEvent takeOut() throws InterruptedException {
        return queue.take();
    }

    public static  MsgQueue getInstance(){
        return  MsgHoder.msgQueue;
    }

    public  int  getQueueSize(){
        if(queue==null){
            return  0;
        }else{
            return queue.size();
        }
    }
}
