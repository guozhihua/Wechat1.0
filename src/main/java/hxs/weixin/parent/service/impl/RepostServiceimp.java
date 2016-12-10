package hxs.weixin.parent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hxs.weixin.parent.dao.ReportMapper;
import hxs.weixin.parent.entity.Report;
import hxs.weixin.parent.service.RepostService;

@Service
public class RepostServiceimp implements RepostService{
	
	private Gson gson = new Gson();
	
	@Value("${hxs.examNameList.url.all}")
	private String examNameListUrl;
	
	@Autowired
	private ReportMapper reportMapper; 

	@Override
	public void saveReport(Report report) {
		
		reportMapper.insertSelective(report);
	}
	
	@Override
	public Report getReportByEntity(Report report) {
		return reportMapper.getReportByEntity(report);
	}
	
	@Override
	public List<Map<String, Object>> getExamName(String code) throws Exception {
		String httpGetRequestJson = HTTPClientUtils.httpGetRequestJson(examNameListUrl + code);
		Map<String, Object> fromJsonMap = null;
		if (null != httpGetRequestJson && 0 < httpGetRequestJson.length()) {
			fromJsonMap = gson.fromJson(httpGetRequestJson, new TypeToken<Map<String, Object>>() {}.getType());
		}
		String json = gson.toJson(fromJsonMap.get("questions"));
		List<Map<String, Object>> mapList = null;
		if (null != json && 0 < json.length()) {
			mapList = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
		}
		return mapList;
			
	}

}
