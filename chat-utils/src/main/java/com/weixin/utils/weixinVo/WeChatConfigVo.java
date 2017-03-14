package com.weixin.utils.weixinVo;

import java.io.Serializable;

public class WeChatConfigVo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String weChatTimestamp;
	private String weChatNonceStr;
	private String weChatSignature;
	private String weChatAppId;
	
	public String getWeChatTimestamp() {
		return weChatTimestamp;
	}
	public void setWeChatTimestamp(String weChatTimestamp) {
		this.weChatTimestamp = weChatTimestamp;
	}
	public String getWeChatNonceStr() {
		return weChatNonceStr;
	}
	public void setWeChatNonceStr(String weChatNonceStr) {
		this.weChatNonceStr = weChatNonceStr;
	}
	public String getWeChatSignature() {
		return weChatSignature;
	}
	public void setWeChatSignature(String weChatSignature) {
		this.weChatSignature = weChatSignature;
	}
	public String getWeChatAppId() {
		return weChatAppId;
	}
	public void setWeChatAppId(String weChatAppId) {
		this.weChatAppId = weChatAppId;
	}
}
