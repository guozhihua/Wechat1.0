package xchat.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xchat.aop.NeedLogin;
import xchat.pojo.User;
import xchat.pojo.UserTicket;
import xchat.service.UserService;
import xchat.service.UserTicketService;
import xchat.sys.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
@Controller
@RequestMapping("/user/")
public class UserController extends ABaseController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserTicketService userTicketService;
    @Autowired
    private UserService userService;

    private static final String salt = "12suidhfi213h123918ncjkncjx__+A_S+DS_";

    @RequestMapping("/login")
    @ResponseBody
    public WebModel login(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            ValiResult valiResult = validataParams("userName", "password");
            request.getSession();
            if (valiResult.isSuccess()) {
                Map<String, Object> paramsMap = super.getParamMap();
                List<User> users = userService.queryList(paramsMap,1,1);
                if(CollectionUtils.isNotEmpty(users)){
                    User user =users.get(0);
                    user.setPassword(null);
                    webModel.setDatas(user);
                    UserTicket userTicket = userTicketService.queryById(user.getUserId());
                    if(userTicket==null){
                        userTicket=new UserTicket();
                        userTicket.setUserId(user.getUserId());
                        userTicket.setTicket(Md5.GetMD5Code(user.getUserId()+salt+ RandomUtils.nextInt(100000)));
                        userTicketService.insertSelective(userTicket);
                    }else{
                        userTicket.setTicket(Md5.GetMD5Code(user.getUserId()+salt+ RandomUtils.nextInt(100000)));
                        userTicketService.updateByIdSelective(userTicket);
                    }
                    CookieUtils.setCookie(response,request.getServerName(),"passport-ticket",userTicket.getTicket(),true,-1);
                }else{
                    webModel.isFail();
                }
            } else {
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
    @NeedLogin
    public WebModel logout() {
        WebModel webModel = WebModel.getInstance();
        try {
            UserTicket userTicket = ThreadLocaUser.get();
            userTicketService.deleteById(userTicket.getUserId());
            CookieUtils.removeCookie(request,response);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }

    @NeedLogin
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public WebModel getUserInfo() {
        WebModel webModel = WebModel.getInstance();
        try {
            UserTicket userTicket = ThreadLocaUser.get();
            User user = userService.queryById(userTicket.getUserId());
            if (user != null) {
                user.setPassword(null);
                webModel.setDatas(user);
            } else {
                webModel.isFail();
            }
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
}
