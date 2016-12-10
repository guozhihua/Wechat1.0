/**
 * <pre>项目名称:hxs_personaltutor_wechat
 * 文件名称:GameController.java
 * 包名:hxs.weixin.parent.controller
 * 创建日期:2016年11月26日下午3:31:30
 * Copyright (c) 2016, wanglmir@163.com All Rights Reserved.</pre>
 */

package hxs.weixin.parent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import hxs.weixin.parent.entity.PUserVoucher;
import hxs.weixin.parent.entity.User;
import hxs.weixin.parent.entity.VGame;
import hxs.weixin.parent.entity.WGame;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.PUserVoucherService;
import hxs.weixin.parent.service.UserService;
import hxs.weixin.parent.service.VGameService;
import hxs.weixin.parent.service.WGameService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.sys.enums.VoucherWayEnum;
import hxs.weixin.parent.util.DataMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>项目名称：hxs_personaltutor_wechat
 * 类名称：GameController
 * 类描述：
 * 创建人：王亮 wanglmir@163.com
 * 创建时间：2016年11月26日 下午3:31:30
 * 修改人：王亮 wanglmir@163.com
 * 修改时间：2016年11月26日 下午3:31:30
 * 修改备注：
 * @version </pre>
 */
@Controller
@RequestMapping("/gameController")
public class GameController extends ABaseController {
    @Autowired
    private VGameService vGameService;
    @Autowired
    private WGameService wGameService;
    @Autowired
    private UserService userService;
    @Autowired
    private PUserVoucherService pUserVoucherService;

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    private static final Integer showNum = 50;

    @RequestMapping("/updateVGameList")
    @ResponseBody
    @MethodLog
    public BaseResponse updateVGameList(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	logger.info("requestUrl"+ "/gameController/updateVGameList");
    	//设置编码
        response.setCharacterEncoding("UTF-8");

        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> map = dataMap.getMap(request);
        Map<String, Object> rankMap = new HashMap<String, Object>();
        try {
            if (null != map && !map.isEmpty()) {
                String json = gson.toJson(map);
                VGame vGame = gson.fromJson(json, VGame.class);
                Map userMap = vGameService.selectVGameRankByUserId(vGame);
                String string = userMap.get("overplus_times").toString();
                long overplusTimes = Long.valueOf(string) - 1l;
                vGame.setOverplusTimes(overplusTimes);
                vGame.setUpdateTime(new Date());
                vGameService.updateVGameByUserId(vGame);
                userMap = vGameService.selectVGameRankByUserId(vGame);
                try {
                    long rank = (long) Double.parseDouble(userMap.get("rownum").toString());
                    userMap.put("rank", rank);
                    /*userMap.put("overplus_times", overplusTimes);
                    userMap.put("score", vGame.getScore());*/
                    rankMap.put("user", userMap);
                    List<Map> rankList = vGameService.selectVGameRankList();
                    for (Map map2 : rankList) {
                        long rank2 = (long) Double.parseDouble(map2.get("rownum").toString());
                        map2.put("rank", rank2);
                    }
                    rankMap.put("rankList", rankList);
                    baseResponse.setMessage("updateVGameList ok");
                    baseResponse.setResult(rankMap);
                    logger.info("rankMap=" + gson.toJson(rankMap));
                } catch (Exception e) {
                    logger.error("updateVGameList", e);
                    baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
                    return baseResponse;
                }
            } else {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map");
            }

        } catch (Exception e) {
            logger.error("updateVGameList", e);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
            return baseResponse;
        }
        logger.info(" updateVGameList  response :" + super.gson.toJson(baseResponse));
        return baseResponse;
    }

    @RequestMapping("/getUserVGameMessage")
    @ResponseBody
    @MethodLog
    public BaseResponse getUserVGameMessage(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	logger.debug("requestUrl"+ "/gameController/getUserVGameMessage");
    	//设置编码
        response.setCharacterEncoding("UTF-8");
        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> map = dataMap.getMap(request);
        if (null != map && !map.isEmpty()) {
            String json = gson.toJson(map);
            VGame vGame = gson.fromJson(json, VGame.class);
             
            try {
                VGame model1 = vGameService.selectUserByUserCode(vGame);
                if (null == model1) {
                    vGame.setOverplusTimes(3l);
                    vGame.setCreateTime(new Date());
                    vGame.setUpdateTime(new Date());
                    vGameService.saveVGame(vGame);
                    baseResponse.setMessage("用户第一次使用");
                    baseResponse.setResult(vGame);
                    logger.debug("vGame=" + gson.toJson(vGame));
                } else {
                    baseResponse.setMessage("用户非第一次使用");
                    vGame.setUpdateTime(new Date());
                    vGameService.updateVGameByUserId(vGame);
                    VGame model2 = vGameService.selectUserByUserCode(vGame);
                    baseResponse.setResult(model2);
                    logger.info("model2=" + gson.toJson(model2));
                }
            } catch (Exception e) {
                logger.error("getUserVGameMessage", e);
                baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
                return baseResponse;
            }
        } else {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map");
        }
        logger.info(" getUserVGameMessage  response :" + super.gson.toJson(baseResponse));
        return baseResponse;
    }

    @RequestMapping("/getNationalRank")
    @ResponseBody
    @MethodLog
    public BaseResponse getNationalRank(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	logger.info("requestUrl"+ "/gameController/getNationalRank");
    	//设置编码
        response.setCharacterEncoding("UTF-8");

        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> map = dataMap.getMap(request);
        Map<String, Object> rankMap = new HashMap<String, Object>();
        if (null != map && !map.isEmpty()) {
            String json = gson.toJson(map);
            VGame vGame = gson.fromJson(json, VGame.class);
            try {
                Map userMap = vGameService.selectVGameRankByUserId(vGame);
                long rank = (long) Double.parseDouble(userMap.get("rownum").toString());
                userMap.put("rank", rank);
                rankMap.put("user", userMap);
                List<Map> rankList = vGameService.selectVGameRankList();
                for (Map map2 : rankList) {
                    long rank2 = (long) Double.parseDouble(map2.get("rownum").toString());
                    map2.put("rank", rank2);
                }
                rankMap.put("rankList", rankList);
                baseResponse.setMessage("查询榜单成功");
                baseResponse.setResult(rankMap);
                logger.info("rankMap" + gson.toJson(rankMap));
            } catch (Exception e) {
                baseResponse.setMessage("查询榜单异常");
                // TODO Auto-generated catch block
                logger.error("查询榜单异常", e);
                baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
                return baseResponse;

            }
        }
        logger.info(" getNationalRank  response :" + super.gson.toJson(baseResponse));
        return baseResponse;
    }

    /*************************************
     * 单词速拼
     ***********************************************/

    @RequestMapping("/updateWGameList")
    @ResponseBody
    @MethodLog
    public BaseResponse updateWGameList(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
        //设置编码
    	logger.info("requestUrl"+ "/gameController/updateWGameList");
        response.setCharacterEncoding("UTF-8");

        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> map = dataMap.getMap(request);
        Map<String, Object> rankMap = new HashMap<String, Object>();
        try {
            if (null != map && !map.isEmpty()) {
                String json = gson.toJson(map);
                WGame wGame = gson.fromJson(json, WGame.class);
                Map userMap = wGameService.selectWGameRankByUserId(wGame);
                String string = userMap.get("overplus_times").toString();
                long overplusTimes = Long.valueOf(string) - 1l;
                wGame.setOverplusTimes(overplusTimes);
                wGame.setUpdateTime(new Date());
                wGameService.updateWGameByUserId(wGame);
                userMap = wGameService.selectWGameRankByUserId(wGame);
                try {
                    long rank = (long) Double.parseDouble(userMap.get("rownum").toString());
                    userMap.put("rank", rank);
                    rankMap.put("user", userMap);
                    List<Map> rankList = wGameService.selectWGameRankList();
                    for (Map map2 : rankList) {
                        long rank2 = (long) Double.parseDouble(map2.get("rownum").toString());
                        map2.put("rank", rank2);
                    }
                    rankMap.put("rankList", rankList);
                    baseResponse.setMessage("查询榜单成功");
                    baseResponse.setResult(rankMap);
                    logger.info("rankMap" + gson.toJson(rankMap));
                } catch (Exception e) {
                    baseResponse.setMessage("查询榜单异常");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else {
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map");
            }
        } catch (JsonSyntaxException e) {
            logger.error("updateWGameList error", e);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
            return baseResponse;
        } catch (Exception e) {
            logger.error("updateWGameList error", e);
            baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
            return baseResponse;
        }
        logger.info(" updateWGameList  response :" + super.gson.toJson(baseResponse));
        return baseResponse;
    }


    @RequestMapping("/getUserWGameMessage")
    @ResponseBody
    @MethodLog
    public BaseResponse getUserWGameMessage(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
        //设置编码
    	logger.info("requestUrl"+ "/gameController/getUserWGameMessage");
        response.setCharacterEncoding("UTF-8");

        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> map = dataMap.getMap(request);
        if (null != map && !map.isEmpty()) {
            String json = gson.toJson(map);
            WGame wGame = gson.fromJson(json, WGame.class);
            try {
                WGame model1 = wGameService.selectUserByUserCode(wGame);
                if (null == model1) {
                    wGame.setOverplusTimes(3l);
                    wGame.setCreateTime(new Date());
                    wGame.setUpdateTime(new Date());
                    wGameService.saveWGame(wGame);
                    baseResponse.setMessage("用户第一次使用");
                    baseResponse.setResult(wGame);
                    logger.info("wGame" + gson.toJson(wGame));
                } else {
                    baseResponse.setMessage("用户非第一次使用");
                    wGame.setUpdateTime(new Date());
                    wGameService.updateWGameByUserId(wGame);
                    WGame model2 = wGameService.selectUserByUserCode(wGame);
                    baseResponse.setResult(model2);
                    logger.info("model2=" + gson.toJson(model2));
                }
            } catch (Exception e) {
                logger.error("getUserWGameMessage error", e);
                baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
                return baseResponse;
            }
        } else {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map");
        }
        logger.info(" getUserWGameMessage  response :" + super.gson.toJson(baseResponse));
        return baseResponse;
    }

    @RequestMapping("/getVoucherFromVGame")
    @ResponseBody
    @MethodLog
    public BaseResponse getVoucherFromVGame(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	//设置编码
    	logger.info("requestUrl"+ "/gameController/getVoucherFromVGame");
        response.setCharacterEncoding("UTF-8");

        BaseResponse baseResponse = new BaseResponse();
        Map<String, Object> map = dataMap.getMap(request);
        Map<String, Object> returnMap = dataMap.getMap(request);
        try {
			if (null != map && !map.isEmpty()) {
				String openId = map.get("userId").toString();
				if (null != openId && 0 < openId.length()) {
					VGame vGame = new VGame();
					vGame.setUserId(openId);
					VGame userVGame = vGameService.selectUserByUserCode(vGame);
					if (null != userVGame) {
						Long voucherFlag = userVGame.getVoucherFlag();
						if (0 == voucherFlag) {
							logger.info("用户第一次领取");
							User user = userService.getByOpenid(openId);
							if (null == user) {
								baseResponse.setResult("对不起，您还未使用过本产品");
								baseResponse.setCode("5000");
								return baseResponse;
							}
							String userId = user.getUserId();
							String voucherWay = "5";
							PUserVoucher userVoucher = new PUserVoucher();
					        userVoucher.setVoucherWay(voucherWay);
					        userVoucher.setUserId(userId);
					        VoucherWayEnum voucherWayEnum=VoucherWayEnum.getEnumByCode(userVoucher.getVoucherWay());
							Map<String, Object> voucherMap = pUserVoucherService.insertUserVoucher(userVoucher, voucherWayEnum);
							String amount = voucherMap.get("amount").toString();
							userVGame.setVoucherFlag(1l);
							vGameService.updateVGameByUserId(userVGame);
							returnMap.put("userId", userVGame.getUserId());
							returnMap.put("amount", amount);
							baseResponse.setResult(returnMap);
							logger.info("returnMap=" + returnMap);
						} else {
							baseResponse.setResult("已经领过游戏券");
							baseResponse.setCode("5001");
							return baseResponse;
						}
					}
				}
			} else {
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map");
			}
		} catch (Exception e) {
			baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		}
        return baseResponse;
    }
    @RequestMapping("/getVoucherFromWGame")
    @ResponseBody
    @MethodLog
    public BaseResponse getVoucherFromWGame(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	//设置编码
    	logger.info("requestUrl"+ "/gameController/getVoucherFromWGame");
    	response.setCharacterEncoding("UTF-8");
    	
    	BaseResponse baseResponse = new BaseResponse();
    	Map<String, Object> map = dataMap.getMap(request);
    	Map<String, Object> returnMap = dataMap.getMap(request);
    	try {
    		if (null != map && !map.isEmpty()) {
    			String openId = map.get("userId").toString();
    			if (null != openId && 0 < openId.length()) {
    				WGame wGame = new WGame();
    				wGame.setUserId(openId);
    				WGame userWGame = wGameService.selectUserByUserCode(wGame);
    				if (null != userWGame) {
    					Long voucherFlag = userWGame.getVoucherFlag();
    					if (0 == voucherFlag) {
    						logger.info("用户第一次领取");
    						User user = userService.getByOpenid(openId);
    						if (null == user) {
								baseResponse.setResult("对不起，您还未使用过本产品");
								baseResponse.setCode("5000");
								return baseResponse;
							}
    						String userId = user.getUserId();
    						String voucherWay = "4";
    						PUserVoucher userVoucher = new PUserVoucher();
    						userVoucher.setVoucherWay(voucherWay);
    						userVoucher.setUserId(userId);
    						VoucherWayEnum voucherWayEnum=VoucherWayEnum.getEnumByCode(userVoucher.getVoucherWay());
    						Map<String, Object> voucherMap = pUserVoucherService.insertUserVoucher(userVoucher, voucherWayEnum);
    						String amount = voucherMap.get("amount").toString();
    						userWGame.setVoucherFlag(1l);
    						wGameService.updateWGameByUserId(userWGame);
    						returnMap.put("userId", userWGame.getUserId());
    						returnMap.put("amount", amount);
    						baseResponse.setResult(returnMap);
    						logger.info("returnMap=" + returnMap);
    					} else {
    						baseResponse.setResult("已经领过游戏券");
							baseResponse.setCode("5001");
							return baseResponse;
    					}
    				}
    			}
    		} else {
    			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map");
    		}
    	} catch (Exception e) {
    		baseResponse.isFail(ResponseCode.SERVICE_ERROR, null);
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		
    	}
    	return baseResponse;
    }

    @RequestMapping("/shareGetVGameTimes")
    @ResponseBody
    @MethodLog
    public BaseResponse shareGetVGameTimes(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	//设置编码
    	logger.info("requestUrl"+ "/gameController/shareGetVGameTimes");
    	response.setCharacterEncoding("UTF-8");
    	
    	BaseResponse baseResponse = new BaseResponse();
    	Map<String, Object> map = dataMap.getMap(request);
    	if (null != map && !map.isEmpty()) {
			String userId = map.get("userId").toString();
			VGame vGame = new VGame();
			vGame.setUserId(userId);
			try {
				VGame userVGame = vGameService.selectUserByUserCode(vGame);
				userVGame.setOverplusTimes(userVGame.getOverplusTimes() + 3l);
				vGameService.updateVGameByUserId(userVGame);
				baseResponse.setMessage("操作成功");
			} catch (Exception e) {
				baseResponse.setMessage("访问数据库失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		} else {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map"); 
		}
    	return baseResponse;
    }
    
    @RequestMapping("/shareGetWGameTimes")
    @ResponseBody
    @MethodLog
    public BaseResponse shareGetWGameTimes(HttpServletRequest request, HttpServletResponse response, DataMap dataMap) {
    	//设置编码
    	logger.info("requestUrl"+ "/gameController/shareGetWGameTimes");
    	response.setCharacterEncoding("UTF-8");
    	
    	BaseResponse baseResponse = new BaseResponse();
    	Map<String, Object> map = dataMap.getMap(request);
    	if (null != map && !map.isEmpty()) {
			String userId = map.get("userId").toString();
			WGame wGame = new WGame();
			wGame.setUserId(userId);
			try {
				WGame userWGame = wGameService.selectUserByUserCode(wGame);
				userWGame.setOverplusTimes(userWGame.getOverplusTimes() + 3l);
				wGameService.updateWGameByUserId(userWGame);
				baseResponse.setMessage("操作成功");
			} catch (Exception e) {
				baseResponse.setMessage("访问数据库失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
					
			}
		} else {
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "map"); 
		}
    	return baseResponse;
    }
}

