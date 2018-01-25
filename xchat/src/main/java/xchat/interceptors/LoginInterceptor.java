package xchat.interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xchat.aop.NeedLogin;
import xchat.pojo.UserTicket;
import xchat.service.UserTicketService;
import xchat.sys.ThreadLocaUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhusen on 2017/1/4.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LogManager.getLogger("LoginInterceptor");
    private static final String passport_ticket = "passport_ticket";

    @Autowired
    private UserTicketService userTicketService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        //验证token是否失效，失效则重新登录
        if (handler instanceof HandlerMethod) {
            System.out.println(request.getCookies());
           String passport =request.getHeader(passport_ticket);
            logger.info("passport is :"+passport);
            HandlerMethod handler2 = (HandlerMethod) handler;
            //获取注解
            NeedLogin needLogin = handler2.getMethodAnnotation(NeedLogin.class);
            if(needLogin!=null){
                if(StringUtils.isNotBlank(passport)){
                    request.setAttribute(passport_ticket,passport);
                    UserTicket userTicket = userTicketService.selectByTicket(passport);
                    if(userTicket==null){
                        response.sendError(709, "Passport ticket  is timeout.");
                        flag=false;
                    }else{
                        //可以放在线程变量里面
                        if(ThreadLocaUser.get()==null){
                            ThreadLocaUser.set(userTicket);
                        }
                    }
                }else{
                    response.sendError(709, "Passport ticket  is timeout.");
                    flag=false;
                }
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }


}
