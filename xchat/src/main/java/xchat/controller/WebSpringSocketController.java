package xchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import xchat.controller.task.HuangjinDarenAnswer;
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
public class WebSpringSocketController {
    private static Logger logger = LoggerFactory.getLogger(WebSpringSocketController.class);

    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/hjdr/setAuth")
    @ResponseBody
    public WebModel send(HttpServletRequest request) {
        WebModel webModel = WebModel.getInstance();
        HuangjinDarenAnswer.setAuthHeader(request.getParameter("auth"));
        return webModel;
    }




}
