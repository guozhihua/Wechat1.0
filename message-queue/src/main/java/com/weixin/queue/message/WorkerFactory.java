package com.weixin.queue.message;

import com.weixin.utils.sys.exceptions.ProBaseException;

/**
 * Created by :Guozhihua
 * Date： 2017/4/7.
 */
public interface WorkerFactory {
    Consumer getWorker(String workName) throws ProBaseException;

}
