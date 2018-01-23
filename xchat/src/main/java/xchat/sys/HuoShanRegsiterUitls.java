package xchat.sys;
/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.cookie.Cookie;
import xchat.pojo.DeviceInfo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 火山注册助手
 */
public class HuoShanRegsiterUitls {
    public static long iid = 23998073710L;
    public static long uuid = 898282030024243L;
    public static long device_id = 77880373860L;
    //+ 5111
    public static String openId = "9c74gar398t45111";

    public static String test_Url = "http://textbooktest.e-edusky.com//textbook/common/get_area";

    /**
     * uuid  860480451187309  是更具什么生成的   get
     */
    public static String local_push_data = "https://hotsoon.snssdk.com/hotsoon/local_push_data/?" +
            "ac=wifi&channel=tengxun&aid=1112&app_name=live_stream&version_code=313&version_name=3.1.3" +
            "&device_platform=android&ssmix=a&device_type=MI+5s&device_brand=Xiaomi&" +
            "language=zh&os_api=19&os_version=4.4.4" +
            "&" +
            "manifest_version_code=313&resolution=720*1280&dpi=192&" +
            "update_version_code=3131" +
            "";

    /**
     * 注册设备，openId是怎么来的 需要知道  post
     */
    public static String device_register = "http://ib.snssdk.com/service/2/device_register/?" +
            "ac=wifi&channel=tengxun&aid=1112&app_name=live_stream&version_code=313&" +
            "version_name=3.1.3&device_platform=android&" +
            "ssmix=a&device_type=MI+5s&device_brand=Xiaomi&" +
            "language=zh&os_api=19&os_version=4.4.4" +
            "&manifest_version_code=313&resolution=720*1280&dpi=192&update_version_code=3131" +
            "&tt_data=a";

    //os_api 22:头条的  23：火上的
    private static String huoshan_resister_check = "https://iu.snssdk.com/passport/mobile/send_code/?os_api=23&" +
            "device_type=vivo+Y70&device_platform=android";

    private static String registerUrl = "https://iu.snssdk.com//passport/mobile/register/?os_api=23&device_type=vivo+X9&device_platform=android&ssmix=a" +
            "&manifest_version_code=313&dpi=320";

    //使用邀请码
    private static String check_inviteCode = "https://api-spe.snssdk.com/h/1/cli/check_invite/?" +
            "ac=wifi&channel=vivo&aid=1112&app_name=live_stream&version_code=313&version_name=3.1.3&device_platform=android&" +
            "ssmix=a&device_type=vivo+X9&device_brand=vivo&language=zh&os_api=23&os_version=6.0&" +
            "&manifest_version_code=313&resolution=720*1280" +
            "";


    public static String setCode(String inviteCode, String sessionKey) throws IOException {
        long time = new Date().getTime();
        check_inviteCode = check_inviteCode.concat("@iid=" + iid);
        check_inviteCode = check_inviteCode.concat("&device_id=" + device_id);
        check_inviteCode = check_inviteCode.concat("&uuid=" + uuid);
        check_inviteCode = check_inviteCode.concat("&openudid=" + openId);
        check_inviteCode = check_inviteCode.concat("&_rticket=" + time);
        Map<String, String> paramsMap = new HashMap<>();
        if(HttpUtils.cookieStore!=null){
            for(Cookie cookie:HttpUtils.cookieStore.getCookies()){
                paramsMap.put(cookie.getName(),cookie.getValue());
            }
        }
//        paramsMap.put("install_id", iid + "");
//        paramsMap.put("sessionid", sessionKey);
//        paramsMap.put("sid_tt", sessionKey);
//        paramsMap.put("sid_guard", "03a8e6730ff1d905c1377eb1605209a4%7C1516332973%7C2592000%7CSun%2C+18-Feb-2018+03%3A36%3A13+GMT");
//        paramsMap.put("uid_tt", "16b7d63c7612daa3390c2b867b09aace");
//        paramsMap.put("ttreq", "1$1dced4aa7bddfa75472b1a4113881a3ebd071126");
//
//        paramsMap.put("login_flag", "1befe68ae14653042c00ef4c5e8aab88");
        paramsMap.put("qh[360]", "1");
//        paramsMap.put("_ga", "GA1.2.971146136.1516073055");
//        paramsMap.put("_gid", "GA1.2.1397006827.1516332894");
        inviteCode = "\n" +
                "\n" +
                "\u0005" + inviteCode + "\u0010 \b\u0018    \u0001";
        String res = HttpUtils.postRequestBody(check_inviteCode, inviteCode, paramsMap);
        System.out.println("开始填写邀请码：" + res);
        HttpUtils.cookieStore.clear();
        return res;

    }


    /**
     * 注册手机号
     *
     * @param mobile
     * @param authCode
     * @return
     */
    public static String registeruUser(String mobile, String authCode) {
        long time = new Date().getTime();
        mobile = converMoblie(mobile);
        String url = registerUrl.concat("&_rticket=").concat(time + "&ts=").concat(time / 1000 + "");
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("password", pwd);
        paramsMap.put("mix_mode", "1");
        paramsMap.put("type", "36");
        paramsMap.put("code", converMoblie(authCode));
        paramsMap.put("mobile", mobile);
        paramsMap.put("os_api", "23");
        paramsMap.put("device_type", "vivo X9");
        paramsMap.put("device_platform", "android");
        paramsMap.put("ssmix", "a");
        paramsMap.put("iid", iid);
        paramsMap.put("manifest_version_code", "313");
        paramsMap.put("dpi", "320");
        paramsMap.put("uuid", uuid);
        paramsMap.put("device_id", device_id);
        paramsMap.put("_rticket", time);
        paramsMap.put("openudid", openId);
        paramsMap.put("version_code", "313");
        paramsMap.put("app_name", "live_stream");
        paramsMap.put("version_name", "3.1.3");
        paramsMap.put("resolution", "720*1280");
        paramsMap.put("os_version", "6.0");
        paramsMap.put("language", "zh");
        paramsMap.put("device_brand", "vivo");
        paramsMap.put("ac", "wifi");
        paramsMap.put("update_version_code", "3131");
        paramsMap.put("aid", "1112");
        paramsMap.put("channel", "vivo");
        JSONObject jsonObject = HttpUtils.postForm(url, null, paramsMap);
        String res = jsonObject.toJSONString();
        System.out.println("开始模拟注册火上用户，响应信息：" + res);
        JSONObject jsonObject1 = JSON.parseObject(res);
        if (jsonObject1.containsKey("user_id") && jsonObject1.containsKey("session_key")) {
            String sessionKey = jsonObject1.getString("session_key");
            String userId = jsonObject1.getString("user_id");
            res = "200:" + sessionKey + "@" + userId;
        } else {
            res = "500";
        }
        return res;

    }


    /**
     * 可以判断是不是注册过的依据
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public static String checkMobileRegister(String mobile) throws Exception {

        long time = new Date().getTime();
        mobile = converMoblie(mobile);
        String url = huoshan_resister_check.concat("&_rticket=").concat(time + "&ts=").concat(time / 1000 + "");
//        url.concat("&iid=" + iid).concat("&openudid=" + openId).concat("&device_id=" + device_id).concat("&uuid=" + uuid);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("mix_mode", "1");
        //31 找回密码  34 注册
        paramsMap.put("type", "34");
        paramsMap.put("mobile", mobile);
        paramsMap.put("os_api", "23");
        paramsMap.put("app_name", "live_stream");
        paramsMap.put("_rticket=", time);
        paramsMap.put("iid", iid);
        JSONObject jsonObject = HttpUtils.postForm(url, null, paramsMap);
        String res = jsonObject.toJSONString();
        System.out.println("检测手机号是不是在火山注册:" + mobile + res);
        if (res.contains("error_code")) {
            return "300";
        } else if (res.contains("retry_time")) {
            return "200";
        }
        System.out.println(jsonObject.toJSONString());
        return "110";
    }


    /**
     * @param mobile
     * @param ext    排除在外的 170|171|172
     * @return
     * @throws Exception
     */
    public static String checkMobile(String mobile, String ext) throws Exception {
        if (StringUtils.isBlank(mobile)) {
            mobile = null;
        }
        String mobile1 = YimaUtils.getMobile(YimaCodeConfig.Huoshan_code, mobile, ext);
        if (StringUtils.isBlank(mobile1)) {
            mobile1 = YimaUtils.getMobile(YimaCodeConfig.Huoshan_code, mobile, ext);
        }
        System.out.println("=======易码返回的手机号：" + mobile1);
        if (StringUtils.isBlank(mobile1)) {
            return "error:110";
        }
        //检查火上注册过没
        String s = checkMobileRegister(mobile1);
        return mobile1 + ":" + s;
    }

    /**
     * 本地推送服务器uuid
     * @return
     */
    public static String  localPush(){
        String url=local_push_data.concat("&_rticket=")+new Date().getTime();
        long uuid= (860480451187309L + RandomUtils.nextInt(1000000));
        url= url.concat("&uuid=") +uuid;
        String s = HttpUtils.get(url);
        System.out.println("local push response:" +s);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if(jsonObject!=null&&jsonObject.getInteger("status_code")!=null&&jsonObject.getInteger("status_code")==0){
            return  uuid+"";
        }
        return  null;

    }

    public static JSONObject  diviceRegister(String uuid){
        String url=device_register.concat("&_rticket=")+new Date().getTime();
        String openId= (3083046630144805L + RandomUtils.nextInt(1))+"";
        url= url.concat("&uuid=") +uuid;
        url=url.concat("&openudid="+openId);

        JSONObject jsonObject = HttpUtils.postForm(url,null,null);
        if(jsonObject!=null&&jsonObject.getInteger("new_user")!=null&&jsonObject.getInteger("new_user")==1){
            jsonObject.put("openId",openId);
            return   jsonObject;
        }
        return  null;

    }



    /**
     * 获取某个手机的验证码
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public static String getAuthCode(String mobile) throws Exception {
        String result = null;
        String authCode = YimaUtils.getAuthCode(YimaCodeConfig.Huoshan_code, mobile);
        if (StringUtils.isBlank(authCode)) {
            throw new Exception(mobile + "获取验证码失败了....");
        }
        if (authCode.length() > 8) {
            result = authCode.substring(8);
        }
        return result;
    }


    public static void main(String[] args) {
        try {
//            String s = setCode("a38yb", "571841163e60f1176853aa6fb48c7d80");
            String s = checkMobileRegister("13438375348");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 火山数字解码
     * @param mobile
     * @return
     */
    private static String converMoblie(String mobile) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mobile.length(); i++) {
            char c = mobile.charAt(i);
            sb.append(emnc[Integer.parseInt("" + c)]);
        }
        return sb.toString();
    }


    //adm123
    private static String pwd = "646168343736";

    private static String[] emnc = new String[]{"35", "34", "37", "36", "31", "30", "33", "32", "3d", "3c"};

    public static void setRandomInfo(DeviceInfo deviceInfo) {
        iid = Long.parseLong(deviceInfo.getIid());
        uuid = Long.parseLong(deviceInfo.getUuid());
        device_id = Long.parseLong(deviceInfo.getDeviceid());
        openId = deviceInfo.getOpenid();
        System.out.println("iid=" + iid);
        System.out.println("uuid=" + uuid);
        System.out.println("device_id=" + device_id);
        System.out.println("openId=" + openId);
    }

}
