package xchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xchat.sys.Md5;
import xchat.sys.ValiResult;
import xchat.sys.WebModel;

import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
@Controller
@RequestMapping("/user/")
public class UserController extends  ABaseController{
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String salt="12suidhfi213h123918ncjkncjx__+A_S+DS_";

    @RequestMapping("/login")
    @ResponseBody
    public WebModel login(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            ValiResult valiResult = validataParams("username", "pwd");
            if(valiResult.isSuccess()){
                Map<String,Object> paramsMap =super.getParamMap();
                String userId=paramsMap.get("username").toString();
                String pwd=paramsMap.get("pwd").toString();
                if(userId.equals("admin")&&pwd.equals("admin123")){
                    webModel.setDatas("登陆成功");
                    webModel.setDatas(88888888);
                    setCookie("Passport_ticket", Md5.GetMD5Code(userId+salt));
                }else{
                    webModel.setMsg("登录失败");
                }
            }else{
               webModel.setCode(valiResult.getHttpCode().code);
                webModel.setMsg(valiResult.getMessage());
            }
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public WebModel  logout(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            if(!paramsMap.get("username").toString().equals("admin")){
                webModel.setCode("701");
                webModel.setMsg("登陆失败");
                setCookie("passport_ticket",null);
            }else{
                setCookie("Passport_ticket","123456");
            }
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public WebModel  getUserInfo(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            if(!paramsMap.get("userName").toString().equals("admin")){
                webModel.setMsg("登陆失败");
            }else{
                webModel.setCode("709");
            }
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
}
