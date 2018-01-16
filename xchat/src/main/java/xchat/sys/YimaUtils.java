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
        String result=null;
        String url = YimaCodeConfig.get_mobile;
        if(StringUtils.isBlank(mobile)){
            url = url.replace("{mobile}", "");
        }else {
            url = url.replace("{mobile}", mobile);
        }
        url = url.replace("{itemid}", itemId);
        String response = HttpUtils.get(url);
        if(response.contains("success")){
          result= response.substring(8);
        }

        return result;
    }

    /**
     * 获取短信内容
     * @param itemId
     * @param mobile
     * @return
     * @throws Exception
     */
    private static String getCode(String itemId,String mobile) throws Exception{
        String result=null;
        String url = YimaCodeConfig.get_auth_code;
        if(StringUtils.isBlank(mobile)){
            url = url.replace("{mobile}", "");
        }else {
            url = url.replace("{mobile}", mobile);
        }
        url = url.replace("{itemid}", itemId);
        result = HttpUtils.get(url);

        return  result;
    }


    /**
     * 获取短信内容
     * @param itemId
     * @param mobile
     * @return
     * @throws Exception
     */
    public static String getAuthCode(String itemId,String mobile) throws Exception{
        String result =null;
        int retry=0;
        for(int i=0;i<6;i++){
            System.out.println("开始获取验证码....");
             result=getCode(itemId,mobile);
            if(result.contains("success")){
                result= result.substring(8);
                break;
            }else{
                retry++;
                Thread.sleep(6000);
            }
        }
        return  result;

    }

    /**
     * 释放手机号码
     * @param itemId
     * @param mobile
     * @return
     */
    public static String releaseMobile(String itemId,String mobile){
        String result=null;
        String url = YimaCodeConfig.release_mobile;
        if(StringUtils.isBlank(mobile)){
            url = url.replace("{mobile}", "");
        }else {
            url = url.replace("{mobile}", mobile);
        }
        url = url.replace("{itemid}", itemId);
        result = HttpUtils.get(url);
        return result;
    }

    public static void main(String[] args) {
        try{
//            System.out.println(getMobile(YimaCodeConfig.Xigua_code,null));
            System.out.println(getAuthCode(YimaCodeConfig.Xigua_code,"18426330965"));


        }catch (Exception ex){ex.printStackTrace();}
    }
}
