package xchat.sys;

/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */
public class YimaCodeConfig {
    public static String Chongding_code="13235";
    public static String Huoshan_code="5022";
    public static String Xigua_code="11610";
    public static String Zhishi_code="13415";
    public static String Douyin_code="7732";

    public static String user_token="00319528a0332712e7a8167910a1439ca3600bfa";
   //获取账户信息
   public static String get_userInfo="http://api.51ym.me/UserInterface.aspx?action=getaccountinfo&format=1&token=token"+user_token;

   //send code
    public static String get_mobile="http://api.51ym.me/UserInterface.aspx?action=getmobile&itemid={itemid}&mobile={mobile}&token="+user_token;
    public static String  release_mobile="http://api.51ym.me/UserInterface.aspx?action=release&mobile={mobile}&itemid={itemid}&token="+user_token;
    public static String get_auth_code="http://api.51ym.me/UserInterface.aspx?action=getsms&mobile={mobile}&itemid={itemid}&token="+user_token;
    public static String check_authCode_status="http://api.51ym.me/UserInterface.aspx?action=getsendsmsstate&mobile={mobile}&itemid={itemid}&token="+user_token;


}
