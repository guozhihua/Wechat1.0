package com.hua.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.hua.dao.MenuDao;
import com.test.WebModel;
import com.test.pojo.Menu;
import com.test.pojo.User;
import com.test.rpc.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */

//服务提供配置
@Service(protocol = {"dubbo"}, group = "annotationConfig", validation = "true")
@Component
public class UserServiceImpl  implements UserService {
    @Override
    public User getUser(int userId) {
        return new User(1,"张三丰");
    }

    @Autowired
    private MenuDao menuDao;

    /**
     * 必须使用setter
     * @param menuDao
     */
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }


    @Override
    public List<User> getUserList() {
        List<User> users = new ArrayList<>();
        users.add(new User(1,"dubbo1"));
        users.add(new User(2,"指望2"));
        users.add(new User(3,"指望3"));
        users.add(new User(4,"指望4"));
        return  users;
    }

    @Override
    public WebModel getMenus() {
        List<Menu> menuList = menuDao.getChildListMenu(0);
        return new WebModel().success(menuList);
    }
}
