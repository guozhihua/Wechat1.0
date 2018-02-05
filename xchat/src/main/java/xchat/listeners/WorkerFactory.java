package xchat.listeners;


import xchat.sys.ProException;

/**
 * Created by :Guozhihua
 * Date： 2017/4/7.
 */
public interface WorkerFactory {
    Worker getWorker(String workName) throws ProException;

}
