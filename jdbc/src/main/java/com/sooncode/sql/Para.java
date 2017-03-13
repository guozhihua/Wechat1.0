package com.sooncode.sql;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * SQL中的参数注入
 * 
 * @author pc
 *
 */
public class Para {

	private static Logger logger = Logger.getLogger("Para.class");

	/**
	 * 替换预SQL中的参数 获得可执行的SQL
	 * 
	 * @param preparedSql
	 *            预编译SQL(必须)
	 * @param args
	 *            注入sql中的对象集 (可选) 或者一个Map
	 * @return 可执行的SQL
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static String replaceParameter(String preparedSql, Object... args) {
		logger.debug("【预编译SQL】: "+preparedSql);
		Map<String, Object> map = new HashMap<>();

		if (args.length == 1 && args[0].getClass().getSuperclass().getName().equals("java.util.AbstractMap")) {
			map = (Map<String, Object>) args[0];
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = "${" + entry.getKey() + "}";
				Object value = entry.getValue();
				preparedSql = preparedSql.replace(key, value.toString());
			}
		} else {

			for (Object obj : args) {
				Class<?> cls = obj.getClass();
				Field[] field = cls.getDeclaredFields();
				for (Field f : field) {
					f.setAccessible(true);
					try {
						if (f.get(obj) != null) {
							map.put("${" + f.getName() + "}", f.get(obj).toString());
						} else {
							map.put("${" + f.getName() + "}", "null");
						}
					} catch (IllegalArgumentException e) {
						logger.error("Para error "+e);
					} catch (IllegalAccessException e) {
						logger.error("Para error "+e);
					}
				}
			}

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				preparedSql = preparedSql.replace(key, value.toString());
			}
		}
		//logger.debug("【可执行SQL】: "+preparedSql);
		return preparedSql;
	}

}
