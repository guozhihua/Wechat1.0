package hxs.weixin.web.controller.test;

import com.weixin.entity.chat.User;
import com.weixin.utils.responsecode.BaseResponse;
import com.weixin.utils.sys.MethodLog;
import com.weixin.utils.util.HTTPClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sso.provider.dubbo.SsoUserLoginService;

import java.util.Date;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
@Controller
@RequestMapping(value = "/dubbo_consumer/")
public class TestDubboComsumer {
    private static final Logger logger= LoggerFactory.getLogger(TestDubboComsumer.class);
    @Autowired(required = false)
    private SsoUserLoginService ssoUserLoginService;


    /**
     * 发送消息
     */
    @RequestMapping(value="dubbo",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse dubbo(){
        BaseResponse baseResponse = new BaseResponse();
        try{
            logger.info("test {} dubbo {}",new Date(),23);
            baseResponse.setResult(ssoUserLoginService.queryUserByTicket("2342"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return baseResponse;
    }



    /**
     * 发送消息
     */
    @RequestMapping(value="rest",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse rest(){
        BaseResponse baseResponse = new BaseResponse();
        try{
           String res =HTTPClientUtils.httpGetRequestJson("http://localhost:20881/user/getUser?ticket=123131");
            System.out.println(res);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return baseResponse;
    }
}
