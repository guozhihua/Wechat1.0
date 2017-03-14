package com.weixin.utils.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:httpclient帮助类
 */
public class HTTPClientUtils {

    protected static final Logger log = LoggerFactory.getLogger(HTTPClientUtils.class);

    //连接时间(单位毫秒)
    private static final Integer CONNECTION_TIMEOUT_TIME = 30000;
    //数据传输时间(单位毫秒)
    private static final Integer SO_TIMEOUT_TIME = 120000;
    /**
     * Description: post方式请求
     */
    public static String httpPostRequest(String url, String key, String value) throws ClientProtocolException, IOException {
//        log.debug(" ===============> url:" + url + " param key:" + key + " param value:" + value);
        if (StringUtils.isNotEmpty(key) && null != value) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(key, value);
            return httpPostRequest(url, paramMap);
        }
        return null;
    }

    public static String httpGetRequestJson(String url){
        HttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT_TIME);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT_TIME);

            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));

            return httpClient.execute(httpGet, baseHttpResponseHandlerJson());
        } catch (Exception e){
            log.error("httpGetRequestJson error:", e);
        } finally {
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return url;
    }

    /**
     * Description: post方式请求
     */
    public static String httpPostRequestJson(String url, String data) throws ClientProtocolException, IOException {
//        log.debug(" ===============> url:" + url + " param:" + data);

        HttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient();//new ThreadSafeClientConnManager()
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT_TIME);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT_TIME);

            HttpPost httppost = new HttpPost(url);
//			httppost.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
            httppost.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));

            if (StringUtils.isNotEmpty(data)) {

                httppost.setEntity(new StringEntity(data, "UTF-8"));
                //jackson object-->String json
            }
            return httpclient.execute(httppost, baseHttpResponseHandlerJson());
        } finally {
            if(httpclient!=null){
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * Description: post方式请求
     */
    public static String httpPostRequest(String url, Map<String, Object> paramMap) throws IOException {
//        log.debug(" ===============> url:" + url + " param:" + paramMap.size());
        HttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT_TIME);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT_TIME);
            HttpPost httppost = new HttpPost(url);
			httppost.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
//            httppost.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));

            if (null != paramMap && !paramMap.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Entry entry : paramMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                //jackson object-->String json
            }
            return httpclient.execute(httppost, baseHttpResponseHandler());
        } finally {
            if(httpclient!=null){
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    public static String httpPost(String url, Map<String, String> paramMap) throws IOException {
        //Gson gson = new Gson();
        //log.debug(" ===============> url:{} param:{}" ,url,gson.toJson(paramMap));
        HttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT_TIME);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT_TIME);
            HttpPost httppost = new HttpPost(url);
            
            if (null != paramMap && !paramMap.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Entry entry : paramMap.entrySet()) {
                    if(entry.getKey()!=null && entry.getValue()!=null)
                        nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            }
            return httpclient.execute(httppost, baseHttpResponseHandler());
        } finally {
            if(httpclient!=null){
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * Description: get方式请求
     */
    public static String httpGetRequest(String url, String key, String value) throws ClientProtocolException, IOException {
//        log.debug(" ===============> url:" + url + " param key:" + key + " param value:" + value);
        if (StringUtils.isNotEmpty(key) && null != value) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put(key, value);
            return httpGetRequest(url, paramMap);
        }
        return null;
    }

    /**
     * Description: get方式请求
     */
    @SuppressWarnings("rawtypes")
    public static String httpGetRequest(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
//        log.info(" ===============> url:" + url);
        HttpClient httpclient = null;
        try {
            if (null != paramMap && !paramMap.isEmpty()) {
                int i = 0;
                for (Entry entry : paramMap.entrySet()) {
                    url += ((i == 0) ? "?" + entry.getKey() + "=" + entry.getValue()
                            : "&" + entry.getKey() + "=" + entry.getValue());
                    i++;
                }
            }
            httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT_TIME);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT_TIME);
            HttpGet httpget = new HttpGet(url);
//			httpget.addHeader(new BasicHeader("Content-Type","application/x-www-form-urlencoded"));
            httpget.addHeader(new BasicHeader("Content-Type", "application/json; charset=utf-8"));

            return httpclient.execute(httpget, baseHttpResponseHandler());
        } finally {
            if(httpclient!=null){
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * Description: 设置基础ResponseHandler
     */
    public static ResponseHandler<String> baseHttpResponseHandler() {
        return new ResponseHandler<String>() {
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() >= 300) {
                    log.error("statusLine.getStatusCode() ------> " + statusLine.getStatusCode());
                    log.error("statusLine.getReasonPhrase() ------> " + statusLine.getReasonPhrase());
//                    throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();
                return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
            }
        };
    }
    /**
     * Description: json格式请求时 设置基础ResponseHandler
     */
    public static ResponseHandler<String> baseHttpResponseHandlerJson(){
        return new ResponseHandler<String>() {
            public String handleResponse(HttpResponse response) throws HttpResponseException {
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() >= 300) {
                    log.error("statusLine.getStatusCode() ------> " + statusLine.getStatusCode());
                    log.error("statusLine.getReasonPhrase() ------> " + statusLine.getReasonPhrase());
                     throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();

                try {
                    return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
    /**
     * put请求
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年5月30日 上午9:28:27
     * @param url   请求地址
     * @param json  json数据
     * @return
     * @throws IOException
     */
    public static String doPut(String url, String json) throws IOException 
	{
		String resultString = "";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT_TIME);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT_TIME);
		HttpPut httpPut = new HttpPut(url);
		httpPut.addHeader("Content-Type", "application/json");
		
		httpPut.setEntity(new StringEntity(json, HTTP.UTF_8));
		// httpPost.setEntity(new UrlEncodedFormEntity(parameters,HTTP.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPut);
		int httpCode = httpResponse.getStatusLine().getStatusCode();
		if (httpCode == HttpStatus.SC_OK)
		{
			resultString = EntityUtils.toString(httpResponse.getEntity());
		} else
		{
			resultString = httpResponse.getStatusLine().toString();
		}
		
		return resultString;
	}
    
    
    /**httpClient模拟表单提交
     * @author songwei
     * Date 2016-06-07
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static String httpPostForm(String url, Map<String, Object> paramMap) throws IOException {
        HttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,CONNECTION_TIMEOUT_TIME);
            httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,SO_TIMEOUT_TIME);
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader(new BasicHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8"));
            
            if (null != paramMap && !paramMap.isEmpty()) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Entry entry : paramMap.entrySet()) {
                    if(entry.getKey()!=null && entry.getValue()!=null)
                        nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
            }
            return httpclient.execute(httppost,baseHttpResponseHandler());
        } finally {
            if(httpclient!=null){
                httpclient.getConnectionManager().shutdown();
            }
        }
    }
}
