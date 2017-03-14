package com.weixin.services.service;


import com.weixin.utils.sys.weixinVO.WeChatConfigVo;

/**
 * Created by :Guozhihua
 * Date： 2016/11/28.
 */
public interface WeChatHttpService {
    public WeChatConfigVo getWeChatConfig(String url);
}
