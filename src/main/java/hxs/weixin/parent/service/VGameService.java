
	 
	 /** 
	 * <pre>项目名称:hxs_personaltutor_wechat 
	 * 文件名称:VGameService.java 
	 * 包名:hxs.weixin.parent.service 
	 * 创建日期:2016年11月26日下午3:28:30 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package hxs.weixin.parent.service;

import java.util.List;
import java.util.Map;

import hxs.weixin.parent.entity.VGame;
	
	 /** 
 * <pre>项目名称：hxs_personaltutor_wechat    
 * 类名称：VGameService    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年11月26日 下午3:28:30    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年11月26日 下午3:28:30    
 * 修改备注：       
 * @version </pre>    
 */

public interface VGameService {

	/**
	 * <pre>selectUserByUserCode(通过用户id查询用户信息)   
		 * 创建人：王亮 wanglmir@163.com   
		 * 创建时间：2016年11月26日 下午3:28:59    
		 * 修改人：王亮 wanglmir@163.com     
		 * 修改时间：2016年11月26日 下午3:28:59    
		 * 修改备注： 
		 * @param record
		 * @return
		 * @throws Exception</pre>
	 */
	public VGame selectUserByUserCode(VGame record) throws Exception;
	
	/**
     * <pre>saveVGame(添加信息)   
    	 * 创建人：王亮 wanglmir@163.com   
    	 * 创建时间：2016年11月26日 下午3:57:27    
    	 * 修改人：王亮 wanglmir@163.com     
    	 * 修改时间：2016年11月26日 下午3:57:27    
    	 * 修改备注： 
    	 * @param record</pre>
     */
    public void saveVGame(VGame record) throws Exception;
    /**
     * <pre>saveVGame(修改信息)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    public void updateVGameByUserId(VGame record) throws Exception;
    /**
     * <pre>saveVGame(查询用户当前排行榜)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    //public VGame selectVGameRankByUserId(VGame record) throws Exception;
    public Map selectVGameRankByUserId(VGame record) throws Exception;
    /**
     * <pre>saveVGame(查询全球排行榜)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    //public List<VGame> selectVGameRankList() throws Exception;
    public List<Map> selectVGameRankList() throws Exception;
}

	