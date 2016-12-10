package hxs.weixin.parent.controller;


import hxs.weixin.parent.entity.PUserVoucher;
import hxs.weixin.parent.entity.User;

import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.PUserVoucherService;
import hxs.weixin.parent.service.UserService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.sys.enums.VoucherWayEnum;
import hxs.weixin.parent.util.HttpRequest;
import hxs.weixin.parent.util.PathUtil;
import hxs.weixin.parent.util.PropertiesUtil;
import hxs.weixin.parent.util.jsonToMapUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wexinInIt/")
public class WexinInItController  extends  ABaseController{

	private static final Logger log =  LoggerFactory.getLogger(WexinInItController.class);
	private static  PropertiesUtil pu = new PropertiesUtil(PathUtil.getClassPath() + "config/weixin.properties");

	private static  final String DEFAULT_URL="http://personaltutortest.e-edusky.com/housekeeper/index.html";
	
	@Autowired
	private UserService userService;

	@Autowired
	private PUserVoucherService userVoucherService;
	
	/**
	 * 根据code获取openid
	 * @param appid
	 * @param secret
	 * @param code
	 * @return
	 */
	@RequestMapping("getOpenid")
	@MethodLog
	public String getOpenid(HttpServletRequest request,User user,Model model){
		BaseResponse baseResponse = new BaseResponse();
		String state = request.getParameter("state");
		log.info("state is "+state);
		if (StringUtils.isBlank(state)) {
			state=DEFAULT_URL;
		}
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+pu.getString("weixinappid")+"&secret="+pu.getString("weixinSecret")+"&code="+request.getParameter("code")+"&grant_type=authorization_code";
		try {
			URL getUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);
			String message = new String(b, "UTF-8");
			if(message==null){
				 return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "openid");
			}
			JSONObject json = JSONObject.fromObject(message);
			log.info("refresh_token============"+json.getString("refresh_token"));
			Map<String, Object> userinfoMap = refresh_token(json.getString("refresh_token"));
			user = userService.getByOpenid(json.getString("openid"));
			if(user==null){
				user=new User();
				user.setCreateTime(new Date());
				user.setOpenId(userinfoMap.get("openid").toString());
				user.setHeadImgUrl(userinfoMap.get("headimgurl").toString());
				user.setUserName(userinfoMap.get("nickname").toString().replaceAll("[\ue000-\uefff]", ""));
				double sexx = Double.parseDouble(userinfoMap.get("sex").toString());
				user.setSex((int)sexx);
				user.setUpdateTime(new Date());
				user.setFirstLoginFlag(true);
				userService.insertSelective(user);
				PUserVoucher userVoucher = new PUserVoucher();
				userVoucher.setUserId(user.getUserId());
				userVoucherService.insertUserVoucher(userVoucher, VoucherWayEnum.VOUCHER_WAY_FIRST_LOGIN);
			}
			log.info("openid========"+ userinfoMap.get("openid").toString());
			String openid = userinfoMap.get("openid").toString();
			if(openid!=null && !openid.equals("")){
				model.addAttribute("openid", userinfoMap.get("openid").toString());
				model.addAttribute("userId", user.getUserId());
				model.addAttribute("nickname", user.getUserName().toString().replaceAll("[\ue000-\uefff]", ""));
				model.addAttribute("redirectUrl", state);
			}
			log.info("model======================"+model.toString());
		} catch (MalformedURLException e) {
			log.error("model======================",e);
		} catch (IOException e) {
			log.error("model======================",e);
		}catch (Exception ex){
			log.error("model======================",ex);
		}
		return "index";
	}
	
	/**
	 * 刷新token,获取用户信息
	 * @return
	 */
	private Map<String, Object> refresh_token(String refreshToken){
		BaseResponse baseResponse = new BaseResponse();
		log.info("进来了===========");
		String refresh_token = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+pu.getString("weixinappid")+"&grant_type=refresh_token&refresh_token="+ refreshToken+"";
		String result = HttpRequest.getRequest(refresh_token);
		log.info("result======"+ result);
		if(result==null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "refresh_token");
		}
		Map<String, Object> parseData = jsonToMapUtil.parseData(result);
		String userinfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+parseData.get("access_token")+"&openid="+parseData.get("openid")+"&lang=zh_CN";
		String info = HttpRequest.getRequest(userinfoUrl);
		if(info==null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "userinfo");
		}
		Map<String, Object> infoMap = jsonToMapUtil.parseData(info);
		log.info("infoMap======="+ infoMap.toString());
		return infoMap;
	}

}
