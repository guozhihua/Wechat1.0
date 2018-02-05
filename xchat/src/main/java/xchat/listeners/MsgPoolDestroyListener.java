package xchat.listeners;


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
        //清理所有的workers
        ConsumerProxyFactory.getInstance().shutDownWorker();
        //关闭线程池
        MsgConsumerIniter.shutdown();
    }
}
