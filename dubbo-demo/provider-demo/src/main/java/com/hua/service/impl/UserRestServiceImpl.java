package com.hua.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.test.WebModel;
import com.test.pojo.User;
import com.test.rpc.UserRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */
//服务提供配置

@Service(protocol = {"rest"}, group = "annotationConfig", validation = "true")
//路径
@Path("rest/user")
//配置请求头 json  text/html
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
//配置响应为 json utf-8
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})
@Component("userRest")
public class UserRestServiceImpl implements UserRestService {
    private static final Logger logger = LoggerFactory.getLogger(UserRestServiceImpl.class);

    //设置为GET请求
    @GET
    //追加路径
    @Path("{id : \\d+}")
    @Override
    public User getUser(@PathParam("id") int userId) {
        return new User(1, "张三丰");
    }

    @POST
    @Path("list")
    @Override
    public WebModel getUserList() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "指望rest"));
        users.add(new User(2, "指望2"));
        users.add(new User(3, "指望3"));
        users.add(new User(4, "指望4"));
        logger.info("获取到了{}条数据",users.size());
        return new WebModel().success(users);
    }
}
