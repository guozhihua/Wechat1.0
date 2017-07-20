package com.yj.oauth.service;

/**
 * Created by :Guozhihua
 * Date： 2017/7/20.
 */
public interface OAuthService {
    public void addAuthCode(String authCode, String username);// 添加 auth code
    public void addAccessToken(String accessToken, String username); // 添加 access token
    boolean checkAuthCode(String authCode); // 验证auth code是否有效
    boolean checkAccessToken(String accessToken); // 验证access token是否有效
    String getUsernameByAuthCode(String authCode);// 根据auth code获取用户名
    String getUsernameByAccessToken(String accessToken);// 根据access token获取用户名
    long getExpireIn();//auth code / access token 过期时间
    public boolean checkClientId(String appId);// 检查客户端id是否存在
    public boolean checkClientSecret(String appSecret);// 坚持客户端安全KEY是否存在
}
