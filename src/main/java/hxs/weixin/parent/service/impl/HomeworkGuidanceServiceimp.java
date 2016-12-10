package hxs.weixin.parent.service.impl;

import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.HomeworkGuidanceService;
import hxs.weixin.parent.sys.CacheKey;
import hxs.weixin.parent.util.jsonToMapUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hxs.weixin.parent.util.redis.RedisClientTemplate;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

@Service
public class HomeworkGuidanceServiceimp implements HomeworkGuidanceService {
    @Autowired
    private RedisClientTemplate redisClientTemplate;

    private static Gson gson = new Gson();

    private static Logger logger = Logger.getLogger(HomeworkGuidanceServiceimp.class);


    @Value("${hxs.product.url}")
    private String hxsProductUrl;

    @Value("${hxs.chapter.url}")
    private String hxsChapterUrl;

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProductList(String bookType, String subjectCode)
            throws ClientProtocolException, IOException {
        BaseResponse baseResponse = new BaseResponse();
        String[] gradeCodes = {"11", "12", "13", "14", "15", "16", "21", "22"};
        String[] subjectCodes = {subjectCode};
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gradeCodes", gradeCodes);
        map.put("subjectCodes", subjectCodes);
        map.put("bookType", bookType);
        String resString = null;
        long stime = System.currentTimeMillis();
        String redisVal = redisClientTemplate.get(CacheKey.HXS_HOME_WORK_PRODUCT_LIST + "getProductList_" + bookType + "_" + subjectCode);
        if (StringUtils.isEmpty(redisVal)) {
            resString = HTTPClientUtils.httpPostRequestJson(hxsProductUrl, gson.toJson(map));
            long etime = System.currentTimeMillis();
            logger.info(hxsProductUrl + "  getProductList  param is bookType :" + bookType + "subjectCode: " + subjectCode + "，请求资源库，消耗时间 ：" + (etime - stime) / 1000 + "s");

        } else {
            resString = redisVal;
        }
        if (resString == null) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "product");
        }
        Map<String, Object> TeachingMaterialMap = jsonToMapUtil.parseData(resString);
        List<Map<String, Object>> objDataMap = (List<Map<String, Object>>) TeachingMaterialMap.get("datas");
        if (objDataMap != null && objDataMap.size() > 0) {
            redisClientTemplate.setex(CacheKey.HXS_HOME_WORK_PRODUCT_LIST + "getProductList_" + bookType + "_" + subjectCode, 3600 * 12, resString);
            return objDataMap;
        }
        return new ArrayList<Map<String, Object>>();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getInformationAndVideo(String ctbCode) {
        BaseResponse baseResponse = new BaseResponse();
        long stime = System.currentTimeMillis();
        String resString = null;
        String redisVal = redisClientTemplate.get(CacheKey.HXS_HOME_WORK_INFO_VIDEO + "getInformationAndVideo" + ctbCode);
        if (StringUtils.isEmpty(redisVal)) {
            resString = HTTPClientUtils.httpGetRequestJson(hxsChapterUrl + "/" + ctbCode);
            long etime = System.currentTimeMillis();
            logger.info(hxsChapterUrl + "  getInformationAndVideo  param is 【" + ctbCode + "】，请求资源库，消耗时间 ：" + (etime - stime) / 1000 + "s");

        } else {
            resString = redisVal;
        }
        if (resString == null) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "resString");
        }
        Map<String, Object> TeachingMaterialMap = jsonToMapUtil.parseData(resString);
        List<Map<String, Object>> objDataMap = (List<Map<String, Object>>) TeachingMaterialMap.get("datas");
        if (objDataMap != null && objDataMap.size() > 0) {
            redisClientTemplate.setex(CacheKey.HXS_HOME_WORK_INFO_VIDEO + "getInformationAndVideo" + ctbCode, 3600 * 12, resString);
            return objDataMap;
        }
        return new ArrayList<Map<String, Object>>();
    }
}
