package com.sooncode.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
 * MD5 算法
*/
public class Md5 {

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public static String getMD5Code(Object object) {
		String str = "Integer Long Short Byte Float Double Character Boolean Date String";
	 
		Class<?> c = object.getClass();
		Field[] fields = c.getDeclaredFields();
		String toString = new String ();
		for (Field field : fields) {
			if (str.contains(field.getType().getSimpleName())) {
				try {
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), object.getClass());
					Method method = pd.getReadMethod();
					Object value = method.invoke(object);
					toString = toString + field.getName() + value +";";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return GetMD5Code(toString);
	}

	public static void main(String[] args) {
		System.out.println(Md5.GetMD5Code("123"));
		
	 
	}
}