package xchat.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xchat.sys.HttpUtils;
import xchat.sys.WebModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
@Controller
@RequestMapping("/user/")
public class UserListController  extends  ABaseController{
    private final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @RequestMapping("/session_list")
    @ResponseBody
    public WebModel list(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            String  urkl ="http://pod.dsylove.com/msg/getSessionDetails/v2";
            Map<String,Object> headers=new HashMap<>();
            headers.put("token", paramsMap.get("token").toString());
            headers.put("version", "3.3.3");
            JSONObject jsonObject = HttpUtils.postForm(urkl,headers,null);
            webModel.setDatas(jsonObject);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
    @RequestMapping("/sendMsg")
    @ResponseBody
    public WebModel sendMsg(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            String  urkl ="http://pod.dsylove.com/msg/sendMsg";
            Map<String,Object> headers=new HashMap<>();
            headers.put("token", paramsMap.get("token").toString());
            headers.put("version", "3.3.3");
            JSONObject jsonObject = HttpUtils.postForm(urkl,headers,paramsMap);
            webModel.setDatas(jsonObject);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
    @RequestMapping("/msgList")
    @ResponseBody
    public WebModel  msgList(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            String  urkl ="http://pod.dsylove.com/msg/getMsgPageV2";
            Map<String,Object> headers=new HashMap<>();
            headers.put("token", paramsMap.get("token").toString());
            headers.put("version", "3.3.3");
            paramsMap.put("pageSize",100);
            paramsMap.put("pageNumber",1);
            JSONObject jsonObject = HttpUtils.postForm(urkl,headers,paramsMap);
            webModel.setDatas(jsonObject);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
    @RequestMapping("/login")
    @ResponseBody
    public WebModel  login(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            if(!paramsMap.get("userName").toString().equals("guozhihua")){
                webModel.setCode(701);
                webModel.setMsg("登陆失败");
                setCookie("passport_ticket",null);
            }else{
                setCookie("passport_ticket","123456");
            }
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
}
