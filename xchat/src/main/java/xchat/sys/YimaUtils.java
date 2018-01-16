package xchat.sys;

/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */

import org.apache.commons.lang.StringUtils;

/**
 * 易码平台
 */
public class YimaUtils {

    public static String getMobile(String itemId, String mobile) throws Exception {
        String result = null;
        String url = YimaCodeConfig.get_mobile;
        if (StringUtils.isNotBlank(mobile)) {
            url = url.concat("&mobile=" + mobile);
        }
        url = url.replace("{itemid}", itemId);
        String response = HttpUtils.get(url);
        System.out.println(response);
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
        String url = YimaCodeConfig.get_auth_code;
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
        System.out.println(s);
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
        int retry = 0;
        for (int i = 0; i < 20; i++) {
            System.out.println("开始获取【" + mobile + "】验证码....");
            result = getCode(itemId, mobile);
            if (result.contains("success")) {
//                result= result.substring(8);
                break;
            } else {
                retry++;
                Thread.sleep(5000);
            }
        }
        if (result != null) {
            //释放
            releaseMobile(itemId, mobile);
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

    public static void main(String[] args) {
        try {
//            System.out.println(getMobile(YimaCodeConfig.Xigua_code,null));
            System.out.println(getAuthCode(YimaCodeConfig.Huoshan_code, "17182585423"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            releaseAll();
        }
    }
}
