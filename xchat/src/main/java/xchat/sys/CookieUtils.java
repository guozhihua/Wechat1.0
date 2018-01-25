package xchat.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * cookieUtils 主要是控制AI 的事件
 * Created by :Guozhihua
 * Date： 2017/8/23.
 */
@Component
public class CookieUtils {
    private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    private static final String path = "/";

    private static Set<String> cookieNames = new HashSet<>();

    {
        cookieNames.add("passport_ticket");
    }


    /**
     * 设置cookie
     *
     * @param response
     * @param domain
     * @param key
     * @param val
     */
    public static void setCookie(HttpServletResponse response, String domain, String key, String val, boolean readOnly, int time) {
        Cookie cookie = new Cookie(key, val);
//        cookie.setDomain(domain);
        cookie.setMaxAge(time);
        cookie.setPath(path);
        response.addCookie(cookie);

    }

    /**
     * 删除cookie
     * @param request
     * @param response
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie == null) {
                    continue;
                }
                if (cookieNames.contains(cookie.getName())) {
                    logger.info("cookie :key is{},value is {},version is {}",cookie.getName(),cookie.getValue(),cookie.getVersion());
                    Cookie cookie2 = new Cookie(cookie.getName(), null);
                    cookie2.setMaxAge(0);
                    cookie2.setPath(path);
                    response.addCookie(cookie2);
                }
            }
        }

    }

    /**
     * 获取一个cookie 的val
     * @param request
     * @param key
     * @return
     */
   public static  String getCookieVal(HttpServletRequest request,String  key){
       Map<String, String> requestCookie = CookieUtils.getRequestCookie(request.getCookies());
       String val=requestCookie.get(key);
       return val;
   }


    /**
     * 遍历获取指定路径下的cookie key-val
     *
     * @param cookies
     * @return
     */
    public static Map<String, String> getRequestCookie(Cookie[] cookies) {
        Map<String, String> cookieMaps = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie == null) {
                    continue;
                }
                cookieMaps.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMaps;
    }

}
