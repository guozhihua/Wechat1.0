package com.test.rpc;

import com.test.WebModel;
import com.test.pojo.Menu;
import com.test.pojo.User;

import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */
public interface UserRestService {

    public User getUser(int userId);

    public WebModel getUserList();

    public WebModel getMenus();

}
