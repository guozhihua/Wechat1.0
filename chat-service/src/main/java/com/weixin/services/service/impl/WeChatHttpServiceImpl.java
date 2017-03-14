package com.weixin.services.service.impl;


import com.weixin.utils.sys.weixinVO.WeChatConfigVo;
import com.weixin.services.service.WeChatHttpService;
import com.weixin.utils.sys.weixinVO.TicketVo;
import com.weixin.utils.sys.weixinVO.TokenVo;
import com.weixin.utils.util.WeixinUtil;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

@Component("WeChatHttpServiceImpl")
public class WeChatHttpServiceImpl implements WeChatHttpService {
	/**
	 * 微信分享需要初始化一些数据，详情见前段js文件
	 * 把这个对象的四个字段填充到js页面
	 * @return
	 */
	public WeChatConfigVo getWeChatConfig(String  url){
		//(微信)获取access_token
		TokenVo tokenVo = WeixinUtil.getAccessToken();
		//(微信)获取ticket【根据tokenVo获取ticketVo】
		TicketVo ticketVo = WeixinUtil.getTicketVo(tokenVo);
		//(微信)获取签名signature
		Map<String, String> valueMap = getTargetMap(ticketVo,url);
		WeChatConfigVo configVo = new WeChatConfigVo();
		configVo.setWeChatNonceStr(valueMap.get("noncestr"));
		configVo.setWeChatSignature(valueMap.get("signature"));
		configVo.setWeChatTimestamp(valueMap.get("timestamp"));
		configVo.setWeChatAppId(WeixinUtil.appid);
		return configVo;
	}

	/**
	 * (微信)获取签名signature
	 * @param ticketVo
	 * @return
	 * @date 2016年01月9日下午1:33:02
	 */
	private Map<String, String> getTargetMap(TicketVo ticketVo,String url){
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("jsapi_ticket", ticketVo.getTicket());
		valueMap.put("noncestr", UUID.randomUUID().toString());
		valueMap.put("timestamp", Long.toString(System.currentTimeMillis() / 1000));
		valueMap.put("url", url);
		StringBuffer strBuf = new StringBuffer();
		Iterator it = valueMap.entrySet().iterator();
		while(it.hasNext()){
			Entry entry = (Entry) it.next();
			strBuf.append("&" + entry.getKey() + "=" + entry.getValue());
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
		}
		md.reset();
		try {
			md.update(strBuf.toString().substring(1).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		String signature = bytesToHexString(md.digest());
		valueMap.put("signature", signature);
		return valueMap;
	}
	
	/**
	 * 
	 * byte转为String
	 * @param src
	 * @return
	 * @date 2016年01月6日下午4:48:25
	 */
	private String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString();   
	}
}
