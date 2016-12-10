
	 
	 /** 
	 * <pre>项目名称:hxs_personaltutor_wechat 
	 * 文件名称:GameServiceImpl.java 
	 * 包名:hxs.weixin.parent.service.impl 
	 * 创建日期:2016年12月5日下午3:29:39 
	 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre> 
	 */
	 
	package hxs.weixin.parent.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hxs.weixin.parent.controller.GameController;
import hxs.weixin.parent.entity.VGame;
import hxs.weixin.parent.entity.WGame;
import hxs.weixin.parent.service.GameService;
import hxs.weixin.parent.service.PUserVoucherService;
import hxs.weixin.parent.service.UserService;
import hxs.weixin.parent.service.VGameService;
import hxs.weixin.parent.service.WGameService;
	
	 /** 
 * <pre>项目名称：hxs_personaltutor_wechat    
 * 类名称：GameServiceImpl    
 * 类描述：    
 * 创建人：王亮 wanglmir@163.com    
 * 创建时间：2016年12月5日 下午3:29:39    
 * 修改人：王亮 wanglmir@163.com    
 * 修改时间：2016年12月5日 下午3:29:39    
 * 修改备注：       
 * @version </pre>    
 */
@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
    private VGameService vGameService;
    @Autowired
    private WGameService wGameService;
    @Autowired
    private UserService userService;
    @Autowired
    private PUserVoucherService pUserVoucherService;

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

	
	 /* (non-Javadoc)    
	 * @see hxs.weixin.parent.service.GameService#shareGetVGameTimes(java.lang.String)    
	 */
		 
	@Override
	public void shareGetVGameTimes(String userId) throws Exception {
		// TODO Auto-generated method stub
		if (null != userId && 0 < userId.length()) {
			VGame vGame = new VGame();
			vGame.setUserId(userId);
			try {
				VGame userVGame = vGameService.selectUserByUserCode(vGame);
				userVGame.setOverplusTimes(userVGame.getOverplusTimes() + 3l);
				vGameService.updateVGameByUserId(userVGame);
			} catch (Exception e) {
				logger.error("访问数据库失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		}
	}

	
	 /* (non-Javadoc)    
	 * @see hxs.weixin.parent.service.GameService#shareGetWGameTimes(java.lang.String)    
	 */
		 
	@Override
	public void shareGetWGameTimes(String userId) throws Exception {
		// TODO Auto-generated method stub
		if (null != userId && 0 < userId.length()) {
			WGame wGame = new WGame();
			wGame.setUserId(userId);
			try {
				WGame userWGame = wGameService.selectUserByUserCode(wGame);
				userWGame.setOverplusTimes(userWGame.getOverplusTimes() + 3l);
				wGameService.updateWGameByUserId(userWGame);
			} catch (Exception e) {
				logger.error("访问数据库失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		}	
	}
    
    
}

	