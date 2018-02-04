import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weixin.utils.util.HTTPClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import xchat.service.iml.CommonPatternService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 志华 on 2018/2/4.
 */
public class HuangjinDarenTest {
    static String url ="https://dt.same.com/api/v1/answer/get-question";
    private static final RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
//            .setProxy(new HttpHost("localhost",8888))
            .build();
    private static final CommonPatternService COMMON_PATTERN = new CommonPatternService();


    /**
     * 提高百度的搜索准确率
     */
    @Test
    public  void testAnswerActive(){
        String jsonstring="{\"code\":0,\"msg\":\"ok\",\"data\":{\"question_id\":8,\"question_num\":12,\"question\":\"《水浒传》中绰号为“及时雨”的是哪个人物？\",\"options\":[{\"answer_id\":1,\"content\":\"A 宋江\"},{\"answer_id\":2,\"content\":\"B 武松 \"},{\"answer_id\":3,\"content\":\"C 鲁智深\"}],\"health\":0,\"alive\":0,\"expire\":10}}";
        JSONObject jsonObject= JSON.parseObject(jsonstring);
        if(jsonObject!=null&&jsonObject.getInteger("code")==0){
            JSONObject data = (JSONObject) jsonObject.get("data");
            String question=data.getString("question");
            JSONArray options = data.getJSONArray("options");
            String[]  answers =new String[options.size()];
            for (int j=0;j<answers.length;j++ ) {
                JSONObject opt = (JSONObject) options.get(j);
                String content = opt.getString("content");
                answers[j] = content;
            }
            String rs = null;
            try {
                rs = COMMON_PATTERN.run(question,answers);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(rs);
        }
    }

    @Test
    public void getQuestins(){
        String result =null;
        int count =10 ;
        for(int i =0 ;i<count;i++){
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(defaultRequestConfig);
                CloseableHttpClient client = HttpClients.createDefault();
                String respContent = null;
                Map<String, Object> params = new HashMap<>();
                params.put("notice",0);
                httpPost.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                httpPost.addHeader("Authorization","6067bb6c74fdbbb30b06484cd6f635b0c4e63f8d_3558905");
                if (params != null && !params.isEmpty()) {
                    net.sf.json.JSONObject jsonParam = new net.sf.json.JSONObject();
                    for (String key : params.keySet()) {
                        jsonParam.put(key, params.get(key));
                    }
                    Thread.sleep(1000);
                    StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");//解决中文乱码问题
                    httpPost.setEntity(entity);
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json");
                }
                HttpResponse resp = client.execute(httpPost);
//                InputStream inStream =     resp.getEntity().getContent();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));  //请注意这里的编码
//                StringBuilder strber = new StringBuilder();
//                String line = null;
//                while ((line = reader.readLine()) != null){
//                    strber.append(line );}
//                inStream.close();
//                System.out.println(unicodeToString(strber.toString()));

                resp.setHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
                System.out.println(resp.getStatusLine().getStatusCode());
                if (resp.getStatusLine().getStatusCode() == 200) {
                    HttpEntity he = resp.getEntity();
                    respContent = EntityUtils.toString(he, "UTF-8");
                    respContent=unicodeToString(respContent);
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

                        result = COMMON_PATTERN.run(question, answers);
                    }else{
                        result="这道题我不会做呀！";
                    }
                } else {
                    throw new IOException("请求失败" + resp);
                }
                client.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                System.out.println(result);
            }

        }




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
}
