package hxs.weixin.parent.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import hxs.weixin.parent.dao.BaseDao;
import hxs.weixin.parent.dao.PRankingDao;
import hxs.weixin.parent.entity.PRanking;
import hxs.weixin.parent.entity.PRankingRelation;
import hxs.weixin.parent.service.PRankingService;
import hxs.weixin.parent.util.HTTPClientUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>PRankingService<br>
 */
@Service("PRankingServiceImpl")
public class PRankingServiceImpl extends BaseServiceImpl<PRanking> implements PRankingService {
    private final static Logger logger = Logger.getLogger(PRankingServiceImpl.class);

    private static final Gson gson = new Gson();
    /**
     * 随机的是当前时间的题目
     */
//	public static final String hxs_random_ten_url = "http://211.157.179.218:9091/llsfw/questionController/getQuestionsByCondition";
    /**
     * 随机的  没有时间条件限制
     */

    public static final String hxs_random_ten_url = "http://211.157.179.218:9091/llsfw/questionController/getTenQuestionRandom";

    @Autowired
    private PRankingDao pRankingDao;

    @Override
    protected BaseDao<PRanking> getBaseDao() {
        return pRankingDao;
    }

    public String getQuestionList(PRankingRelation pRankingRelation) throws JsonSyntaxException, Exception {
        String gsonResponse = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("stageCode", pRankingRelation.getStageCode());
            map.put("subjectCode", pRankingRelation.getSubjectCode());
            map.put("gradeCode", pRankingRelation.getGradeCode());
            map.put("ctbCode", pRankingRelation.getCtbCode());
            String data = gson.toJson(map);
            logger.info("PRankingServiceImpl 获取十道题  请求参数data:" + data);
            long  s_time = System.currentTimeMillis();
            gsonResponse = HTTPClientUtils.httpPostRequestJson(hxs_random_ten_url, data);
            long e_time =System.currentTimeMillis();
            logger.info("PRankingServiceImpl  getQuestionList 随机获取10道题 请求资源库，消耗时间 ："+(e_time-s_time)/1000+"s");

            if(gsonResponse==null){
                return null;
            }

        } catch (IOException e) {
            logger.error("PRankingServiceImpl getQuestionList"+e);
            return null;
        }
        logger.info("PRankingServiceImpl 获取十道题  资源库返回数据：" + gsonResponse);
        return gsonResponse;
    }

    public PageInfo<PRanking> getPageList(int pageNum, int pageSize, Map<String, Object> paramMap) {
        PageInfo<PRanking> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
//		List<PRanking> pRankingList = this.selectList(paramMap);
            List<PRanking> pRankingList = pRankingDao.selectListByPage(paramMap);
            pageInfo = new PageInfo<PRanking>(pRankingList);

        } catch (Exception e) {
            logger.error("PRankingServiceImpl getPageList:"+e);
            return null;
        }
        return pageInfo;
    }


    public long countCurrentRank(String userCode, String subjectCode, String gradeCode, String ctbCode) {
        Long current = null;
        try {
            final String sql_id = "set_rank";
            pRankingDao.set_rank(sql_id);
            current = pRankingDao.countCurrentRank(userCode, subjectCode, gradeCode, ctbCode);
            if (current == null) {
                current = pRankingDao.countCurrentRank(userCode, subjectCode, gradeCode, ctbCode);
            }

        } catch (Exception e) {
            logger.error("PRankingServiceImpl countCurrentRank ："+e);
            return current;
        }
        return current;
    }

    @Override
    public boolean saveOrUpdate(Map<String, Object> mapPage) {
        PRanking ranking = null;
        boolean result = true;
        try {
            Map<String, Object> mapHa = new HashMap<String, Object>();
            mapHa.put("subjectCode", mapPage.get("subjectCode"));
            mapHa.put("gradeCode", mapPage.get("gradeCode"));
            mapHa.put("ctbCode", mapPage.get("ctbCode"));
            mapHa.put("userId", mapPage.get("userId"));
            PRanking pRanking = this.queryOne(mapHa);
//			PRanking pRanking =  pRankingDao.selectOne(mapHa);
//			PRanking pRanking = this.queryOne(mapPage);
            ranking = new PRanking();
            ranking.setSubjectCode(mapPage.get("subjectCode").toString());
            ranking.setGradeCode(mapPage.get("gradeCode").toString());
            ranking.setCtbCode(mapPage.get("ctbCode").toString());
            Object sc = mapPage.get("score");
            ranking.setScore(Long.parseLong(sc.toString()));
            ranking.setUserId(mapPage.get("userId").toString());
            ranking.setCreateDate(new Date());
            if (pRanking == null) {
                pRankingDao.insert(ranking);
            } else {
                pRanking.setScore(pRanking.getScore() + ranking.getScore());
                pRanking.setUpdateDate(new Date());
                pRankingDao.updateByPrimaryKey(pRanking);
            }
        } catch (Exception e) {
            result = false;
            logger.error("PRankingServiceImpl saveOrUpdate ："+e);
            return result;
        }
        return result;
    }

}
