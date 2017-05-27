package hxs.weixin.web.controller.test;

import com.weixin.utils.responsecode.BaseResponse;
import com.weixin.utils.sys.MethodLog;
import com.weixin.yj.search.ESHelper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sso.provider.SsoUserLoginService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
@Controller
@RequestMapping(value = "/dubbo_consumer/")
public class TestDubboComsumer {
    @Autowired(required = false)
    private SsoUserLoginService ssoUserLoginService;


    /**
     * 发送消息
     */
    @RequestMapping(value="send",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse saveUserVoucher(){
        BaseResponse baseResponse = new BaseResponse();
        try{
            baseResponse.setResult(ssoUserLoginService.queryUserByTicket("2342"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return baseResponse;
    }
}
