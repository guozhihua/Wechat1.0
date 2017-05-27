package com.chat.sso.provider;

import com.weixin.entity.chat.User;
import sso.provider.SsoUserLoginService;
import sun.net.NetworkClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
public class SsoUserLoginServiceImpl implements SsoUserLoginService {
    @Override
    public User queryUserByTicket(String ticket) {
        User user = new User();
        user.setUserName("zhang san");
        try {
            System.out.println("sso test..host is "+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return user;
    }
}
