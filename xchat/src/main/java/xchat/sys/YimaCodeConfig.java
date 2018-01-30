package xchat.sys;

/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */
public class YimaCodeConfig {
    //冲顶大会
    public static String Chongding_code="13235";
    //火山小视频
    public static String Huoshan_code="5022";
    public static String Xigua_code="11610";
   //芝士超人
    public static String Zhishi_code="13415";
    //抖音
    public static String Douyin_code="7732";
    //一直播 黄金十秒
    public static String Yizhibo="2862";
    //新浪微博
    public static String Xinlang="35";
    //趣头条平台
    public static String Qutoutiao="2674";
    //东方头条
    public static String Dongfang="74";

    //今日头条
    public static String JinRitoutiao="995";




    public static String user_token="00319528a0332712e7a8167910a1439ca3600bfa";
   //获取账户信息
   public static String get_userInfo="http://api.51ym.me/UserInterface.aspx?action=getaccountinfo&format=1&token=token"+user_token;

   //send code
    public static String get_mobile="http://api.51ym.me/UserInterface.aspx?action=getmobile&itemid={itemid}&token="+user_token;
    public static String  release_mobile="http://api.51ym.me/UserInterface.aspx?action=release&mobile={mobile}&itemid={itemid}&token="+user_token;
    public static String get_auth_code="http://api.51ym.me/UserInterface.aspx?action=getsms&mobile={mobile}&itemid={itemid}&token="+user_token;
    public static String check_authCode_status="http://api.51ym.me/UserInterface.aspx?action=getsendsmsstate&mobile={mobile}&itemid={itemid}&token="+user_token;
    public static  String releaseAll="http://api.51ym.me/UserInterface.aspx?action=releaseall&token="+user_token;

}
