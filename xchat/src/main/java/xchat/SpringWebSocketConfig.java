package xchat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import xchat.controller.SpringWebSocketHandler;
import xchat.interceptors.SpringWebSocketHandlerInterceptor;

/**
 * Created by :Guozhihua
 * Date： 2018/2/5.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("register SpringWebSocketConfig 。。。。。。。。。。。。。");
        registry.addHandler(webSocketHandler(),"/websocket/socketServer.do").
                addInterceptors(new SpringWebSocketHandlerInterceptor()).setAllowedOrigins("http://localhost","http://dev.edu.cn");
        registry.addHandler(webSocketHandler(), "/sockjs/socketServer.do").addInterceptors(new SpringWebSocketHandlerInterceptor()).withSockJS();
    }

    @Bean
    public TextWebSocketHandler webSocketHandler(){
        return new SpringWebSocketHandler();
    }
}
