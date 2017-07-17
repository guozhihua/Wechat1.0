package hxs.weixin.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by zhusen on 2016/12/7.
 */
@Component
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
//
//    @Autowired
//    protected RedisClientTemplate redisClientTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sign = request.getParameter("sign");
        if (!StringUtils.isEmpty(sign)) {
            return true;
//            String userId = redisClientTemplate.get(sign);
//            if (StringUtils.isEmpty(userId)) {
//                signError(response);
//                return false;
//            } else {
//                return true;
//            }
        } else {
            signError(response);
            return false;
        }

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

    private void signError(HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
            jsonObject.put("statusCode", 0);
            jsonObject.put("message", "签名验证失效");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.append(jsonObject.toJSONString());
            writer.flush();


        } catch (Exception ex) {

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
