package com.chat.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by :Guozhihua
 * Date： 2017/5/26.
 */
@Controller
public class SsoController extends  BaseController{
    /**
     * 跳转至登录页面
     */
    @RequestMapping(value="/index")
    public String saveUserVoucher(){
        return "login";
    }

}
