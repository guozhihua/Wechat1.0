package com.sooncode.db;

public class DB {
	
	/**数据源唯一标识码*/
	private String key;
	
	/**数据库驱动*/
	private String driver;
	
	/**数据库所在的主机IP*/
	private String ip;
	
	/**数据库端口*/
	private String port;
	
	/**数据库名称*/
	private String dataName;
	
	/**数据库使用的编码格式*/
	private String encodeing;
	
	/**数据库用户名*/
	private String userName;
	
	/**数据库密码*/
	private String password;

	
	//-----------------------------------------------------------------------------
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getEncodeing() {
		return encodeing;
	}

	public void setEncodeing(String encodeing) {
		this.encodeing = encodeing;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
