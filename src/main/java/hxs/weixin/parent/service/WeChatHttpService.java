package hxs.weixin.parent.service;

import hxs.weixin.parent.entity.vo.WeChatConfigVo;

/**
 * Created by :Guozhihua
 * Date： 2016/11/28.
 */
public interface WeChatHttpService {
    public WeChatConfigVo getWeChatConfig(String url);
}
