package sso.provider.dubbo;


import com.weixin.entity.chat.User;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
public interface SsoUserLoginService {
    public User queryUserByTicket(String ticket);


}
