
	 
	 /** 
	 * <pre>项目名称:hxs_personaltutor_wechat 
	 * 文件名称:VGameServiceImpl.java 
	 * 包名:hxs.weixin.parent.service.impl 
	 * 创建日期:2016年11月26日下午3:29:39 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package hxs.weixin.parent.service.impl;

import java.util.List;
import java.util.Map;

import hxs.weixin.parent.dao.VGameMapper;
import hxs.weixin.parent.entity.VGame;
import hxs.weixin.parent.service.VGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	
	 /** 
 * <pre>项目名称：hxs_personaltutor_wechat    
 * 类名称：VGameServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年11月26日 下午3:29:39    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年11月26日 下午3:29:39    
 * 修改备注：       
 * @version </pre>    
 */
@Service
public class VGameServiceImpl implements VGameService {

	@Autowired
	private VGameMapper vGameMapper;

	
	 /*     
	 *  由用户id查询信息   
	 */
		 
	@Override
	public VGame selectUserByUserCode(VGame record) throws Exception {
		// TODO Auto-generated method stub
		VGame selectUserByUserCode = vGameMapper.selectUserByUserCode(record);
		return selectUserByUserCode;
			
	}
	
	 /*     
	 * 添加信息   
	 */
		 
	@Override
	public void saveVGame(VGame record) throws Exception {
		// TODO Auto-generated method stub
		vGameMapper.saveVGame(record);
	}

	 /*     
	 * 修改信息    
	 */
		 
	@Override
	public void updateVGameByUserId(VGame record) throws Exception {
		// TODO Auto-generated method stub
		vGameMapper.updateVGameByUserId(record);	
	}

	
	 /*   
	 * 查询用户当前排行榜    
	 */
		 
	/*@Override
	public VGame selectVGameRankByUserId(VGame record) throws Exception {
		// TODO Auto-generated method stub
		VGame selectVGameRankByUserId = vGameMapper.selectVGameRankByUserId(record);
		return selectVGameRankByUserId;
			
	}*/
	@Override
	public Map selectVGameRankByUserId(VGame record) throws Exception {
		// TODO Auto-generated method stub
		Map selectVGameRankByUserId = vGameMapper.selectVGameRankByUserId(record);
		return selectVGameRankByUserId;
		
	}

	
	 /*   
	 * 查询全球排行榜    
	 */
		 
	@Override
	/*public List<VGame> selectVGameRankList() throws Exception {
		// TODO Auto-generated method stub
		List<VGame> selectVGameRankList = vGameMapper.selectVGameRankList();
		return selectVGameRankList;
			
	}*/
	public List<Map> selectVGameRankList() throws Exception {
		// TODO Auto-generated method stub
		List<Map> selectVGameRankList = vGameMapper.selectVGameRankList();
		return selectVGameRankList;
		
	}
	
	
}

	