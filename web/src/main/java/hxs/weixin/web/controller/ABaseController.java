package hxs.weixin.web.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
public  abstract  class ABaseController  {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;



    //默认的缓存有效期
    public int DEFAULT_TIME_OUT=60*60*2;
    public int DEFAULT_MAX_TIME_OUT=60*60*24;



    protected Gson gson = new Gson();

}
