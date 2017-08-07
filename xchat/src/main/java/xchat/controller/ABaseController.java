package xchat.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
public abstract class ABaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected HttpServletResponse response;


    protected String getParamString() {
        String json = request.getParameter("params");
        return json;
    }

    protected <T> T getParamEntity(Class<T> tClass) {
        String json = request.getParameter("params");
        return JSON.parseObject(json, tClass);
    }

    protected Map<String, Object> getParamMap() {
        String json = request.getParameter("params");

        return JSON.parseObject(json, HashMap.class);
    }

    protected void  setCookie(String key,String val){
        Cookie cookie2 = new Cookie(key,val);
//        cookie2.setDomain("dev.edu.cn");
        cookie2.setDomain("101.200.55.143");
        cookie2.setMaxAge(60*200);
        cookie2.setHttpOnly(false);
        cookie2.setPath("/");
        this.response.addCookie(cookie2);
    }


}
