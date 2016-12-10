package hxs.weixin.parent.controller;

import hxs.weixin.parent.entity.Order;
import hxs.weixin.parent.entity.PTestProject;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.OrderService;
import hxs.weixin.parent.service.PTestProjectService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.sys.ResourceRequestUrl;
import hxs.weixin.parent.util.HttpRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhusen on 2016/11/25.
 */
@Controller
@RequestMapping(value = "/exam")
public class ExamHelperController  extends  ABaseController{
    private Logger logger = LoggerFactory.getLogger(ExamHelperController.class);

    @Autowired
    private PTestProjectService pTestProjectService;

    @Autowired
    private OrderService orderService;

    /**
     * 根据学科code获取教材信息(又用不上了，留着先)
     *
     * @param subjectCode
     * @return
     */
    @RequestMapping("/baseBookTypeByGradeCodeandSubjectCode")
    @ResponseBody
    @MethodLog
    public BaseResponse baseBookTypeByGradeCodeandSubjectCode(String subjectCode) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(subjectCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "subjectCode");
        }
        String[] gradeCodes = new String[]{"11", "12", "13", "14", "15", "16", "21", "22"};
        try {
            String url = ResourceRequestUrl.baseBookTypeByGradeCodeandSubjectCode;
            JSONArray bookType = new JSONArray();
            for (int i = 0; i < gradeCodes.length; i++) {
                String gradeCode = gradeCodes[i];
                url = url.replace("{gradeCode}", gradeCode).replace("{subjectCode}", subjectCode);
                String result = HttpRequest.getRequest(url);
                if (StringUtils.isBlank(result)) {
                    continue;
                }
                JSONObject resultJson = JSONObject.fromObject(result);
                if (resultJson == null || !resultJson.getString("status").equals("200")) {
                    continue;
                }
                if (!JSONUtils.isArray(resultJson.get("data"))) {
                    continue;
                }
                JSONObject bookTypeJson = new JSONObject();
                bookTypeJson.put(gradeCode, resultJson.getJSONArray("data"));
                bookType.add(bookTypeJson);
            }
            baseResponse.setResult(bookType);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" baseBookTypeByGradeCodeandSubjectCode  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("baseBookTypeByGradeCodeandSubjectCode error", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }

    /**
     * 根据教材类型，学科编号获取知识信息
     *
     * @param bookType
     * @param subjectCode
     * @return
     */
    @RequestMapping("/getBookKnowledgeInfo")
    @ResponseBody
    @MethodLog
    public BaseResponse getBookKnowledgeInfo(String bookType, String subjectCode, String openid) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(bookType)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "bookType");
        }
        if (StringUtils.isBlank(subjectCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "subjectCode");
        }
        try {
            if (StringUtils.equals(new String(bookType.getBytes("ISO-8859-1"), "ISO-8859-1"), bookType)) {
                bookType = new String(bookType.getBytes("ISO-8859-1"), "UTF-8");
            }
            // 获取有效产品信息列表
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("status", 1);
            queryMap.put("subjectCode", subjectCode);
            queryMap.put("bookType", bookType);
            List<PTestProject> tpList = this.pTestProjectService.selectList(queryMap);
            if (tpList == null || tpList.isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }

            // 从资源库获取现有教材上下册信息
            if (bookType.equals("人教版新课标版")) {
                bookType = "人教新课标版";
            }
            String url = ResourceRequestUrl.getBookKnowledgeInfo;
            JSONObject json = new JSONObject();
            json.put("bookType", bookType);
            json.put("gradeCodes", JSONArray.fromObject("[\"11\",\"12\",\"13\",\"14\",\"15\",\"16\",\"21\",\"22\"]"));
            JSONArray subjectCodeArray = new JSONArray();
            subjectCodeArray.add(subjectCode);
            json.put("subjectCodes", subjectCodeArray);
            String result = HttpRequest.postRequest(url, json.toString());
            Map<String, JSONObject> bookTypeDataMap = new HashMap<>();
            JSONArray bookTypeDatas = null;
            if (StringUtils.isNotBlank(result)) {
                JSONObject resultJson = JSONObject.fromObject(result);
                if (resultJson != null && resultJson.getString("status").equals("200") && resultJson.get("datas") != null) {
                    if (JSONUtils.isArray(resultJson.get("datas")) || !resultJson.getJSONArray("datas").isEmpty()) {
                        bookTypeDatas = resultJson.getJSONArray("datas");
                        for (int i = 0; i < bookTypeDatas.size(); i++) {
                            JSONObject bookTypeData = bookTypeDatas.getJSONObject(i);
                            if (bookTypeData == null) {
                                continue;
                            }
                            bookTypeDataMap.put(bookTypeData.getString("ctbCode"), bookTypeData);
                        }
                    }
                }
            }

            if (bookType.equals("人教新课标版")) {
                json.put("bookType", "新课标人教版");
                result = HttpRequest.postRequest(url, json.toString());
                if (StringUtils.isNotBlank(result)) {
                    JSONObject resultJson = JSONObject.fromObject(result);
                    if (resultJson != null && resultJson.getString("status").equals("200") && resultJson.get("datas") != null) {
                        if (JSONUtils.isArray(resultJson.get("datas")) || !resultJson.getJSONArray("datas").isEmpty()) {
                            JSONArray bookTypeDatasAppend = resultJson.getJSONArray("datas");
                            if (bookTypeDatas == null)
                                bookTypeDatas = bookTypeDatasAppend;
                            else
                                bookTypeDatas.addAll(bookTypeDatasAppend);
                        }
                    }
                }
            }
            if (bookTypeDatas != null) {
                for (int i = 0; i < bookTypeDatas.size(); i++) {
                    JSONObject bookTypeData = bookTypeDatas.getJSONObject(i);
                    if (bookTypeData == null) {
                        continue;
                    }
                    bookTypeDataMap.put(bookTypeData.getString("ctbCode"), bookTypeData);
                }
            }

            // 获取已购买的未过期订单列表
            queryMap = new HashMap<>();
            queryMap.put("openid", openid);
            queryMap.put("buyType", 2);
            List<Order> boughtList = this.orderService.selectBought(queryMap);
            Map<Long, Order> boughtMap = new HashMap<>();
            if (boughtList != null && !boughtList.isEmpty()) {
                for (int i = 0; i < boughtList.size(); i++) {
                    Order order = boughtList.get(i);
                    if (order != null) {
                        boughtMap.put(Long.parseLong(order.getProjectId()), order);
                    }
                }
            }

            // 响应数据封装
            JSONArray responseJsonArray = new JSONArray();
            for (int i = 0; i < tpList.size(); i++) {
                PTestProject pt = tpList.get(i);
                if (pt == null) {
                    continue;
                }
                // 产品数据转为json
                JSONObject ptJson = JSONObject.fromObject(pt);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                ptJson.put("createTime", sdf.format(pt.getCreateTime()));
                ptJson.put("updateTime", sdf.format(pt.getUpdateTime()));

                // 产品数据组装资源库教材数据
                if (!bookTypeDataMap.isEmpty() && pt.getCtbCode() != null) {
                    JSONObject bookTypeData = bookTypeDataMap.get(String.valueOf(pt.getCtbCode()));
                    if (bookTypeData != null) {
                        ptJson.putAll(bookTypeData);
                    }
                } else {
                    ptJson.put("knowledgeName", pt.getProjectName());
                }

                // 判断是否买过
                if (!boughtMap.isEmpty()) {
                    Order order = boughtMap.get(pt.getProjectId());
                    if (order != null) {
                        ptJson.put("isBought", true);
                        ptJson.put("endTime", sdf.format(order.getEndTime()));
                    }
                }
                responseJsonArray.add(ptJson);
            }
            baseResponse.setResult(responseJsonArray);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" getBookKnowledgeInfo  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("getBookKnowledgeInfo error", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }

    /**
     * 获取考试帮手商品详情
     *
     * @param ctbCode
     * @return
     */
    @RequestMapping("/getDetail")
    @ResponseBody
    @MethodLog
    public BaseResponse getDetail(String ctbCode) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(ctbCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "ctbCode");
        }
        try {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("ctbCode", ctbCode);
            List<PTestProject> pTestProject = this.pTestProjectService.selectList(queryMap);
            if (pTestProject == null || pTestProject.isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            baseResponse.setResult(pTestProject.get(0));
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" getDetail  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("getDetail error:", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }

    /**
     * 根据上下册code获取单元列表
     *
     * @param ctbCode
     * @return
     */
    @RequestMapping("/getCtbList")
    @ResponseBody
    @MethodLog
    public BaseResponse getCtbList(String ctbCode) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(ctbCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "ctbCode");
        }
        try {
            String url = ResourceRequestUrl.getCtbList;
            String result = HttpRequest.getRequest(url.replace("{ctbCode}", ctbCode));
            if (StringUtils.isBlank(result)) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            JSONArray ctbArray = JSONArray.fromObject(result);
            if (ctbArray == null || ctbArray.isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("ctbCode", ctbCode);
            List<PTestProject> pTestProjectList = this.pTestProjectService.selectList(queryMap);
            if (pTestProjectList == null || pTestProjectList.isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            PTestProject pTestProject = pTestProjectList.get(0);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("projectName", pTestProject.getProjectName());
            responseMap.put("ctbList", ctbArray);
            baseResponse.setResult(responseMap);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" getCtbList  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("getCtbList error:", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }

    /**
     * 根据学年、学科、教材、单元知识点code获取试卷列表
     *
     * @param gradeCode
     * @param subjectCode
     * @param booktype
     * @param ctbCode
     * @return
     */
    @RequestMapping("/getPaper")
    @ResponseBody
    @MethodLog
    public BaseResponse getPaper(String gradeCode, String subjectCode, String booktype, String ctbCode) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(gradeCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "gradeCode");
        }
        if (StringUtils.isBlank(subjectCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "subjectCode");
        }
        if (StringUtils.isBlank(booktype)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "booktype");
        }
        if (StringUtils.isBlank(ctbCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "ctbCode");
        }
        try {
            String url = ResourceRequestUrl.getPapersForIdAndNames;
            JSONObject json = new JSONObject();
            json.put("gradeCode", gradeCode);
            json.put("subjectCode", subjectCode);
            json.put("booktype", booktype);
            json.put("volume", ctbCode);
            json.put("type", "单元测试");
            String result = HttpRequest.postRequest(url, json.toString());
            if (StringUtils.isBlank(result)) {
                baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
                return baseResponse;
            }
            JSONObject resultJson = JSONObject.fromObject(result);
            if (resultJson == null || !resultJson.getString("status").equals("200") ||
                    resultJson.get("paperDatas") == null || !JSONUtils.isArray(resultJson.get("paperDatas"))) {
                baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
                return baseResponse;
            }
            JSONArray paperDatas = resultJson.getJSONArray("paperDatas");
            if (paperDatas == null || paperDatas.isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            JSONObject randomPaperJson = paperDatas.getJSONObject(new Random().nextInt(paperDatas.size()));
            String paperCode = randomPaperJson.getString("id");
            String paperName = randomPaperJson.getString("paperName");
            url = ResourceRequestUrl.getPaperWithCode;
            result = HttpRequest.getRequest(url.replace("{paperCode}", paperCode));
            if (StringUtils.isBlank(result)) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            JSONObject paperJson = JSONObject.fromObject(result.replace("null", String.valueOf(System.currentTimeMillis())));
            if (paperJson == null || !JSONUtils.isArray(paperJson.get("questions")) || paperJson.getJSONArray("questions").isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            JSONArray questions = paperJson.getJSONArray("questions");
            JSONObject responseJson = new JSONObject();
            responseJson.put("paperCode", paperCode);
            responseJson.put("paperName", paperName);
            responseJson.put("questions", questions);
            baseResponse.setResult(responseJson);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" getPaper  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("getPaper error:", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }

    /**
     * 根据单元code获取视频信息
     *
     * @param ctbCode
     * @return
     */
    @RequestMapping("/bookKnowledgeTreeAndVideo")
    @ResponseBody
    @MethodLog
    public BaseResponse bookKnowledgeTreeAndVideo(String ctbCode) {
        BaseResponse baseResponse = new BaseResponse();
        if (StringUtils.isBlank(ctbCode)) {
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.code, "ctbCode");
        }
        try {
            String url = ResourceRequestUrl.bookKnowledgeTreeAndVideo;
            String result = HttpRequest.getRequest(url.replace("{code}", ctbCode));
            if (StringUtils.isBlank(result)) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            JSONObject resultJson = JSONObject.fromObject(result);
            if (resultJson == null || !JSONUtils.isArray(resultJson.get("reponseVedio")) || resultJson.getJSONArray("reponseVedio").isEmpty()) {
                baseResponse.setCode(ResponseCode.RESOURCE_NOTFOUND.code);
                return baseResponse;
            }
            JSONArray reponseVedio = resultJson.getJSONArray("reponseVedio");
            baseResponse.setResult(reponseVedio);
            baseResponse.setCode(ResponseCode.SUCCESS.code);
            logger.info(" bookKnowledgeTreeAndVideo  response :" + super.gson.toJson(baseResponse));
            return baseResponse;
        } catch (Exception e) {
            logger.error("bookKnowledgeTreeAndVideo error:", e);
            baseResponse.setCode(ResponseCode.SERVICE_ERROR.code);
            return baseResponse;
        }
    }
}
