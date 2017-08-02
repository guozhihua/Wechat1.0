package xchat.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
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


}
