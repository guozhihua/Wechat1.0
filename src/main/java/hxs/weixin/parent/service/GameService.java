
	 
	 /** 
	 * <pre>项目名称:hxs_personaltutor_wechat 
	 * 文件名称:GameService.java 
	 * 包名:hxs.weixin.parent.service 
	 * 创建日期:2016年12月5日下午3:29:01 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package hxs.weixin.parent.service;
	
	 /** 
 * <pre>项目名称：hxs_personaltutor_wechat    
 * 类名称：GameService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月5日 下午3:29:01    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月5日 下午3:29:01    
 * 修改备注：       
 * @version </pre>    
 */

public interface GameService {

	/**
	 * <pre>shareGetVGameTimes(速算赢家分享加次数)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月5日 下午3:32:42    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月5日 下午3:32:42    
		 * 修改备注： 
		 * @param userId
		 * @throws Exception</pre>
	 */
	public void shareGetVGameTimes(String userId) throws Exception;
	
	/**
	 * <pre>shareGetWGameTimes(单词速拼分享加次数)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年12月5日 下午3:34:50    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年12月5日 下午3:34:50    
		 * 修改备注： 
		 * @param userId
		 * @throws Exception</pre>
	 */
	public void shareGetWGameTimes(String userId) throws Exception;
}

	