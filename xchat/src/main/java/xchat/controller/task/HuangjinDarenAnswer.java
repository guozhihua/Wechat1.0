package xchat.controller.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weixin.utils.util.DateUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xchat.pojo.Question;
import xchat.service.DataDicService;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 志华 on 2018/2/4.
 */
@Component
public class HuangjinDarenAnswer {
    static String url = "https://dt.same.com/api/v1/answer/get-question";
    public static Set<String> allQuestions = new HashSet<>();

    public static boolean getQuestion = true;


    private static String auth="hjdr_auth";
    private static String time1="hjdr_time1";
    private static String time2="hjdr_time2";

    @Autowired
    private DataDicService dataDicService;

    private static final RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
//            .setProxy(new HttpHost("localhost",8888))
            .build();


    public  Question getQuestins() throws Exception {
        Question question1 =null;
        if (!getQuestion) {
            return question1;
        }
        //在直播时间内
        if (isTime()){
             question1 = new Question();
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(defaultRequestConfig);
                CloseableHttpClient client = HttpClients.createDefault();
                String respContent = null;
                Map<String, Object> params = new HashMap<>();
                params.put("notice", 0);
                httpPost.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                httpPost.addHeader("Authorization", dataDicService.queryValueByCode(auth).getValue());
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
                        question=question.replace("\n","");
                        question=question.replace("\u2028","");
                        question1.setQuestion(question);
                        question1.setStatus("200");
                        question1.setOptions(StringUtils.join(answers,"#"));
                        return question1;
                    } else if (jsonObject != null && jsonObject.getInteger("code") == 2) {
                        question1.setStatus("999999");
                    } else {
                        question1.setStatus("000000");
                    }
                } else {
                    question1.setStatus("000000");
                }
                client.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                question1.setStatus("000000");
            }
        } else {
           return question1;
        }
        return question1;
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






    public  boolean isTime() throws ParseException {
        String shortDateStr = com.weixin.utils.util.DateUtils.getShortDateStr().concat(" ");
        long currentTime = com.weixin.utils.util.DateUtils.getCurrentTime();
        String t1 = shortDateStr.concat(dataDicService.queryValueByCode(time1).getValue());
        String t2 = shortDateStr.concat(dataDicService.queryValueByCode(time2).getValue());
        long date1 = DateUtils.parseDate(t1).getTime();
        long date2 = DateUtils.parseDate(t2).getTime();
        long date11 = date1 + 30 * 60 * 1000;
        long date12 = date2 + 30 * 60 * 1000;
       if((currentTime > date1 && currentTime < date11) || (currentTime > date2 && currentTime < date12)){
           return true ;
       }
        return false ;
    }







}
