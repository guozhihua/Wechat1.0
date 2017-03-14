package com.weixin.cache.redis;

import com.google.gson.Gson;
import com.weixin.utils.util.*;
import com.weixin.utils.weixinVo.TicketVo;
import com.weixin.utils.weixinVo.TokenVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

public class WeixinUtil {
    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static final String WX_ACCESS_TOKEN = "wx_base_access_token";
    public static final String WX_JSAPI_TICKET = "wx_base_jsAPI_ticket";

    private static final Logger logger = Logger.getLogger(WeixinUtil.class);

    public  static String appid = "";
    public  static String weixinSecret = "";
    public  static String access_token_url = "";
    public  static String js_api_ticket_url = "";


    static {
        PropertiesUtil properties = new PropertiesUtil(PathUtil.getClassPath() + "config/weixin.properties");
        appid = properties.getString("weixinappid");
        weixinSecret = properties.getString("weixinSecret");
        access_token_url = properties.getString("tokenServiceUrl");
        js_api_ticket_url = properties.getString("ticketServiceUrl");
    }


    public static String xmlPost(String urlStr, String xml) {
        try {
            URL url = new URL(urlStr);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            if (null != xml) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(xml.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容  
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            return buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * 获取签名
     *
     * @param map    参加签名算法的字段
     * @param apiKey API密钥
     * @return
     */
    public static String getSign(TreeMap<String, String> map, String apiKey) {
        String string = "";
        for (Entry<String, String> en : map.entrySet()) {
            string = string + en.getKey() + "=" + en.getValue() + "&";
        }
        string = string + "key=" + apiKey;

        string = Md5.GetMD5Code(string).toUpperCase();
        return string;
    }

    public static TokenVo getAccessToken() {
       RedisClientTemplate redisClientTemplate =(RedisClientTemplate) SpringBeanUtils.getBean("redisClientTemplate");
        String tokenJson =redisClientTemplate.get(WX_ACCESS_TOKEN);
        TokenVo object =null;
        if(org.springframework.util.StringUtils.isEmpty(tokenJson)){
            initWxAccessToken();
            tokenJson =redisClientTemplate.get(WX_ACCESS_TOKEN);
        }
        object=new Gson().fromJson(tokenJson,TokenVo.class);
        return object;
    }

    /**
     * (微信)获取ticket
     *
     * @param tokenVo
     * @return
     * @date 2016年01月9日下午1:32:32
     */
    public static TicketVo getTicketVo(TokenVo tokenVo) {
        RedisClientTemplate redisClientTemplate =(RedisClientTemplate) SpringBeanUtils.getBean("redisClientTemplate");
        String tokenJson =redisClientTemplate.get(WX_JSAPI_TICKET);
        TicketVo object =null;
        if(org.springframework.util.StringUtils.isEmpty(tokenJson)){
            initWxAccessToken();
            tokenJson =redisClientTemplate.get(WX_JSAPI_TICKET);
        }
        object=new Gson().fromJson(tokenJson,TicketVo.class);
        return object;
    }

    /**
     * 在获取不到access_token之后，重新生成token
     */
    public static void initWxAccessToken() {

        Map<String, String> tokenValueMap = new HashMap<String, String>();
        tokenValueMap.put("appid", appid);
        tokenValueMap.put("secret", weixinSecret);
        tokenValueMap.put("grant_type", "client_credential");
        try {
            String tokenMssage = HTTPClientUtils.httpGetRequest(access_token_url, tokenValueMap);
            TokenVo tokenVo = new Gson().fromJson(tokenMssage, TokenVo.class);
            TicketVo ticketVo = null;
            if (tokenVo != null && tokenVo.getAccessToken() != null) {
                Map<String, String> ticketValueMap = new HashMap<String, String>();
                ticketValueMap.put("access_token", tokenVo.getAccessToken());
                ticketValueMap.put("type", "jsapi");
                String message = HTTPClientUtils.httpGetRequest(js_api_ticket_url, ticketValueMap);
                ticketVo = new Gson().fromJson(message, TicketVo.class);
            }
            if (tokenVo != null && ticketVo != null && StringUtils.isNotEmpty(ticketVo.getTicket())) {
                RedisClientTemplate redisClientTemplate =(RedisClientTemplate) SpringBeanUtils.getBean("redisClientTemplate");
                redisClientTemplate.setex(WX_ACCESS_TOKEN,6000,new Gson().toJson(tokenVo));
                redisClientTemplate.setex(WX_JSAPI_TICKET,6000,new Gson().toJson(ticketVo));
            }
        } catch (Exception ex) {
            logger.error("获取微信access_token ,jsApi_Ticket 失败", ex);
        }
    }
}
