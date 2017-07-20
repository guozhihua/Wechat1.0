package com.weixin.utils.sys;

import com.weixin.utils.util.Md5;
import sun.misc.BASE64Encoder;

import java.util.Random;

/**
 * Created by :Guozhihua
 * Date： 2017/5/10.
 */
public class TokenUtils {
    static BASE64Encoder base64Encoder=new BASE64Encoder();


    public static String createSimpleToken(String end){
        String token = System.currentTimeMillis()+"HSDYw34"+new Random().nextInt(99999);
        token =  Md5.GetMD5Code(token);
        return "acTk".concat(base64Encoder.encode(token.getBytes()).toLowerCase()+"."+end);
    }
    public static String createAppCommonToken(String appId){
        String token = System.currentTimeMillis()+appId+new Random().nextInt(999999);
        token =  Md5.GetMD5Code(token);
        return "acTk".concat(base64Encoder.encode(token.getBytes()).toLowerCase());
    }

    public static String getUserToken(String userName,String password){
        String token = System.currentTimeMillis()+userName+new Random().nextInt(999999)+password;
        token =  Md5.GetMD5Code(token);
        return "acTk".concat(base64Encoder.encode(token.getBytes()).toLowerCase());
    }


}
