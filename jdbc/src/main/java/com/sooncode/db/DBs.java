package com.sooncode.db;

import com.sooncode.C3p0Utils;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;


/**
 * 数据库
 * 
 * @author pc
 *
 */
public class DBs {

	private static final DBs instance=new DBs();
	/** 源码所在路径 */
	private static String classesPath;



	public final static Logger logger = Logger.getLogger("DBs.class");

   static {
	   classesPath = instance.getClassesPath();

   }
	/**
	 * 获取连接
	 * @param dbKey
	 * @return
	 */
	public static synchronized Connection getConnection(String dbKey) {
		final Connection connection= C3p0Utils.getInstance().getConnection();
		try{
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		}catch (Exception ex){
			logger.error("get connect is exception :",ex);
		}
		logger.info("get connection from  c3p0 :"+connection.toString());
		return connection;
	}

	/**
	 * 关闭连接资源
	 * 
	 * @param objs 含有colse()方法的对象集合
	 * 
	 */
	public static void close(Object... objs) {

		for (Object obj : objs) {
			try {
				if (obj != null) {
					Method method = obj.getClass().getMethod("close");
					if (method != null) {
						method.invoke(obj);
					}
				}
			} catch (Exception e) {
				logger.error("[关闭数据库资源失败]",e);
			}
		}
		logger.info("return  c3p0  connection  ok !");
		C3p0Utils.getDesc();
	}


    /**
     * 获取 源码所在路径
     * @return
     */
	private String getClassesPath() {
		String path = this.getClass().getResource("/").getPath();
		File file = new File(path);
		String classesPath = file.toString() + File.separatorChar;
		return classesPath;

	}
}


 
 
