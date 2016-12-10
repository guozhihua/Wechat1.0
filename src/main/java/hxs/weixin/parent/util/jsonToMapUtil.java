package hxs.weixin.parent.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class jsonToMapUtil {

	/**
	 * gson将复杂json串转成map
	 * @param data
	 * @return
	 */
	public static Map<String, Object> parseData(String data){
       GsonBuilder gb = new GsonBuilder();
       Gson g = gb.create();
       Map<String, Object> map = g.fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
       return map;
	}
}
