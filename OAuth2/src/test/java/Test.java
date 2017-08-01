import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/7/24.
 */
public class Test {

    public static void main(String[] args)  {
        String  urkl ="http://pod.dsylove.com/user/nextUserDetail";
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        CloseableHttpResponse response =null;
        List<String> res=new ArrayList<>();
        try {
            HttpPost httppost = new HttpPost(urkl);
            httppost.addHeader("token", "402167221");
            httppost.addHeader("version", "3.3.3");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            for(int i=0;i<1000;i++){
                response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8")).getJSONObject("data");
                    if(jsonObject!=null&&jsonObject.containsKey("weixin")&&!jsonObject.getString("weixin").equals("对方未设置或不可见")){
                        String s ="姓名："+jsonObject.getString("nickName")+"   年龄："+jsonObject.getString("info1")+"  微信："+jsonObject.getString("weixin");
                        res.add(s);
                    }
                }
                response.close();

            }

        }catch (Exception ex){
            ex.printStackTrace();

        }finally {
            System.out.println("--------------------------------------");
            for(String s :res){
                System.out.println(s);
            }
        }

    }


}
