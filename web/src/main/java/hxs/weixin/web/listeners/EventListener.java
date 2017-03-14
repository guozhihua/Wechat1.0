package hxs.weixin.web.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by :Guozhihua
 * Date： 2017/3/13.
 */
public class EventListener implements ServletContextListener {
    /**
     * Receives notification that the web application initialization
     * process is starting.
     * <p/>
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     * <p/>
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
            ServletContext sc =  sce.getServletContext();


    }

}
