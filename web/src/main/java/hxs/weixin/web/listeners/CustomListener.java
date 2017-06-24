package hxs.weixin.web.listeners;

import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * Created by 志华 on 2017/6/25.
 */
public class CustomListener extends ContextLoaderListener {
//    static{
//        System.setProperty("dubbo.application.logger","log4j2");
//    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        NettyClient.shudownTheadsByContainerDestroy();
        super.contextDestroyed(event);
    }
}
