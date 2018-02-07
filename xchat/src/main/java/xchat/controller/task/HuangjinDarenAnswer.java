package xchat.controller.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weixin.utils.util.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 志华 on 2018/2/4.
 */
public class HuangjinDarenAnswer {
    static String url = "https://dt.same.com/api/v1/answer/get-question";
    public static Set<String> allQuestions = new HashSet<>();

    public static String authHeder = "de5a814762fe77fa8d9b3825d14a1de3478a5669_3558905";

    public static boolean getQuestion = true;

    public static void setAuthHeader(String auth) {
        authHeder = auth;
    }


    public static  String time1 =" 12:28:20";


    public static  String time2 =" 19:58:20";


    private static final RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
//            .setProxy(new HttpHost("localhost",8888))
            .build();


    public static String getQuestins() throws Exception {
        String result = null;
        if (!getQuestion) {
            return result;
        }
        String shortDateStr = com.weixin.utils.util.DateUtils.getShortDateStr();
        long currentTime= com.weixin.utils.util.DateUtils.getCurrentTime();
        String t1 =shortDateStr.concat(time1);
        String t2 =shortDateStr.concat(time2);
        long date1 = DateUtils.parseDate(t1).getTime();
        long date2 = DateUtils.parseDate(t2).getTime();
        long date11 =date1+38*60&1000;
        long date12 =date2+38*60&1000;
        //在直播时间内
        if((currentTime>date1&&currentTime<date11)||(currentTime>date2&&currentTime<date12)){
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(defaultRequestConfig);
                CloseableHttpClient client = HttpClients.createDefault();
                String respContent = null;
                Map<String, Object> params = new HashMap<>();
                params.put("notice", 0);
                httpPost.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                httpPost.addHeader("Authorization", authHeder);
                setPostEntity(httpPost, params);
                HttpResponse resp = client.execute(httpPost);
                resp.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                if (resp.getStatusLine().getStatusCode() == 200) {
                    HttpEntity he = resp.getEntity();
                    respContent = EntityUtils.toString(he, "UTF-8");
                    respContent = unicodeToString(respContent);
                    System.out.println(respContent);
                    JSONObject jsonObject = JSON.parseObject(respContent);
                    if (jsonObject != null && jsonObject.getInteger("code") == 0) {
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        String question = data.getString("question");
                        JSONArray options = data.getJSONArray("options");
                        String[] answers = new String[options.size()];
                        for (int j = 0; j < answers.length; j++) {
                            JSONObject opt = (JSONObject) options.get(j);
                            String content = opt.getString("content");
                            answers[j] = content;
                        }
                        result = question;
                    } else if (jsonObject != null && jsonObject.getInteger("code") == 2) {
                        result = "999999";
                    } else {
                        result = "000000";
                    }
                } else {
                    result = "000000";
                }
                client.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                result = "000000";
            }
        }else{
            result="unstart";
        }
        return result;
    }

    private static void setPostEntity(HttpPost httpPost, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            net.sf.json.JSONObject jsonParam = new net.sf.json.JSONObject();
            for (String key : params.keySet()) {
                jsonParam.put(key, params.get(key));
            }
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");//解决中文乱码问题
            httpPost.setEntity(entity);
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
        }
    }


    /**
     * unicode转中文
     *
     * @param str
     * @return
     * @author yutao
     * @date 2017年1月24日上午10:33:25
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch + "");

        }

        return str;

    }


}
