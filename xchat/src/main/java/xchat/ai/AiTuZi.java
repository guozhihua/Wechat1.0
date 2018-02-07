package xchat.ai;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */
public class AiTuZi {
    //智能兔子
    public static String appId="10806145";
    public static String appKey="0ZyZ8tarhpsgrADQriTck4xP";
    public static String secretKey="Cu4srUxuXPmeTqFeMIRk8t5elppGxpCD";

    //智能熊猫
    public static String appId2="10806856";
    public static String appKey2="k9YWKICqhjjhovIA0XTGZvOr";
    public static String secretKey2="UuRt3PGezvMbkh2y9WAVdRbyvnU5nX2M";
    public AipNlp getTuZiCient(){
        AipNlp client = new AipNlp(appId, appKey, secretKey);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        return client;
    }
    public AipNlp getXMCient(){
        AipNlp client = new AipNlp(appId2, appKey2, secretKey2);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        return client;
    }
}
