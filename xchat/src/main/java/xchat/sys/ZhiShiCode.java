package xchat.sys;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by :Guozhihua
 * Date： 2018/1/12.
 */
public class ZhiShiCode {



    public static int setCode(String inviteCode,int size ,int fistCode ) {
        if(fistCode<6000000){
            fistCode=7122000;
        }
        int lastnum = 0 ;
        String url1="http://service.h7tuho5mf.cn/api/invite_code/bind?" +
                "cc=TG43909&" +
                "lc=32182257247424ed&" +
                "mtxid=f0b429338d78&" +
                "devi=864282030024243&" +
                "sid=30jQqfJCZ6oBVdFlJuvnnOIi1AxnnZggTG49Gv9G1dMuGBkHAi3i3" +
                "&osversion=android_23&" +
                "cv=CR1.2.00_Android&" +
                "imei=864282030024243&proto=8&" +
                "conn=wifi&ua=vivovivoY67&logid=&icc=89860315750100536714&" +
                "aid=9c44daa398745111&" +
                "smid=&" +
                "imsi=460110013449164" +
                "&mtid=6592643e9abd9c879cb57d9a37a1a69c" +
                "&code="+inviteCode;
        for(int i =0;i<10;i++){
            if(lastnum==size||lastnum>size){
                break;
            }
                String cur =url1.concat("&uid=")+(7050031);
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
                   boolean f  =  jsonObject.toJSONString().contains("\"bind_success\":1");
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
        System.out.println(setCode("WU9SH",1,9234310));
    }
}
