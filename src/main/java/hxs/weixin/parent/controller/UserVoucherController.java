package hxs.weixin.parent.controller;

import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.VoucherService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.sys.enums.VoucherWayEnum;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户优惠券controller
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
@Controller
@RequestMapping(value = "/userVoucher")
public class UserVoucherController extends  ABaseController {
private static Logger logger = Logger.getLogger(UserVoucherController.class);



    @Autowired
    private VoucherService voucherService;

    /**
     * 保存用户代金券
     */
    @RequestMapping(value="/save",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse saveUserVoucher(@RequestParam("userId") String  userId,
                                        @RequestParam("voucherWay") String  voucherWay,
                                        @RequestParam(required =false) String  outTradeNo,
                                        @RequestParam(required=false) String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);



        return baseResponse;
    }

    /**
     * 检测用户是否获取过某些一次性代金券
     */
    @RequestMapping(value="/check_user", method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse checkUserVoucher(  @RequestParam("wayType") String  wayType,
                                           @RequestParam("userId") String  userId, @RequestParam(required=false) String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
       try{
            Map<String,Object> param= new HashMap<>();
            param.put("wayType",wayType);
            param.put("userId",userId);
           VoucherWayEnum voucherWayEnum=VoucherWayEnum.getEnumByCode(wayType);
           if(voucherWayEnum==null){
               baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"wayType is error");
               return baseResponse;
           }

       }catch (Exception ex){
           logger.error("分享获取代金券失败:", ex);
           baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
           return baseResponse;
       }
        logger.info(" check_user response :"+super.gson.toJson(baseResponse));
        return baseResponse;
    }
    /**
     * 获取用户代金券基本信息
     */
    @RequestMapping(value="/get",method={ RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @MethodLog
    public BaseResponse getUserVoucher(@RequestParam("userId") String userId, @RequestParam(required=false) String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
        try{
            if(StringUtils.isEmpty(userId)){
                baseResponse.isFail(ResponseCode.PARAMETER_MISS,"userId is miss");
                return baseResponse;
            }
        }catch (Exception ex){
            logger.error("分享获取代金券失败:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
            return baseResponse;
        }
        logger.info(" check_user response :"+super.gson.toJson(baseResponse));
        return baseResponse;
    }



    /**
     * 产品图片上传
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse upload(@RequestParam(value = "file") MultipartFile multipartFile) {
        BaseResponse baseResponse = new BaseResponse();
        if (multipartFile == null) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "multipartFile");
        }
        try {
            String baseUrl = "/opt/statocs/";
            File file = new File(baseUrl);
            if (!file.exists()&&!file.isDirectory()) {
                file.mkdirs();
            }
            String name = multipartFile.getOriginalFilename();
            name = UUID.randomUUID().toString().replace("-","") + name.substring(name.lastIndexOf(".")).toLowerCase();
            String path = baseUrl + name;
            file = new File(path);
            try {
                // 先尝试压缩并保存图片
                Thumbnails.of(multipartFile.getInputStream()).scale(0.7f).outputQuality(0.35f).toFile(file);
            } catch (IOException e) {
                try {
                    multipartFile.transferTo(file);
                } catch (IOException e1) {
                    logger.error("file upload error");
                    baseResponse.isFail(ResponseCode.SERVICE_ERROR,"图片上传失败");
                    return baseResponse;
                }
            }
            Map<String, Object> result = new HashMap();
            result.put("path",   "/statics/" + name);
            baseResponse.setResult(result);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" admin fileUpload  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("admin fileUpload error", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }



}
