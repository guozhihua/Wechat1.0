package com.yj.oauth.service.impl;

import com.yj.oauth.entity.OApps;
import com.yj.oauth.service.OAppsService;
import com.yj.oauth.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by :Guozhihua
 * Date： 2017/7/20.
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private OAppsService appsService;

    @Override
    public void addAuthCode(String authCode, String username) {

    }

    @Override
    public void addAccessToken(String accessToken, String username) {

    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return false;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return false;
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return null;
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return null;
    }

    @Override
    public long getExpireIn() {
        return 3600*2;
    }

    @Override
    public boolean checkClientId(String appId) {
        OApps oApps = appsService.findByClientId(appId);
        if (oApps != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean checkClientSecret(String appSecret) {
        OApps oApps = appsService.findByClientSecret(appSecret);
        if (oApps != null) {
            return true;
        }
        return false;
    }
}
