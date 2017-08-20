package xchat.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
public class HttpUtils {

    public static JSONObject postForm(String url, Map<String, Object> headers, Map<String, Object> paramsMap) {
        JSONObject jsonObject = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        CloseableHttpResponse response = null;
        List<String> res = new ArrayList<>();
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    httppost.addHeader(key, headers.get(key).toString());
                }
            }
            if (null != paramsMap && !paramsMap.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry entry : paramsMap.entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null)
                        nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8")).getJSONObject("data");

        } catch (Exception ex) {
            ex.printStackTrace();

        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }


    public static String get(String url) {
        String jsonObject = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        CloseableHttpResponse response = null;
        List<String> res = new ArrayList<>();
        try {
            HttpGet httpGet = new HttpGet(url);
            response = httpclient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            jsonObject = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();

        }finally {
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

}
