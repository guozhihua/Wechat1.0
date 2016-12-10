package hxs.weixin.parent.entity;

/**
 * 统一下单 接口返回参数模型
 * 
 * @author pc
 *
 */
public class UnifiedOrderReturn {

	/**
	 * 公众账号ID
	 */
	private String appid;

	/**
	 * 返回状态码
	 */
	private String return_code;
	

	/**
	 * 返回信息
	 */
	private String return_msg;

	/**
	 * 商户号
	 */
	private String mch_id;

	/**
	 * 随机字符串
	 */
	private String nonce_str;

	/**
	 * 业务结果
	 */
	private String result_code;

	/**
	 * 预支付交易会话标识
	 */
	private String prepay_id;

	/**
	 * 交易类型
	 */
	private String trade_type;

	/**
	 * 签名
	 */
	private String sign;

	
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "{\"appid\":\"" + appid + "\",\"return_code\":\"" + return_code + "\",\"return_msg\":\"" + return_msg + "\",\"mch_id\":\"" + mch_id + "\",\"nonce_str\":\"" + nonce_str + "\",\"result_code\":\"" + result_code + "\",\"prepay_id\":\"" + prepay_id + "\",\"trade_type\":\"" + trade_type + "\",\"sign\":\"" + sign + "\"}  ";
	}
	
	
}
