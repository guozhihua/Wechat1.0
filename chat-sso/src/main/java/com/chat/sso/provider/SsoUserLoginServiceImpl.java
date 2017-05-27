package com.chat.sso.provider;

import com.weixin.entity.chat.User;
import sso.provider.SsoUserLoginService;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
public class SsoUserLoginServiceImpl implements SsoUserLoginService {
    @Override
    public User queryUserByTicket(String ticket) {
        User user = new User();
        user.setUserName("zhang san");
        return user;
    }
}
