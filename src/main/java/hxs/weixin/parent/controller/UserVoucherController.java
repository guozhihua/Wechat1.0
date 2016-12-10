package hxs.weixin.parent.controller;

import hxs.weixin.parent.entity.PUserVoucher;
import hxs.weixin.parent.entity.vo.UserVoucherVo;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.PUserVoucherService;
import hxs.weixin.parent.service.VoucherService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.sys.enums.VoucherWayEnum;
import hxs.weixin.parent.sys.exceptions.ProBaseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PUserVoucherService pUserVoucherService;

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

        PUserVoucher userVoucher = new PUserVoucher();
        userVoucher.setVoucherWay(voucherWay);
        VoucherWayEnum voucherWayEnum=VoucherWayEnum.getEnumByCode(userVoucher.getVoucherWay());
      /*  Map<String,Object> obj=new HashMap<>();
        obj.put("amount",voucherWayEnum.getWayInfo());
        Voucher voucher = voucherService.queryOne(obj);
        if(voucher==null){
            baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"voucherWay is error!");
            return  baseResponse;
        }*/
//        userVoucher.setVoucherId(voucher.getVoucherId());
        userVoucher.setUserId(userId);
        try {
            if(userVoucher!=null){
                if(userVoucher.getUserId()==null){
                    baseResponse.isFail(ResponseCode.PARAMETER_MISS,"userId is miss!");
                }else{

                    if(voucherWayEnum==null){
                        baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"wayType is error，system is not found");
                        return baseResponse;
                    }
                    userVoucher.setOutTradeNo(outTradeNo);
                   Map<String,Object> result =pUserVoucherService.insertUserVoucher(userVoucher,voucherWayEnum);
                   baseResponse.setResult(result);
                }
            }else{
               baseResponse.isFail(ResponseCode.PARAMETER_MISS,null);
            }
        }catch (ProBaseException pro){
            baseResponse.isFail(ResponseCode.FX_FAIL,pro.getMessage());
            logger.error("分享获取代金券失败",pro);
            return  baseResponse;
        }catch (Exception e) {
            logger.error("分享获取代金券失败:", e);
            baseResponse.isFail(ResponseCode.FX_FAIL,null);
           return baseResponse;
        }
        logger.info("save user voucehr  response :"+super.gson.toJson(baseResponse));
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
           int num =pUserVoucherService.checkUserVoucher(param);
           if(num==0){
               baseResponse.setMessage("success");
           }else{
               baseResponse.isFail(ResponseCode.FX_FAIL,"该券只能获取一次");
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
            Map<String,Object> param=new HashMap<>();
            param.put("userId",userId);
            param.put("isUsed",0);
            param.put("endTime",new Date());
            List<UserVoucherVo> userVoucherVos = pUserVoucherService.getUserVoucherCount(param);
            baseResponse.setResult(userVoucherVos);
        }catch (Exception ex){
            logger.error("分享获取代金券失败:", ex);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
            return baseResponse;
        }
        logger.info(" check_user response :"+super.gson.toJson(baseResponse));
        return baseResponse;
    }



    private VoucherWayEnum chechVoucherWayEnum(String way){
        VoucherWayEnum voucherWayEnum=  VoucherWayEnum.getEnumByCode(way);
        return voucherWayEnum;
    }




}
