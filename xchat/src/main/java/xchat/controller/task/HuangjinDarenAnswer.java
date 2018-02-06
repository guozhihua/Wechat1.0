package xchat.controller.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    static String url ="https://dt.same.com/api/v1/answer/get-question";
    public static  Set<String> allQuestions =new HashSet<>();

    public   static String authHeder="de5a814762fe77fa8d9b3825d14a1de3478a5669_3558905" ;

   public   static void  setAuthHeader(String auth){
       authHeder=auth;
   }


    private static final RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
//            .setProxy(new HttpHost("localhost",8888))
            .build();


    public  static String getQuestins(){
        String result =null;
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(defaultRequestConfig);
                CloseableHttpClient client = HttpClients.createDefault();
                String respContent = null;
                Map<String, Object> params = new HashMap<>();
                params.put("notice",0);
                httpPost.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                httpPost.addHeader("Authorization",authHeder);
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
                HttpResponse resp = client.execute(httpPost);
                resp.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                if (resp.getStatusLine().getStatusCode() == 200) {
                    HttpEntity he = resp.getEntity();
                    respContent = EntityUtils.toString(he, "UTF-8");
                    respContent=unicodeToString(respContent);
                    System.out.println(respContent);
                    JSONObject jsonObject= JSON.parseObject(respContent);
                    if(jsonObject!=null&&jsonObject.getInteger("code")==0){
                       JSONObject data = (JSONObject) jsonObject.get("data");
                        String question=data.getString("question");
                        JSONArray options = data.getJSONArray("options");
                        String[]  answers =new String[options.size()];
                        for (int j=0;j<answers.length;j++ ) {
                            JSONObject opt= (JSONObject) options.get(j);
                            String content = opt.getString("content");
                            answers[j]=content;
                        }
                        result =question;
                    }else{
                        result="000000";
                    }
                } else {
                    result="000000";
                    throw new IOException("请求失败" + resp);
                }
                client.close();
            }catch (Exception ex){
                ex.printStackTrace();
                result="000000";
            }
        return result;
    }




    /**
     * unicode转中文
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

            str = str.replace(matcher.group(1), ch+"" );

        }

        return str;

    }
    public static void main(String[] args) {
        getQuestins();
    }
}
