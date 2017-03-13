package com.sooncode.sql;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.log4j.Logger;


/**
 * 读取Xml文件
 * 
 * @author pc
 *
 */
public class SqlXml {

	private static Logger logger = Logger.getLogger("SqlXml.class");
	/**
	 * xml 文件名
	 */
	private String xmlName;
	
	
    /**
     * 
     * @param xmlName xml文件的全路径名称
     */
	public SqlXml(String xmlName) {
		this.xmlName = xmlName;
	}

	/**
	 * 去掉字符串中的 回车、换行符、制表符
	 * 
	 * @param str
	 * @return 压缩后的字符串
	 */
	private String compressString(String str) {
		String temp = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			temp = m.replaceAll("");
		}
		return temp;
	}

	 
	/**
	 * 获取可执行SQL语句
	 * @param id xml文件中 节点的id属性值
	 * @param obs 参数载体(注入sql中的对象集 (可选) 或者一个Map对象)
	 * @return 可执行SQL语句
	 */
	public String getSql(String id,Object...obs) {
		 String xml =  readFile(this.xmlName);
		 ParaXml paraXml = new ParaXml(xml);
         String sql = paraXml.getValue(id);
         if(obs.length>0){
        	 sql = Para.replaceParameter(sql, obs);
        	 return compressString(sql);
        	 
         }else{
        	 return sql;
         }
	}
	/**
	 * 读文件
	 * 
	 * @param filePath
	 *            文件所在的路径
	 * @return 文件的内容
	 * @throws IOException
	 */
	private  String readFile(String filePath)  {
		File file = new File(filePath);
		if (!file.exists() || file.isDirectory()) {
			return null;
		}
		 
		BufferedReader br;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(file));
			String temp = null;
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
		} catch (Exception e) {
			logger.error("ParaXml error "+e);
		}
		return sb.toString();
	}
	
	 
}
