package hxs.weixin.web.listeners;

import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.remoting.transport.netty.NettyClient;
import com.alibaba.dubbo.rpc.RpcContext;
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
        ProtocolConfig.destroyAll();
        NettyClient.shudownTheadsByContainerDestroy();
        super.contextDestroyed(event);
    }
}
