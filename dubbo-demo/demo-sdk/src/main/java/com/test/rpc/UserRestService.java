package com.test.rpc;

import com.test.pojo.User;

import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */
public interface UserRestService {

    public User getUser(int userId);

    public List<User> getUserList();

}
