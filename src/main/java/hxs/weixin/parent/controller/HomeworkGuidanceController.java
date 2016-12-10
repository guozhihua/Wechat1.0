package hxs.weixin.parent.controller;

import hxs.weixin.parent.entity.Order;
import hxs.weixin.parent.entity.Project;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.HomeworkGuidanceService;
import hxs.weixin.parent.service.OrderService;
import hxs.weixin.parent.service.ProjectService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.util.DataMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/homework/")
public class HomeworkGuidanceController extends ABaseController{
	
	private static final Logger log = LoggerFactory.getLogger(HomeworkGuidanceController.class);

	@Autowired
	private HomeworkGuidanceService homeworkGuidanceService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 获取产品列表
	 * @param data
	 * @param request
	 * @return
	 */
	@RequestMapping("getProductList")
	@MethodLog
	public @ResponseBody BaseResponse getProductList(DataMap data,HttpServletRequest request,HttpServletResponse response,Project project){
		BaseResponse baseResponse = new BaseResponse();
		Map map = data.getMap(request);
		List<Map<String, Object>> productListMap = null;
		if(map.get("bookType").toString().equals("") || map.get("bookType")==null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "bookType");
		}
		if(map.get("subjectCode").toString().equals("") || map.get("subjectCode")==null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
		}
		if(map.get("openid").toString().equals("") || map.get("openid")==null){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "openid");
		}
		try {
			String bookType = map.get("bookType").toString();
            if (StringUtils.equals(new String(bookType.getBytes("ISO-8859-1"), "ISO-8859-1"), bookType)) {
                bookType = new String(bookType.getBytes("ISO-8859-1"), "UTF-8");
            }
			productListMap = homeworkGuidanceService.getProductList(bookType , map.get("subjectCode").toString());
			if(productListMap!=null && productListMap.size()>0){
				/**获取用户所有购买过产品列表**/
				Map<String, Object> queryMap = new HashMap<>();
				queryMap.put("openid", map.get("openid").toString());
	            queryMap.put("buyType", 1);
				List<Order> boughtList = orderService.selectBought(queryMap);
				Map<Long, Order> boughtMap = new HashMap<>();
		        if (boughtList != null && !boughtList.isEmpty()) {
	                for (int i = 0; i < boughtList.size(); i++) {
	                    Order order = boughtList.get(i);
	                    if (order == null) {
	                        continue;
	                    }
	                    boughtMap.put(Long.parseLong(order.getProjectId()), order);
	                }
		         }
				for (Map<String, Object> productMap : productListMap) {
					/**查询产品列表**/
					project = projectService.getByctbCode(productMap.get("ctbCode").toString());
					if(project==null){
						continue;
					}
					productMap.put("project", project);
					Order order = boughtMap.get((long) project.getProjectId());
					if (order != null) {
						productMap.put("buy", "已购买");
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        productMap.put("endTime", sf.format(order.getEndTime()));
					}
				}
				baseResponse.setResult(productListMap);
				log.info("productListMap:"+productListMap);
				log.info("save getProductList  response :"+super.gson.toJson(baseResponse));
			}
		} catch (IOException iex){
			log.error("getProductList Ioexception error",iex.getMessage());
			baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
			return  baseResponse;
		} catch (Exception e) {
			log.error("getProductList error",e);
			baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
			return  baseResponse;
		}
		return baseResponse;
	}
	
	/**
	 * 根据ctbCode获取节点信息和视频
	 * @param data
	 * @param request
	 * @return
	 */
	@RequestMapping("getChapterList")
	@MethodLog
	public @ResponseBody BaseResponse getChapterList(DataMap data,HttpServletRequest request){
		BaseResponse baseResponse = new BaseResponse();
		try {
			Map map = data.getMap(request);
			if(!map.isEmpty() && map !=null){
				if(map.get("ctbCode").toString().equals("") || map.get("ctbCode") == null){
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "ctbCode");
				}
				List<Map<String, Object>> informationAndVideo = homeworkGuidanceService.getInformationAndVideo(map.get("ctbCode").toString());
				if(informationAndVideo!=null && informationAndVideo.size()>0){
					baseResponse.setResult(informationAndVideo);
				}
			}
		}catch (IOException iex){
			log.error("getChapterList Ioexception error",iex.getMessage());
			baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
			return  baseResponse;
		}catch (Exception ex){
			log.error("getChapterList error",ex);
			baseResponse.isFail(ResponseCode.SERVICE_ERROR,null);
			return  baseResponse;
		}
		log.info(" getChapterList  response :"+super.gson.toJson(baseResponse));
		return baseResponse;
	}
}
