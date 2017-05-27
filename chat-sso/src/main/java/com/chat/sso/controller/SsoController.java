package com.chat.sso.controller;

import com.alibaba.dubbo.common.json.JSONObject;
import com.weixin.entity.chat.User;
import org.springframework.web.bind.annotation.*;

/**
 * Created by :Guozhihua
 * Date： 2017/5/26.
 */
@RestController
public class SsoController extends  BaseController{
    /**
     * 跳转至登录页面
     */
    @RequestMapping(value="/index")
    public String saveUserVoucher(){
        return "login";
    }

    @RequestMapping(value = "/person/{id:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    User getPerson(@PathVariable("id") int id) {
        User person = new User();
        person.setUserName("张三");
        return person;
    }

    @RequestMapping(value = "/person/{id:\\d+}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deletePerson(@PathVariable("id") int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "删除人员信息成功");
        return jsonObject;
    }

}
