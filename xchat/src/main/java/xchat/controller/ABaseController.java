package xchat.controller;

import com.alibaba.fastjson.JSON;
import com.weixin.utils.responsecode.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xchat.sys.ValiResult;

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

    protected ValiResult validataParams(String... names) {
        boolean isSuccess = true;
        ResponseCode responseCode = ResponseCode.SUCCESS;
        StringBuilder sb= new StringBuilder("");
        if (names.length > 0) {
            Map<String, Object> pamrasMap = this.getParamMap();
            for (String name : names) {
                Object val = pamrasMap.get(name);
                if (val == null) {
                    sb.append("参数【"+name+"】缺失！");
                    responseCode=ResponseCode.PARAMETER_MISS;
                    isSuccess = false;
                    break;
                } else {
                    if (val instanceof String) {
                        if (StringUtils.isBlank(val.toString())) {
                            sb.append("参数【"+name+"】的值为null！");
                            responseCode= ResponseCode.PARAMETER_MISS;
                            isSuccess = false;
                            break;
                        }
                    }
                }
            }
        }
        ValiResult valiResult= new ValiResult(isSuccess,sb.toString(),responseCode);
        return valiResult;
    }


}
