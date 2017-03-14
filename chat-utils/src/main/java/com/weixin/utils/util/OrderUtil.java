package com.weixin.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {
	/**
	 * 生成订单号
	 * 
	 * @param preFixString
	 * @return
	 */
	public static  synchronized String getOrderNumber(String preFixString) {

		 Date today = new Date();
		 int orderIndex = 0;
		Date n = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = outFormat.format(n);

		if (orderIndex > 0) {
			if (n.getYear() == today.getYear() && n.getMonth() == today.getMonth() && n.getDay() == today.getDay()) {
				orderIndex += 1;
			} else {
				today = n;
				orderIndex = 1;
			}
		} else {
			today = n;
			orderIndex = 1;
		}
		if (orderIndex > 999999) {
			orderIndex = 1;
		}
		String indexString = String.format("%s%06d", currTime, orderIndex);
		String orderNumberString = preFixString + indexString;
		return orderNumberString;
	}
	
}
