package hxs.weixin.parent.controller;

import com.github.pagehelper.PageInfo;
import hxs.weixin.parent.entity.PRanking;
import hxs.weixin.parent.entity.PRankingRelation;
import hxs.weixin.parent.entity.RankingModel;
import hxs.weixin.parent.entity.User;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.PRankingRelationService;
import hxs.weixin.parent.service.PRankingService;
import hxs.weixin.parent.service.UserService;
import hxs.weixin.parent.sys.MethodLog;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zx on 2016/11/25.
 */
@Controller
@RequestMapping("/hxs_ranking_wx")
public class RankingController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(RankingController.class);

    @Autowired
    private PRankingRelationService pRankingRelationServiceImpl;

    @Autowired
    private PRankingService pRankingServiceImpl;

    @Autowired
    private UserService userService;

    /**
     * 获取排行榜中的数据   分页
     *
     * @param requestId
     * @param pranking
     * @return
     */
    @RequestMapping(value = "/get_all_rank", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(remark ="获取排行榜中的数据")
    public BaseResponse getAllRank(@RequestParam(required = false) String requestId, @RequestBody RankingModel pranking) {

        BaseResponse baseResponse = new BaseResponse(requestId);

        if (pranking.getUserId() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (pranking.getSubjectCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (pranking.getGradeCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (pranking.getBookType() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "bookType");
        }
        if (pranking.getPageNum() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (pranking.getPageSize() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            /**
             * 根据 学科code  年级code   教材类型
             *
             *   ctbcode
             */
            Map<String, Object> mapCtb = new HashMap<String, Object>();
            mapCtb.put("subjectCode", pranking.getSubjectCode());
            mapCtb.put("gradeCode", pranking.getGradeCode());
            mapCtb.put("bookType", pranking.getBookType());
            PRankingRelation pRankingRelation = pRankingRelationServiceImpl.queryOne(mapCtb);
            if (pRankingRelation == null) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURCE_NOTFOUND.toString(), "ctb_code");
            }
            int length = pRankingRelation.getCtbCode().length();
            if (length < 5) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURSCEDATA_ERROR.toString(), "资源库之中没有对应的教材数据");
            }
            /**
             * 学科code  年级code ctbcode
             * 分页获取排行榜 数据
             */
            Map<String, Object> mapPage = new HashMap<String, Object>();
            mapPage.put("subjectCode", pRankingRelation.getSubjectCode());
            mapPage.put("gradeCode", pRankingRelation.getGradeCode());
            mapPage.put("ctbCode", pRankingRelation.getCtbCode());
            PageInfo<PRanking> pageInfo = pRankingServiceImpl.getPageList(Integer.parseInt(pranking.getPageNum()),
                    Integer.parseInt(pranking.getPageSize()), mapPage);

            baseResponse.setResult(pageInfo);


        } catch (Exception e) {
            logger.error("RankingController getAllRank exception:{}", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "getAllRank Exception");
        }
        logger.info("RankingController getAllRank 分页获取全部的排行榜   ：" + gson.toJson(baseResponse));
        return baseResponse;
    }

    /**
     * 获取自己在榜中的数据
     * @param requestId
     * @param pranking
     * @return
     */
    @RequestMapping(value = "/get_own_rank", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(remark ="获取自己在榜中的数据")
    public BaseResponse getOwnRank(@RequestParam(required = false) String requestId, @RequestBody RankingModel pranking) {

        BaseResponse baseResponse = new BaseResponse(requestId);

        if (pranking.getUserId() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (pranking.getSubjectCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (pranking.getGradeCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (pranking.getBookType() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "bookType");
        }

        try {
            /**
             * 根据 学科code  年级code   教材类型
             *
             *   ctbcode
             */
            Map<String, Object> mapCtb = new HashMap<String, Object>();
            mapCtb.put("subjectCode", pranking.getSubjectCode());
            mapCtb.put("gradeCode", pranking.getGradeCode());
            mapCtb.put("bookType", pranking.getBookType());
            PRankingRelation pRankingRelation = pRankingRelationServiceImpl.queryOne(mapCtb);
            if (pRankingRelation == null) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURCE_NOTFOUND.toString(), "ctb_code");
            }
            int length = pRankingRelation.getCtbCode().length();
            if (length < 5) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURSCEDATA_ERROR.toString(), "资源库之中没有对应的教材数据");
            }
            /**
             * 学科code  年级code ctbcode
             * 分页获取排行榜 数据
             */
            Map<String, Object> mapOne = new HashMap<String, Object>();
            mapOne.put("subjectCode", pranking.getSubjectCode());
            mapOne.put("gradeCode", pranking.getGradeCode());
            mapOne.put("ctbCode", pRankingRelation.getCtbCode());
            mapOne.put("userId", pranking.getUserId());
            PRanking rankingOwn = pRankingServiceImpl.queryOne(mapOne);
            if(rankingOwn==null){
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURCE_NOTFOUND.toString(), "此用户暂没有榜中数据");
            }
            /**
             * 获取自己的姓名
             */
            User byPrimaryKey = userService.selectByPrimaryKey(pranking.getUserId());
            if(byPrimaryKey == null){
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURCE_NOTFOUND.toString(), "暂时没有此用户相关数据");
            }

            /**
             * 自己排名
             */

            long userCurrentRank = pRankingServiceImpl.countCurrentRank(pranking.getUserId(), pranking.getSubjectCode(),
                                                                             pranking.getGradeCode(), pRankingRelation.getCtbCode());

            RankingModel rankingModel = new RankingModel();
            rankingModel.setUserId(pranking.getUserId());
            rankingModel.setGradeCode(pranking.getGradeCode());
            rankingModel.setSubjectCode(pranking.getSubjectCode());
            rankingModel.setCurrentRank(userCurrentRank+"");
            rankingModel.setScore(rankingOwn.getScore().toString());
            rankingModel.setUserName(byPrimaryKey.getUserName());
            rankingModel.setHeadImgUrl(byPrimaryKey.getHeadImgUrl());
            rankingModel.setSex(byPrimaryKey.getSex().toString());
            rankingModel.setMobile(byPrimaryKey.getMobile());

            baseResponse.setResult(rankingModel);
        } catch (Exception e) {
            logger.error("RankingController getOwnRank exception:{}", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "getOwnRank Exception");
        }
        logger.info("RankingController getOwnRank 获取自己在排行榜中的数据库 ：" + gson.toJson(baseResponse));
        return baseResponse;
    }

    /**
     * 获取  10 道题  随机
     *
     * @param requestId
     * @param pranking
     * @return
     */
    @RequestMapping(value = "/get_random_question", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(remark ="随机获取10道题")
    public BaseResponse getRandomQuestion(@RequestParam(required = false) String requestId, @RequestBody RankingModel pranking) {

        BaseResponse baseResponse = new BaseResponse(requestId);
        String questionList = null;
        if (pranking.getSubjectCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (pranking.getGradeCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (pranking.getBookType() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "bookType");
        }

        try {
            /**
             * 根据 学科code  年级code   教材类型  获取
             *   ctbcode
             */
            Map<String, Object> mapCtb = new HashMap<String, Object>();
            mapCtb.put("subjectCode", pranking.getSubjectCode());
            mapCtb.put("gradeCode", pranking.getGradeCode());
            mapCtb.put("bookType", pranking.getBookType());
            PRankingRelation pRankingRelation = pRankingRelationServiceImpl.queryOne(mapCtb);
            /**
             * 数据库之中的 ctb_code 为非空
             *
             * 资源库之中  对应年级 对应学科  没有相应的教材版本数据
             */
            if (pRankingRelation == null) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURCE_NOTFOUND.toString(), "ctb_code");
            }
            int length = pRankingRelation.getCtbCode().length();
            if (length < 5) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURSCEDATA_ERROR.toString(), "资源库之中没有对应的教材数据");
            }
            questionList = pRankingServiceImpl.getQuestionList(pRankingRelation);
            if (StringUtils.isBlank(questionList)) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURSCEDATA_ERROR.toString(), "资源库之中没有返回试题");
            }

            /**
             * analyzeKey  值为null的时候  程序解析500  加下面判断避免抛出500
             */
//            if(questionList.contains("null")){
//                return BaseResponse.setResponse(new BaseResponse(requestId),
//                        ResponseCode.RESOURSCEDATA_ERROR.toString(), "\\\"analyzeKey\\\":null,\\\"analyzeValue\\\":\\\"C");
//            }
            JSONObject question  = JSONObject.fromObject(questionList.replace("null",String.valueOf("000")));
            baseResponse.setResult(question);


        } catch (Exception e) {
            logger.error("RankingController getRandomQuestion exception:{}", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "getRandomQuestion Exception");
        }
        logger.info("RankingController getRandomQuestion  随机获取 10 道题   ：" + gson.toJson(baseResponse));
        return baseResponse;
    }

    /**
     * 生成排行榜之中的数据(保存或者是更新操作)
     *
     * @param requestId
     * @param pranking
     * @return
     */
    @RequestMapping(value = "/save_update", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(remark ="生成排行榜之中的数据")
    public BaseResponse saveOrUpdate(@RequestParam(required = false) String requestId, @RequestBody RankingModel pranking) {

        BaseResponse baseResponse = new BaseResponse(requestId);

        if (pranking.getUserId() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (pranking.getSubjectCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (pranking.getGradeCode() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (pranking.getBookType() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "bookType");
        }
        if (pranking.getScore() == null) {
            return BaseResponse.setResponse(new BaseResponse(requestId),
                    ResponseCode.PARAMETER_MISS.toString(), "score");
        }
        try {

            /**
             * 先获取 ctb_code
             *
             */
            Map<String, Object> mapCtb = new HashMap<String, Object>();
            mapCtb.put("subjectCode", pranking.getSubjectCode());
            mapCtb.put("gradeCode", pranking.getGradeCode());
            mapCtb.put("bookType", pranking.getBookType());
            PRankingRelation pRankingRelation = pRankingRelationServiceImpl.queryOne(mapCtb);
            if (pRankingRelation == null) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURCE_NOTFOUND.toString(), "ctb_code");
            }
            int length = pRankingRelation.getCtbCode().length();
            if (length < 5) {
                return BaseResponse.setResponse(new BaseResponse(requestId),
                        ResponseCode.RESOURSCEDATA_ERROR.toString(), "资源库之中没有对应的教材数据");
            }
            /**
             * 查找榜之中有没有这么一条数据
             */


            Map<String, Object> mapHa = new HashMap<String, Object>();
            mapHa.put("subjectCode", pranking.getSubjectCode());
            mapHa.put("gradeCode", pranking.getGradeCode());
            mapHa.put("ctbCode", pRankingRelation.getCtbCode());
            mapHa.put("userId", pranking.getUserId());
            mapHa.put("score", pranking.getScore());

            boolean saveOrUpdate = pRankingServiceImpl.saveOrUpdate(mapHa);
            PRanking ranking = null;
            if (saveOrUpdate) {
                Map<String, Object> mapBack = new HashMap<String, Object>();
                mapBack.put("subjectCode", pranking.getSubjectCode());
                mapBack.put("gradeCode", pranking.getGradeCode());
                mapBack.put("ctbCode", pRankingRelation.getCtbCode());
                mapBack.put("userId", pranking.getUserId());
                ranking = pRankingServiceImpl.queryOne(mapBack);
            }
            baseResponse.setResult(ranking);

        } catch (Exception e) {
            logger.error("RankingController saveOrUpdate exception:{}", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString(), "saveOrUpdate Exception");
        }
        logger.info("RankingController saveOrUpdate  保存或更新榜之中的数据  ：" + gson.toJson(baseResponse));
        return baseResponse;
    }


}
