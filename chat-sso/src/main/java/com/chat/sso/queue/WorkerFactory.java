package com.chat.sso.queue;


import com.chat.sso.exception.ProException;

/**
 * Created by :Guozhihua
 * Date： 2017/4/7.
 */
public interface WorkerFactory {
    Worker getWorker(String workName) throws ProException;

}
