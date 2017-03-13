package com.sooncode.ref;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型
 * @author pc
 *
 */
public class Genericity {
    /**
     * 获取泛型的全类名
     * @param thisClass
     * @param index
     * @return
     */
	public static String  getGenericity(Class<?> thisClass,int index){
		
		Type type = thisClass.getGenericSuperclass(); // 非常关键的一步
		ParameterizedType parameterizedType = (ParameterizedType) type;// ParameterizedType:表示参数化类型
		Class<?> TClass = (Class<?>) parameterizedType.getActualTypeArguments()[index]; // getActualTypeArguments():
		String tClassName = TClass.getName(); // 泛型T实际运行时的全类名
		
		return tClassName;
	}
	
	public static String getSimpleName(String allClassName){
		String [] strs = allClassName.split("\\.");
		return strs[strs.length -1];
	}
}
