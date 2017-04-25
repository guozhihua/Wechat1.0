package hxs.weixin.web.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by :Guozhihua
 * Date： 2017/3/13.
 */
public class EventListener implements SmartApplicationListener {


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return aClass==ApplicationEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
