package hxs.weixin.parent.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonSyntaxException;
import hxs.weixin.parent.entity.PRanking;
import hxs.weixin.parent.entity.PRankingRelation;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>PRankingService<br>
 */
public interface PRankingService extends BaseService<PRanking> {

    /**
     * 根据学科 ，学年，教材 随机获取  10 道题
     * @param baseDataModel
     * @return
     * @throws JsonSyntaxException
     * @throws Exception
     */
    String getQuestionList(PRankingRelation baseDataModel) throws JsonSyntaxException, Exception;


    /**
     * 分页显示榜之中的数据
     * @param pageNum
     * @param pageSize
     * @param paramMap
     * @return
     * @throws Exception
     */
    PageInfo<PRanking> getPageList(int pageNum, int pageSize, Map<String,Object> paramMap)throws Exception;

    /**
     * 统计用户实时榜当前排名
     * @param userCode
     * @param subjectCode
     * @param gradeCode
     * @param ctbCode
     * @return
     */
    long countCurrentRank(String userCode,String subjectCode,String gradeCode,String ctbCode);

    /**
     * 保存或者更新榜之中的数据
     * @param mapPage
     * @return
     */
    boolean saveOrUpdate(Map<String, Object> mapPage);

}
