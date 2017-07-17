package com.chat.sso.provider;

import com.chat.sso.service.UserService;
import com.weixin.entity.chat.User;
import org.springframework.beans.factory.annotation.Autowired;
import sso.provider.dubbo.SsoUserLoginService;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
public class SsoUserLoginServiceImpl implements SsoUserLoginService {

    @Autowired
    private UserService userService;


    @Override
    public User queryUserByTicket(String ticket) {
        User user = new User();
        user.setUserName("zhang san");
        try {
            User user1 = userService.selectByPrimaryKey("23423");
            System.out.println(user1);
            System.out.println("sso test..host is "+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return user;
    }
}
