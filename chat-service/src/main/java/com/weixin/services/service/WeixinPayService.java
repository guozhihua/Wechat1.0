package com.weixin.services.service;

import com.weixin.entity.chat.UnifiedOrder;
import com.weixin.entity.chat.UnifiedOrderReturn;
import com.weixin.services.WeixinUtil;
import com.weixin.utils.util.ParaXml;
import com.weixin.utils.util.PathUtil;
import com.weixin.utils.util.PropertiesUtil;

import java.util.TreeMap;

import org.springframework.stereotype.Service;

@Service
public class WeixinPayService {

	public static UnifiedOrderReturn unifiedorder(UnifiedOrder uo) {
		TreeMap<String, String> map = new TreeMap<>();
		map.put("appid", uo.getAppid());
		map.put("mch_id", uo.getMch_id());
		map.put("nonce_str", uo.getNonce_str());
		map.put("openid", uo.getOpenid());
		map.put("out_trade_no", uo.getOut_trade_no());
		map.put("body", uo.getBody());
		map.put("total_fee", uo.getTotal_fee());
		map.put("notify_url", uo.getNotify_url());
		map.put("trade_type", uo.getTrade_type());
		map.put("spbill_create_ip", uo.getSpbill_create_ip());
		String sign = WeixinUtil.getSign(map, uo.getKey());
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		xml.append("<appid>" + uo.getAppid() + "</appid>");
		xml.append("<mch_id>" + uo.getMch_id() + "</mch_id>");
		xml.append("<body><![CDATA[" + uo.getBody() + "]]></body>");// <![CDATA[诊断报告]]>
		xml.append("<openid>" + uo.getOpenid() + "</openid>");
		xml.append("<nonce_str>" + uo.getNonce_str() + "</nonce_str>");
		xml.append("<out_trade_no>" + uo.getOut_trade_no() + "</out_trade_no>");
		xml.append("<spbill_create_ip>" + uo.getSpbill_create_ip() + "</spbill_create_ip>");
		xml.append("<notify_url>" + uo.getNotify_url() + "</notify_url>");
		xml.append("<trade_type>" + uo.getTrade_type() + "</trade_type>");
		xml.append("<total_fee>" + uo.getTotal_fee() + "</total_fee>");
		xml.append("<sign><![CDATA[" + sign + "]]></sign>");
		xml.append("</xml>");

		PropertiesUtil properties = new PropertiesUtil(PathUtil.getClassPath() + "config/weixin.properties");
		String reslutXml = WeixinUtil.xmlPost(properties.getString("unifiedorderUrl"), xml.toString());
		System.out.println("==="+reslutXml);
		UnifiedOrderReturn uor = new UnifiedOrderReturn();
		ParaXml px = new ParaXml(reslutXml);
		uor.setResult_code(px.getValue("result_code"));
		uor.setReturn_code(px.getValue("return_code"));
		if (uor.getResult_code()!=null && uor.getResult_code().equals("SUCCESS") && uor.getReturn_code().equals("SUCCESS")) {
			uor.setAppid(px.getValue("appid"));
			uor.setMch_id(px.getValue("mch_id"));
			uor.setSign(px.getValue("sign"));
			uor.setPrepay_id(px.getValue("prepay_id"));
			uor.setTrade_type(px.getValue("trade_type"));
			uor.setReturn_msg(px.getValue("return_msg"));
			uor.setNonce_str(px.getValue("nonce_str"));
		}
		return uor;
	}
}
