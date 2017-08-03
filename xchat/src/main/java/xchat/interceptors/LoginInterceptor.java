package xchat.interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhusen on 2017/1/4.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(LoginInterceptor.class);
    private static final String passport_ticket = "Passport_ticket";



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        //验证token是否失效，失效则重新登录
        if (handler instanceof HandlerMethod) {
           String passport =request.getHeader(passport_ticket);
            if(StringUtils.isEmpty(passport)||!"123456".equals(passport)){
                response.sendError(702,"ticket is miss");
                flag=false;
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
