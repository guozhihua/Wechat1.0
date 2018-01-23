package xchat.sys;

/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * 易码平台
 */
public class YimaUtils {

    public static String getMobile(String itemId, String mobile,String ext) throws Exception {
        String result = null;
        String url = YimaCodeConfig.get_mobile;
        if(StringUtils.isNotBlank(ext)){
            url=url.concat("&excludeno=").concat(ext);
        }
        if (StringUtils.isNotBlank(mobile)) {
            url = url.concat("&mobile=" + mobile);
        }
        url = url.replace("{itemid}", itemId);
        String response = HttpUtils.get(url);
        if (response.contains("success")) {
            result = response.substring(8);
        }
        return result;
    }

    /**
     * 获取短信内容
     *
     * @param itemId
     * @param mobile
     * @return
     * @throws Exception
     */
    private static String getCode(String itemId, String mobile) throws Exception {
        String result = null;
        String url = YimaCodeConfig.get_auth_code.concat("&release=1");
        if (StringUtils.isBlank(mobile)) {
            url = url.replace("{mobile}", "");
        } else {
            url = url.replace("{mobile}", mobile);
        }
        url = url.replace("{itemid}", itemId);
        result = HttpUtils.get(url);

        return result;
    }

    public static void releaseAll() {
        String s = HttpUtils.get(YimaCodeConfig.releaseAll);
    }


    /**
     * 获取短信内容  如果传递mobile，则为指定mobile 内容
     *
     * @param itemId
     * @param mobile
     * @return
     * @throws Exception
     */
    public static String getAuthCode(String itemId, String mobile) throws Exception {
        String result = null;
        for (int i = 0; i < 8; i++) {
            System.out.println("开始获取【" + mobile + "】验证码....");
            result = getCode(itemId, mobile);
            if (result.contains("success")&&result.indexOf("】")>0) {
                result= result.substring(result.indexOf("】")+1,result.indexOf("】")+5);
                break;
            } else {
                Thread.sleep(5000);
            }
        }
        return result;

    }

    /**
     * 释放手机号码
     *
     * @param itemId
     * @param mobile
     * @return
     */
    public static String releaseMobile(String itemId, String mobile) {
        String result = null;
        String url = YimaCodeConfig.release_mobile;
        if (StringUtils.isBlank(mobile)) {
            url = url.replace("{mobile}", "");
        } else {
            url = url.replace("{mobile}", mobile);
        }
        url = url.replace("{itemid}", itemId);
        result = HttpUtils.get(url);
        return result;
    }

    /**
     * 手机号，密码
     * @param phone
     * @param code
     * @return
     */
    public static JSONObject login(String phone,String code ){


        return  null;
    }

    public static void main(String[] args) {
        try {
            String s ="success|【火山小视频】2796（火山小视频验证码），30分钟内有效，请勿泄露";
//            System.out.println(getMobile(YimaCodeConfig.Xigua_code,null));
            System.out.println(s.substring(s.indexOf("】")+1,s.indexOf("】")+5));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            releaseAll();
        }
    }
}
