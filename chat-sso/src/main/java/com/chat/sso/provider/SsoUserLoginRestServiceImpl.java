package com.chat.sso.provider;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.weixin.entity.chat.User;
import org.springframework.stereotype.Service;
import sso.provider.rest.SsoUserLoginRestService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.

##@Path：整体服务的访问路径
        本样例http访问就应该是：  http:/IP:PORT/应用名称/contextpath/users
        contextpath：web.xml和resf服务中定义的
        ##可以接受的数据格式。json和简单xml
 */
@Path("users")
//@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})

public class SsoUserLoginRestServiceImpl implements SsoUserLoginRestService {


    @GET
    @Path("getUser")
    @Override
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    public User queryUserByTicket(@QueryParam("ticket") String ticket) {
        try {
            System.out.println("rest fule=========== "+ticket);
            System.out.println(" resiter sso test..host is "+ InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUserName(" rest");
        return user;
    }

    //    @POST
//    @Path("register")
//    public User queryUserByTicket(User user) {
//        user.setUserName("zhang san rest");
//        try {
//            System.out.println(" resiter sso test..host is "+ InetAddress.getLocalHost().getHostAddress());
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }


    @Path("checkId/{id}")
    @GET
    @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    @Override
    public User checkIp(@PathParam("id") Long id,@Context HttpServletRequest request) {
        System.out.println("Client address is " + request.getRemoteAddr());
        return new User();
    }
}
