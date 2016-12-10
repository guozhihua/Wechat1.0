package hxs.weixin.parent.dao;


import hxs.weixin.parent.entity.PRanking;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>PRankingDao<br>
 */
@Repository
public interface PRankingDao extends BaseDao<PRanking> {

    Long countCurrentRank(@Param("userId") String userCode, @Param("subjectCode")String subjectCode, @Param("gradeCode") String gradeCode, @Param("ctbCode") String ctbCode);

    void set_rank(String sql_id);

    List<PRanking> selectListByPage(Map<String,Object> param);

}
