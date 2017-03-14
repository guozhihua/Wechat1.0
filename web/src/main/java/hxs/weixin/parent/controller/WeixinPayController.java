package hxs.weixin.parent.controller;

import java.util.*;


import com.weixin.entity.chat.Order;
import com.weixin.entity.chat.UnifiedOrder;
import com.weixin.entity.chat.UnifiedOrderReturn;
import com.weixin.services.service.OrderService;
import com.weixin.services.service.WeixinPayService;
import com.weixin.utils.responsecode.BaseResponse;
import com.weixin.utils.responsecode.ResponseCode;
import com.weixin.utils.sys.MethodLog;
import com.weixin.utils.util.DataMap;
import com.weixin.utils.util.HttpServletStream;
import com.weixin.utils.util.Md5;
import com.weixin.utils.util.OrderUtil;
import com.weixin.utils.util.ParaXml;
import com.weixin.utils.util.PathUtil;
import com.weixin.utils.util.PropertiesUtil;
import com.weixin.utils.util.SoonJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pay/")
public class WeixinPayController {
	
	private static final Logger log = LoggerFactory.getLogger(WeixinPayController.class);
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private WeixinPayService weixinPayService;

	/**
	 * 支付购买产品
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("payProduct")
	@MethodLog
	public @ResponseBody BaseResponse payProduct(DataMap data, HttpServletRequest request, HttpServletResponse response){
		log.info("=============开始购买===============");
		BaseResponse baseResponse = new BaseResponse();
		try {
			Map paramMap = data.getMap(request);
			if (paramMap != null && !paramMap.isEmpty()) {
				if (paramMap.get("projectId") == null || paramMap.get("projectId").toString().equals("")) {//商品名称
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "projectId");
				}
				if (paramMap.get("projectDesc") == null || paramMap.get("projectDesc").toString().equals("")) {//商品描述
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "projectDesc");
				}
				if (paramMap.get("totalAmout") == null || paramMap.get("totalAmout").toString().equals("")) {//总金额
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "totalAmout");
				}
				if (paramMap.get("projectName") == null || paramMap.get("projectName").toString().equals("")) {//商品名称
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "projectName");
				}
				if (paramMap.get("spbill_create_ip") == null || paramMap.get("spbill_create_ip").toString().equals("")) {//ip地址
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "spbill_create_ip");
				}
				if (paramMap.get("openid") == null || paramMap.get("openid").toString().equals("")) {//openid
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "openid");
				}
				if (paramMap.get("buyType") == null || paramMap.get("buyType").toString().equals("")) {//购买类型
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "buyType");
				}
				if (paramMap.get("userId") == null || paramMap.get("userId").toString().equals("")) {//user_id
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
				}
				if (paramMap.get("amount") == null || paramMap.get("amount").toString().equals("")) {//代金券金额
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "amount");
				}
				Order order = new Order();
				order.setProjectId(paramMap.get("projectId").toString());
				String totalAmout = paramMap.get("totalAmout").toString();
				order.setTotalAmout(totalAmout);
				order.setdescription(paramMap.get("projectDesc").toString());
				order.setOutTradeNo(OrderUtil.getOrderNumber(""));
				order.setCreateTime(new Date());
				order.setOrderDate(new Date());
				order.setSerialNumber(getSerialNumber());
				order.setState("0");
				order.setUpdateTime(new Date());
				order.setUserUniqueId(paramMap.get("openid").toString());
				String buyType = paramMap.get("buyType").toString();
				order.setBuyType(Integer.parseInt(buyType));
				Long endTime = System.currentTimeMillis();
				Integer effectiveTime = 0;
				String buyTestStr = null;
//				if (buyType.equals("1")) {
//					order.setProjectName(paramMap.get("projectName").toString()+" - 作业辅导");
//					buyTestStr = "作业辅导";
//					Project p = this.projectService.selectByPrimaryKey(Integer.parseInt(paramMap.get("projectId").toString()));
//					effectiveTime = p.getEffectiveTime();
//				} else {
//					buyTestStr = "考试帮手";
//					PTestProject p = this.pTestProjectService.queryById(paramMap.get("projectId").toString());
//					effectiveTime = p.getEffectiveTime();
//				}
				endTime += effectiveTime * 24 * 60 * 60 *1000l;
				order.setEndTime(new Date(endTime));
				order.setCouponAmout(paramMap.get("amount").toString());//代金券金额
				order.setCashAmout(totalAmout);
//				if(Integer.parseInt(paramMap.get("amount").toString())>0){
//					String puserVoucherId =pUserVoucherService.updateVoucherByUserIdAndAmount(paramMap.get("userId").toString(), Integer.parseInt(paramMap.get("amount").toString()));
//					if(puserVoucherId!=null){
//						order.setPuserVoucherId(puserVoucherId);
//					}else{
//						baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"下单失败，没有对用的代金券。");
//						return  baseResponse;
//					}
//				}
				orderService.insertSelective(order);
				UnifiedOrder UnifiedOrder = new UnifiedOrder();
				PropertiesUtil properties = new PropertiesUtil(PathUtil.getClassPath() + "config/weixin.properties");
				UnifiedOrder.setAppid(properties.getString("weixinappid"));
				UnifiedOrder.setMch_id(properties.getString("weixinpartnerid"));
				UnifiedOrder.setKey(properties.getString("weixinpartnerKey"));
				UnifiedOrder.setBody(buyTestStr + paramMap.get("projectName").toString());
				UnifiedOrder.setNotify_url(properties.getString("notify_url"));
				UnifiedOrder.setOut_trade_no(order.getOutTradeNo());
				log.info("openid==========" + paramMap.get("openid").toString());
				UnifiedOrder.setOpenid(paramMap.get("openid").toString());
				UnifiedOrder.setNonce_str(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				UnifiedOrder.setSpbill_create_ip(paramMap.get("spbill_create_ip").toString());
				UnifiedOrder.setTotal_fee((int) (Double.valueOf(totalAmout) * 100) + "");
				UnifiedOrder.setTrade_type("JSAPI");
				log.info("开始生成预支付id===========================");
				UnifiedOrderReturn uor = weixinPayService.unifiedorder(UnifiedOrder);
				/**生成 预支付编号**/
				String prepayid = uor.getPrepay_id();
				log.info("prepayid==========" + prepayid);
				String packag = "Sign=WXPay";
				String noncestr = UUID.randomUUID().toString().replace("-", "").toUpperCase();
				Long timestamp = new Date().getTime() / 1000;
				/**使用此TreeMap用于排序**/
				TreeMap<String, String> map = new TreeMap<String, String>();
				map.put("appid", properties.getString("weixinappid"));
				map.put("partnerid", properties.getString("weixinpartnerid"));
				map.put("prepayid", prepayid);
				map.put("package", packag);
				map.put("noncestr", noncestr);
				map.put("timestamp", timestamp.toString());
				String signString = "appId=" + properties.getString("weixinappid") + "&nonceStr=" + noncestr + ""
						+ "&package=prepay_id=" + prepayid + "&signType=MD5&timeStamp=" + timestamp.toString() + "&key=" + properties.getString("weixinpartnerKey") + "";
				map.put("paysign", Md5.GetMD5Code(signString).toUpperCase());
				Map<String, Object> rMap = new LinkedHashMap<>();
				rMap.put("appid", map.get("appid"));
				rMap.put("partnerid", map.get("partnerid"));
				rMap.put("prepayid", map.get("prepayid"));
				rMap.put("package", map.get("package"));
				rMap.put("noncestr", map.get("noncestr"));
				rMap.put("timestamp", map.get("timestamp"));
				rMap.put("paysign", map.get("paysign"));
				rMap.put("outTradeNo", order.getOutTradeNo());
				String rjson = SoonJson.getJson(rMap);
				baseResponse.setResult(rjson);
				log.info("下单成功==============");
			}
		}catch (Exception ex){
			log.error("payProduct  下单失败",ex);
			baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
			return  baseResponse;
		}
		return baseResponse;
	}
	
	/**
	 * 内部支付
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("payInnerProduct")
	@MethodLog
	public @ResponseBody BaseResponse payInnerProduct(DataMap data,HttpServletRequest request,HttpServletResponse response){
		BaseResponse baseResponse = new BaseResponse();
		try {
			Map paramMap = data.getMap(request);
			if (paramMap != null && !paramMap.isEmpty()) {
				if (paramMap.get("projectId") == null || paramMap.get("projectId").toString().equals("")) {//商品名称
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "projectId");
				}
				if (paramMap.get("projectDesc") == null || paramMap.get("projectDesc").toString().equals("")) {//商品描述
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "projectDesc");
				}
				if (paramMap.get("totalAmout") == null || paramMap.get("totalAmout").toString().equals("")) {//总金额
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "totalAmout");
				}
				if (paramMap.get("projectName") == null || paramMap.get("projectName").toString().equals("")) {//商品名称
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "projectName");
				}
				if (paramMap.get("openid") == null || paramMap.get("openid").toString().equals("")) {//openid
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "openid");
				}
				if (paramMap.get("buyType") == null || paramMap.get("buyType").toString().equals("")) {//购买类型
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "buyType");
				}
				if (paramMap.get("userId") == null || paramMap.get("userId").toString().equals("")) {//user_id
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
				}
				if (paramMap.get("amount") == null || paramMap.get("amount").toString().equals("")) {//代金券金额
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "amount");
				}
				Order order = new Order();
				order.setProjectId(paramMap.get("projectId").toString());
				String totalAmout = paramMap.get("totalAmout").toString();
				totalAmout = (int) (Double.valueOf(totalAmout) * 100) + "";
				order.setTotalAmout(totalAmout);
				order.setdescription(paramMap.get("projectDesc").toString());
				order.setOutTradeNo(OrderUtil.getOrderNumber(""));
				order.setCreateTime(new Date());
				order.setOrderDate(new Date());
				order.setSerialNumber(getSerialNumber());
				order.setProjectName(paramMap.get("projectName").toString());
				order.setState("1");
				order.setUpdateTime(new Date());
				order.setUserUniqueId(paramMap.get("openid").toString());
				String buyType = paramMap.get("buyType").toString();
				order.setBuyType(Integer.parseInt(buyType));
				Long endTime = System.currentTimeMillis();
				Integer effectiveTime = 0;
//				if (buyType.equals("1")) {
//					Project p = this.projectService.selectByPrimaryKey(Integer.parseInt(paramMap.get("projectId").toString()));
//					effectiveTime = p.getEffectiveTime();
//				} else {
//					PTestProject p = this.pTestProjectService.queryById(paramMap.get("projectId").toString());
//					effectiveTime = p.getEffectiveTime();
//				}
				endTime += effectiveTime * 24 * 60 * 60 *1000l;
				order.setEndTime(new Date(endTime));
				order.setCouponAmout(paramMap.get("amount").toString());//代金券金额
				order.setCashAmout(totalAmout);
//				if(Integer.parseInt(paramMap.get("amount").toString())>0){
//					String puserVoucherId =pUserVoucherService.updateVUserVoucherInner(paramMap.get("userId").toString(), Integer.parseInt(paramMap.get("amount").toString()));
//					if(puserVoucherId!=null){
//						order.setPuserVoucherId(puserVoucherId);
//					}else{
//						baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"下单失败，没有对用的代金券。");
//						return  baseResponse;
//					}
//				}
				orderService.insertSelective(order);
				baseResponse.setResult(order.getOutTradeNo());
			}else{
				baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"下单失败，没有对用的代金券。");
				return  baseResponse;
			}
			}catch(Exception ex){
				log.error("innser pay product error ",ex);
				baseResponse.isFail(ResponseCode.PARAMETER_INVALID,"下单失败，没有对用的代金券。");
				return  baseResponse;
			}
		return baseResponse;
	}
	
	/**
	 * 微信支付回调(回复微信服务器防止多次回调)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "notify", method = RequestMethod.POST)
	@ResponseBody
	public void notify(HttpServletRequest request, HttpServletResponse response,Order order) {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin","*");
		log.info("进入回调url============");
		try {
			String xml = HttpServletStream.getString(request);
			ParaXml px = new ParaXml(xml);
			String return_code = px.getValue("return_code");
			order.setOutTradeNo(px.getValue("out_trade_no"));
			if (return_code.equals("SUCCESS")) {
				order.setState("1");
				orderService.updateByOrderNo(order);
				log.info("支付成功============");
			} else {
				log.error("支付失败");
			}
		}catch (Exception ex){
			log.error("微信支付回调 notify error",ex);
			return;
		}
		String r = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		HttpServletStream.putString(r, response);
	}
	
	/**
	 * 判断用户是否第一次购买
	 * @param order
	 * @param data
	 * @return
	 */
	@RequestMapping("isLastPay")
	@MethodLog
	public @ResponseBody BaseResponse isLastPay(Order order,DataMap data,HttpServletRequest request){
		BaseResponse baseResponse = new BaseResponse();
		Map map = data.getMap(request);
		if(!map.isEmpty() || map!=null){
			if(map.get("openid") == null || map.get("openid").toString().equals("")){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "openid");
			}
			if(map.get("buyType") == null || map.get("buyType").toString().equals("")){
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "buyType");
			}
			order.setUserUniqueId(map.get("openid").toString());
			order.setBuyType(Integer.parseInt(map.get("buyType").toString()));
			List<Order> orders = orderService.getByUserId(order);
			if (orders != null && orders.size() > 0) {
				if (orders.size() == 1) {
					baseResponse.setMessage("last");
				}else{
					baseResponse.setMessage("manyTimes");
				}
			}
		}
		return baseResponse;
	}
	
	/**
	 * 生成流水号
	 * @return
	 */
	private synchronized String  getSerialNumber() {
		String a = (int)((Math.random()*9+1)*100000)+"";
		String b = System.currentTimeMillis()+"";
		return "YJLT"+b+a;
	}
	
}
