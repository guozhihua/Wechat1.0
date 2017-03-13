package hxs.weixin.parent.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

 

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Json 处理类
 * 
 * @author pc
 *
 */
public class SoonJson {
	/**
	 * 获取 Json 字符串
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getJson(Object obj) {
		Map<String, Object> map = new HashMap<>();
		String className = obj.getClass().getName();
		if (className.contains("java.util.") && className.contains("Map")) {
			map = (Map<String, Object>) obj;
		} else {
			map = getFiledAndValue(obj);
		}
		return getJson4Map(map);
	}

	private static String getJson4Map(Map<String, Object> map) {

		StringBuffer json = new StringBuffer();
		json.append("{");
		int n = 0;
		for (Entry<String, Object> en : map.entrySet()) {

			String key = en.getKey();
			if (n > 0) {
				json.append(",\"" + key + "\":");
			} else {
				json.append("\"" + key + "\":");
			}
			Object value = en.getValue();
			String type = "";
			if (value != null) {
				type = value.getClass().getSimpleName();
			} else {
				json.append(value);
				continue;
			}

			if (type.equals("Date") || type.equals("Timestamp")) {
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				System.out.println("SoonJson.getJson4Map()--"+date);
				json.append("\"" + date + "\"");
			} else if (type.equals("String")) {
				if (isJsonArray(value.toString()) || isJsonObject(value.toString())) {
					json.append(value);
				} else {
					json.append("\"" + value + "\"");
				}
			} else {
				json.append(value);
			}

			n++;
		}

		json.append("}");

		return json.toString();
	}

	public static String getJsonArray(List<Map<String, Object>> list) {
		StringBuffer json = new StringBuffer();
		json.append("[");

		for (int i = 0; i < list.size(); i++) {
			String str = getJson(list.get(i));
			if (i == 0) {
				json.append(str);
			} else {
				json.append("," + str);
			}

		}
		json.append("]");
		return json.toString();
	}

	public static String getJson4String(Map<String, String> map) {

		StringBuffer json = new StringBuffer();
		json.append("{");
		int n = 0;
		for (Entry<String, String> en : map.entrySet()) {

			String key = en.getKey();
			if (n > 0) {
				json.append(",\"" + key + "\":");
			} else {
				json.append("\"" + key + "\":");
			}
			Object value = en.getValue();
			String type = "";
			if (value != null) {
				type = value.getClass().getSimpleName();
			} else {
				json.append(value);
				continue;
			}

			if (type.equals("Date") || type.equals("Timestamp")) {
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				System.out.println("SoonJson.getJson4Map()--"+date);
				json.append("\"" + date + "\"");
			} else if (type.equals("String")) {
				if (isJsonArray(value.toString()) || isJsonObject(value.toString())) {
					json.append(value);
				} else {
					json.append("\"" + value + "\"");
				}
			} else {
				json.append(value);
			}

			n++;
		}

		json.append("}");

		return json.toString();
	}

	
	/**
	 * 
	 * @param jsonString 避免key中有class关键字
	 * @param key
	 * @return
	 */
	public static String getValue(String jsonString, String key) {
		if(jsonString==null){
			return null;
		}
	 
		String[] keys = key.split("\\.");
		JSONObject jsonRoot = null;
		try{
			 jsonRoot = JSONObject.fromObject(jsonString);
			
		}catch(Exception e) {
			return null;
		}
		if (keys.length == 1) {
			Object obj = jsonRoot.get(keys[0]);
			return obj==null?null:obj.toString();
		} else {
			
			String newKeys = new String();
			for(int i = 1;i<keys.length;i++){
				if(i==1){
					newKeys = keys[i];
				}else{
					newKeys = newKeys + "."+keys[i];
				}
			}
			//-----------------------------------
			String thisKey = keys[0];
			
			if(thisKey.contains("[")&&thisKey.contains("]")){
				int start = thisKey.indexOf("[");
				int end = thisKey.indexOf("]");
				String number = thisKey.substring(start+1, end);
				int num = Integer.valueOf(number);
				thisKey =thisKey.replace("["+num+"]", "") ;
				JSONArray jsonArray = JSONArray.fromObject(jsonRoot.get(thisKey));
				if(num<jsonArray.size()){
					JSONObject obj = (JSONObject) jsonArray.get(num);
					return getValue(obj.toString(),newKeys);
				}else{
					return null;
				}
				
			}else{
				
				Object obj = jsonRoot.get(keys[0]);
				if(obj==null){
					return null;
				}else{
					String value = obj.toString();
					return getValue(value,newKeys) ;
					
				}
			}
			
		 
			 
		}

		 

	}

	
	public static String remove(String jsonData, String key){
		String json = new String ();
		if(jsonData == null){
			return null;
		}
		
		if(isJsonObject(jsonData)){
			JSONObject object = JSONObject.fromObject(jsonData);
			object.remove(key);
			json=object.toString();
			return json;
		} else{
			return jsonData;
		}
		
		
	}
	
	
	
	/** 获取对象的属性和其对应的值 */
	private static Map<String, Object> getFiledAndValue(Object obj) {
		Map<String, Object> map = new HashMap<>();
		Class<?> c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			String type = field.getType().getSimpleName();
			if (!field.getName().equals("serialVersionUID") && isFamiliarType(type)) {
				PropertyDescriptor pd;
				try {
					pd = new PropertyDescriptor(field.getName(), obj.getClass());
					Method method = pd.getReadMethod();
					Object value = method.invoke(obj);
					map.put(field.getName(), value);
				} catch (Exception e) {
				}
			}
		}
		return map;
	}

	private static Boolean isFamiliarType(String type) {
		switch (type) {
		case "String":
			return true;
		case "Integer":
			return true;
		case "Boolean":
			return true;
		case "Byte":
			return true;
		case "Short":
			return true;
		case "Character":
			return true;
		case "Long":
			return true;
		case "Float":
			return true;
		case "Double":
			return true;
		case "Date":
			return true;
		default:
			return false;
		}
	}

	private static boolean isJsonObject(String json) {
		try {
			JSONObject.fromObject(json);

			return true;
		} catch (Exception e) {

			return false;
		}

	}

	private static boolean isJsonArray(String json) {
		try {
			JSONArray.fromObject(json);
			return true;
		} catch (Exception e) {

			return false;
		}

	}

	public static void main(String[] args) {

//		Persion p = new Persion();
//
//		p.setId(12);
//		p.setName("[{\"knowledge_name\":\"八年级上册\",\"ctb_code\":\"69\"},{\"knowledge_name\":\"八年级下册\",\"ctb_code\":\"70\"}]");
//
//		// String json = getJson(p);
//
//		// System.out.println(json);
//
//		Map<String, Object> map = new HashMap<>();
//		map.put("id", 12);
//		map.put("clazz","{\"clazzName\":\"八年级一班\",\"AA\":{\"BB\":\"bb\"}}");
//		map.put("name", "[{\"knowledge_name\":\"八年级上册\",\"ctb_code\":\"69\"},{\"knowledge_name\":\"八年级下册\",\"ctb_code\":\"70\"}]");
//		String json = getJson(map);
//
//		String value = getValue(json, "name[1].knowledge_name");
//
//		//System.out.println(value);
//		
//		String ss = remove(json, "name");
//		System.out.println(ss);
//	
		}

}
