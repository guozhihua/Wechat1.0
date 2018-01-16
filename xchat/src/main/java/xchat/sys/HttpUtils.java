package xchat.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
    private static final RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();

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


    /**
     * JSON
     *
     * @throws IOException
     * @throws RuntimeException
     */
    public static String postJson(String url, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(defaultRequestConfig);
        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;
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
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();

            respContent = EntityUtils.toString(he, "UTF-8");
        } else {
            throw new IOException("请求失败" + resp);
        }
        client.close();
        return respContent;
    }
}
