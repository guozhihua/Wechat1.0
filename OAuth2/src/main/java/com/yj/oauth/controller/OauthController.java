package com.yj.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/7/20.
 */
@RestController
@RequestMapping("oauth/")
public class OauthController {

    @RequestMapping("test")
    @ResponseBody
    public Map testController(){
        Map map= new HashMap();
        map.put("status",123);
        return   map;

    }

}
