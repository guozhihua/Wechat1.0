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
@RequestMapping("/code/")
public class CoderController extends  ABaseController{
    private final Logger logger = LoggerFactory.getLogger(CoderController.class);

    @RequestMapping("/zhishi")
    @ResponseBody
    public WebModel list(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            String code =paramsMap.get("code").toString();
            int size =Integer.parseInt(paramsMap.get("size").toString());
            int fistCode =Integer.parseInt(paramsMap.get("fistCode").toString());
            int last =ZhiShiCode.setCode(code,size,fistCode);
            webModel.setDatas(last);
            webModel.setMsg("成功充值"+last+"张复活卡");
            logger.info("code is {},size is{},result is  {}",code,size,last);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }


    @RequestMapping("/xg")
    @ResponseBody
    public WebModel xg(@RequestParam("params") String params) {
        WebModel webModel = WebModel.getInstance();
        try {
            Map<String,Object> paramsMap =super.getParamMap();
            String code =paramsMap.get("code").toString();
            int size =Integer.parseInt(paramsMap.get("size").toString());
            int fistCode =Integer.parseInt(paramsMap.get("fistCode").toString());
            int last =ZhiShiCode.setCode(code,size,fistCode);
            webModel.setDatas(last);
            webModel.setMsg("成功充值"+last+"张复活卡");
            logger.info("code is {},size is{},result is  {}",code,size,last);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }


}
