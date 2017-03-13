package com.sooncode.cache;

import com.sooncode.utils.Md5;
import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * JDBC 缓存
 * @author pc
 *
 */
public class Cache {
	public final static Logger logger = Logger.getLogger("Cache.class");
	
	public final static int MAX_SIZE =100;
	public static Map<String,Map<String,Object>>  cache = new HashMap<>() ;
	

	public static void put(Class<?> clas,List<?> list){
		
		Map<String,Object> newMap = new HashMap<>();
		
		for (Object object : list) {
			String key = Md5.GetMD5Code(objectToString(object));
			newMap.put(key, object);
		}
		
		 
		Map<String,Object> cacheMap =  cache.get(clas.getName());
		if(cacheMap == null ){
			cacheMap = new HashMap<>();
		}
		int oldSize= cacheMap.size();
		int thisSize = list.size();
		if(oldSize + thisSize <= MAX_SIZE){
			cacheMap.putAll(newMap);
			cache.put(clas.getName(), cacheMap);
			logger.debug("【添加数据到缓存】" +list);
		}else {
			logger.debug("【超出缓存最大容量】清空缓存");
			cache.remove(clas.getName());
		}
		
	}
	
	
	public static List<?> get(Object obj){
		String name = obj.getClass().getName();
		 
		//--------获取obj的属性名称和值-----------
		Map<String, Object> objMap = getFieldAndValue(obj);
		
		Map<String,Object> map = cache.get(name);
		if(map==null){
			return new ArrayList<>();
		}
		
		List<?> list = new ArrayList<>(map.values());
		List<Object> newList = new ArrayList<>();
		
		for (Object o : list) {
			Map<String, Object> oMap = getFieldAndValue(o);
			Boolean bool = true;
			for(Entry<String, Object> en : oMap.entrySet()){
				String key = en.getKey();
				Object value = en.getValue();
				
				Object oldValue = objMap.get(key);
				if(oldValue !=null &&  !value.equals(oldValue)){
					bool = bool && false;
				}
			}
			
			if(bool){
				newList.add(o);
			}
		}
		logger.debug("【从缓存中获取数据】" +newList);
		return newList;
		
	}
	
	public static void remove (Class<?> clas){
		String  name = clas.getName();
		cache.remove(name);
		logger.debug("【从缓存中清空数据】" + name);
		
	}
	
	private static String objectToString(Object obj){
		Map<String ,Object> map = getFieldAndValue(obj);
		String str = "";
		for(Entry<String, Object> en : map.entrySet()){
			str = str + en.getKey() + ":" + en.getValue() +";";
		}
		return str;
	}
	
	private static Map<String ,Object> getFieldAndValue(Object obj){
		String str = "Integer Long Short Byte Float Double Character Boolean Date String";
		Map<String, Object> map = new HashMap<>();
		Class<?> c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			// logger.debug(field.getName());
			if (!field.getName().equals("serialVersionUID") && str.contains(field.getType().getSimpleName())) {
				Object value =invokeGetMethod(obj,field.getName()); 
				if(value != null){
					map.put(field.getName(),value);
				}
			}
		}
		return map;
	}
	
	private static Object invokeGetMethod(Object obj, String fieldName) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, obj.getClass());
			// 获得set方法
			Method method = pd.getReadMethod();
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}  

	}
}
