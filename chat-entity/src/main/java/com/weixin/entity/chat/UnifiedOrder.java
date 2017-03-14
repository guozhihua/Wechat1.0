package com.weixin.entity.chat;

/**
 * 统一下单   发送参数模型
 * @author pc
 *
 */
public class UnifiedOrder {
 
 
	/**
	 * 公众账号ID
	 */
	private String appid;
	/**
	 * 商品描述
	 */
	private String body ;
	
	/**
	 * 商户号
	 */
	private String mch_id ;
	
	/**
	 * 随机字符串
	 */
	private String nonce_str ;
	
	/**
	 * 通知地址
	 */
	private String notify_url ;
	
	/**
	 * 商户订单号
	 */
	private String out_trade_no ;
	
	/**
	 * 终端IP
	 */
	private String spbill_create_ip ;
	
	
	/**
	 * 总金额
	 */
	private String total_fee;
	
	/**
	 * 交易类型
	 */
	private String trade_type;
	
	/**
	 * API密钥
	 */
	private String key;
	
	/**
	 * 用户openid
	 */
	private String openid;
	
 
	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public String getAppid() {
		return appid;
	}


	public void setAppid(String appid) {
		this.appid = appid;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getMch_id() {
		return mch_id;
	}


	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}


	public String getNonce_str() {
		 
		return this.nonce_str;
	}



	public String getNotify_url() {
		return notify_url;
	}


	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}


	public String getOut_trade_no() {
		return out_trade_no;
	}


	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}


	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}


	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}


	public String getTotal_fee() {
		return total_fee;
	}


	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}


	public String getTrade_type() {
		return trade_type;
	}


	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
 

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	
	
	
}
