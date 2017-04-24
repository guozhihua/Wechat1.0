package com.yj.ws.service;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * 一般情况下，使用post方式进行接口限制，防止在页面直接输入url调用。
 * Created by :Guozhihua
 * Date： 2017/4/20.
 */
@Component
@WebService(endpointInterface = "com.yj.ws.service.IsService",serviceName = "helloService",targetNamespace = "http://service.ws.yj.com/")
public class HelloService implements IsService {
    @Override
    public String sayHi(String text) {
        return "hello " + text;
    }

    @Override
    public App getAppById(String appId) {
        App app = new App(12,"apple");
        return app;
    }
}
