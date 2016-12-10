package hxs.weixin.parent.controller;

import hxs.weixin.parent.aop.LogService;
import hxs.weixin.parent.entity.vo.WeChatConfigVo;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.WeChatHttpService;
import hxs.weixin.parent.sys.MethodLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by :Guozhihua
 * Date： 2016/11/28.
 */
@Controller
@RequestMapping("/wx/common/")
public class WeiXinShareController  extends  ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinShareController.class);

    @Autowired
    private WeChatHttpService weChatHttpService;



    @RequestMapping(value = "/get_sign", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog(remark = "获取微信通用的签名")
    public BaseResponse wxShareGeTSign(@RequestParam("url") String url,@RequestParam(required = false) String openId) {
            BaseResponse baseResponse = new BaseResponse();
        try{
            WeChatConfigVo chatConfigVo = weChatHttpService.getWeChatConfig(url);
            baseResponse.setResult(chatConfigVo);
            logger.info(gson.toJson(baseResponse));
        }catch (Exception ex){
            logger.error("wx share get sign error:"+ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
            return  baseResponse;
        }
        return  baseResponse;
    }


}
