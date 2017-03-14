package com.weixin.utils.util;

import java.io.File;

/**
 * 工程路径 工具类
 * 
 * @author pc
 *
 */
public class PathUtil {
    private static String classes;
    
    static{
    	classes= new PathUtil().getPath();
    }
	public static String getClassPath() {
		//System.out.println("【classes路径】"+classes);
		return classes;

	}

	private String getPath() {
		String path = this.getClass().getResource("/").getPath();
		File file = new File(path);
		String classesPath = file.toString() + File.separatorChar;
		return classesPath;
	}

	public static void main(String[] args) {
		System.out.println("PathUtil.main()" + getClassPath());
	}
}
