package xchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.controller.task.HuangjinDarenAnswer;
import xchat.sys.SessionBucket;
import xchat.sys.WebModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by 志华 on 2018/2/4.
 */
@Controller
public class WebSpringSocketController extends ABaseController {
    private static Logger logger = LoggerFactory.getLogger(WebSpringSocketController.class);

    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/hjdr/setAuth")
    @ResponseBody
    public WebModel send(HttpServletRequest request) {
        WebModel webModel = WebModel.getInstance();
        HuangjinDarenAnswer.setAuthHeader(request.getParameter("auth").trim());
        logger.info("set 黄金答人的auth 为：{}",HuangjinDarenAnswer.authHeder);
        return webModel;
    }
    @RequestMapping("/hjdr/setQuestionFlag")
    @ResponseBody
    public WebModel setQuestionFlag(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        //1 关闭  0 打开
        if("1".equals(params)){
            HuangjinDarenAnswer.getQuestion=false;
        }else{
            HuangjinDarenAnswer.getQuestion=true;
        }
        logger.info("set 黄金答人的开关是 {}", HuangjinDarenAnswer.getQuestion);
        webModel.setMsg("黄金答人获取题的开关是："+  HuangjinDarenAnswer.getQuestion);
        Map<String, WebSocketSession> allsessionMap = SessionBucket.getInstance().getAllsessionMap();
        TextMessage textMessage=new TextMessage(HuangjinDarenAnswer.getQuestion?"3@1":"3@0");
        for(WebSocketSession session:allsessionMap.values()){
            try {
                session.sendMessage(textMessage);
            } catch (IOException e) {
              logger.error("发送链接信息",e);
            }
        }
        return webModel;
    }



}
