package hxs.weixin.parent.service;

import java.util.List;
import java.util.Map;

import hxs.weixin.parent.entity.Report;

public interface RepostService {

	public void saveReport(Report report);
	
	/**
	 * 根据report实体查询
	 * @param report
	 * @return
	 */
	public Report getReportByEntity(Report report);
	
	public List<Map<String, Object>> getExamName(String code) throws Exception;
}
