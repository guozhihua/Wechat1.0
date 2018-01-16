package xchat.sys;
/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 火山注册助手
 */
public class HuoShanRegsiterUitls {

     //os_api 22:头条的  23：火上的
    private static String huoshan_resister_check = "https://iu.snssdk.com/user/mobile/send_code/v2/?os_api=23&" +
             "device_type=vivo+Y70&device_platform=android&ssmix=a&iid=22765174858" ;
//             "&manifest_version_code=313&dpi=320&uuid=864282030024547&version_code=313" +
//             "&app_name=live_stream&version_name=3.1.3&openudid=9c44daa398745111&device_id=35800373860&resolution=720*1280&os_version=6.0&language=zh&device_brand=vivo&ac=wifi&update_version_code=3131&aid=1112&channel=vivo&_rticket=1516089952624&ts=1516089954&as=a2d55b75b2663ae2cd&cp=b16baf5129db5222e2&mas=003a68bde17a99c6070d257fe6f48e681e22064e22" ;
//            "&manifest_version_code=313&dpi=320&uuid=864282020024243&version_code=313" +
//            "&app_name=live_stream&version_name=3.1.3&openudid=9c44daa398746111&device_id" +
//            "=35800373860&resolution=720*1280&os_version=6.0&language=zh&" +
//            "device_brand=vivo&ac=wifi&update_version_code=3131&aid=1112&channel=vivo" +
//            "&as=a2553875639a2ac42d&cp=84a9aa5738da5145e2&mas=00813d30f3e5e97b4cc57568644ead0a5a448ae644";

    /**
     * 可以判断是不是注册过的依据
     *
     * @param mobile
     * @return
     * @throws Exception
     */
     public static String checkMobileRegister(String mobile) throws Exception {
        long time = new Date().getTime();
        mobile=converMoblie(mobile);
        String url=huoshan_resister_check.concat("&_rticket=").concat(time+"&ts=").concat(time/1000+"");
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("mix_mode","1");
        paramsMap.put("type","31");
        paramsMap.put("mobile",mobile);
        paramsMap.put("os_api","23");
        JSONObject jsonObject = HttpUtils.postForm(url, null, paramsMap);
        String res=jsonObject.toJSONString();
         System.out.println("火山res:"+mobile+res);
        if(res.contains("error_code")){
            //c错误说明没注册过
            return "200";
        }else if(res.contains("retry_time")){
           return "300";
        }
        System.out.println(jsonObject.toJSONString());
        return "110";
    }

    public static String checkMobile(String mobile) throws Exception {
        if(StringUtils.isBlank(mobile)){
            mobile=null;
        }
        String mobile1 = YimaUtils.getMobile(YimaCodeConfig.Huoshan_code, mobile);
        if(StringUtils.isBlank(mobile1)){
             mobile1 = YimaUtils.getMobile(YimaCodeConfig.Huoshan_code, mobile);
        }
        System.out.println("=======易码返回的手机号："+mobile1);
        if(StringUtils.isBlank(mobile1)){
            return "error:110";
        }
        //检查火上注册过没
        String s = checkMobileRegister(mobile1);
        return   mobile1+":"+s;
    }

    /**
     * 获取某个手机的验证码
     * @param mobile
     * @return
     * @throws Exception
     */
    public static String getAuthCode(String mobile) throws Exception {
        String result = null;
        String authCode = YimaUtils.getAuthCode(YimaCodeConfig.Huoshan_code, mobile);
        if(StringUtils.isBlank(authCode)){
            throw  new Exception(mobile+"获取验证码失败了....");
        }
        if(authCode.length()>8){
            result= authCode.substring(8);
        }
        return  result;
    }



    public static void main(String[] args) {
        try {
            String s = checkMobileRegister("17192892949");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static String converMoblie(String mobile) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mobile.length(); i++) {
            char c = mobile.charAt(i);
            sb.append(emnc[Integer.parseInt("" + c)]);
        }
        return sb.toString();
    }


    private static String[] emnc = new String[]{"35", "34", "37", "36", "31", "30", "33", "32", "3d", "3c"};

}
