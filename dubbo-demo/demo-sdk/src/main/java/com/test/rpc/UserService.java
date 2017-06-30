package com.test.rpc;

import com.test.pojo.User;

import java.util.List;

/**
 *
 * 用户test
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */
public interface UserService {

    public User getUser(int userId);

    public List<User> getUserList();



}
