package sso.provider.rest;


import com.weixin.entity.chat.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
public interface SsoUserLoginRestService {
    public User queryUserByTicket(String ticket);

    public User checkIp(Long id , HttpServletRequest request);
}
