package xchat.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.CookieGenerator;
import sun.security.provider.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
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
        System.out.println(this.request.getServerName()
        );
        Cookie cookie2 = new Cookie(key, Md5.GetMD5Code("[2342423b2yu]").toLowerCase().toString());
        cookie2.setDomain(request.getServerName());
//        cookie2.setDomain("101.200.55.143");
        cookie2.setMaxAge(60*200);
        cookie2.setHttpOnly(true);
        cookie2.setPath("/");
        Cookie cookie3= new Cookie(key,val);
        cookie3.setDomain(request.getServerName());
//        cookie2.setDomain("101.200.55.143");
        cookie3.setMaxAge(60*200);
        cookie3.setHttpOnly(false);
        cookie3.setPath("/xchat/");

        this.response.addCookie(cookie2);
        this.response.addCookie(cookie3);
    }


}
