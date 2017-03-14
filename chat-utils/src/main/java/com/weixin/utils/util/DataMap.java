package com.weixin.utils.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class DataMap{
	
	public Map getMap(HttpServletRequest request) {
		Map map = new HashMap();
		Map<String, String[]> tmp = request.getParameterMap();
		if (tmp != null) {
			for (String key : tmp.keySet()) {
				String[] values = tmp.get(key);
				map.put(key, values.length == 1 ? values[0].trim() : values);
			}
		}
		return map;
	}
	
}
