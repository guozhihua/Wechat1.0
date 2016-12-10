package hxs.weixin.parent.dao;

import hxs.weixin.parent.entity.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportMapper {
    int countByExample(Report example);

    int deleteByExample(Report example);

    int deleteByPrimaryKey(Integer reportId);

    int insert(Report record);

    int insertSelective(Report record);

    List<Report> selectByExample(Report example);
    
    /**
	 * 根据report实体查询
	 * @param report
	 * @return
	 */
	public Report getReportByEntity(Report report);

}