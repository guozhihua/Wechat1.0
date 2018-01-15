package xchat.controller;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by :Guozhihua
 * Date： 2018/1/12.
 */
public class XGCode {



    public static int setCode(String inviteCode,int size ,int fistCode ) {
        if(fistCode<6000000){
            fistCode=7122000;
        }
        int lastnum = 0 ;
        String url1="http://service.h7tuho5mf.cn//api/invite_code/bind?cc=TG43109&lc=3e5563h89bab84l5&mtxid=f0b429348d78&devi=864284033024273&sid=30YvOEBbKXiCFDGdkFttt2xGmjqR5i26pdi0JJo8wmbNQg3A5Ai3i3&osversion=android_23&cv=CR1.2.00_Android&imei=86428203002O243&proto=8&conn=wifi&ua=vivovivoX7&logid=&icc=89890315750100596714&aid=9c44daa698745211&smid=&imsi=409110013449164&mtid=6592643e9abd9c877cb57d9037a1a60c&code="+inviteCode;
        for(int i =0;i<300;i++){
            if(lastnum==size||lastnum>size){
                break;
            }
                String cur =url1.concat("&uid=")+(fistCode+i);
                try {
                    String result = "";
                    StringBuilder json = new StringBuilder();
                    URL u = new URL(cur);
                    HttpURLConnection uc = (HttpURLConnection) u.openConnection();
                    uc.setDoOutput(true);
                    uc.setDoInput(true);
                    uc.setUseCaches(false);
                    uc.setRequestMethod("GET");
                    BufferedReader bd = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String s = null;
                    while ((s = bd.readLine()) != null) {
                        json.append(s);
                    }
                    bd.close();
                    JSONObject jsonObject=JSONObject.parseObject(json.toString());
                    System.out.println(jsonObject.toJSONString());
                   boolean f  =  jsonObject.toJSONString().contains("\"revivals\":1,\"bind_success\":1");
                    if(f){
                        lastnum++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return  lastnum;
    }

    public static void main(String[] args) {
        System.out.println(setCode("4H552",100,7430290));
    }
}
