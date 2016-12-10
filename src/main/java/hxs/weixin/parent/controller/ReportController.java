package hxs.weixin.parent.controller;

import hxs.weixin.parent.entity.Answer;

import hxs.weixin.parent.entity.Report;
import hxs.weixin.parent.responsecode.BaseResponse;
import hxs.weixin.parent.responsecode.ResponseCode;
import hxs.weixin.parent.service.ArithmeticService;
import hxs.weixin.parent.service.RepostService;
import hxs.weixin.parent.sys.MethodLog;
import hxs.weixin.parent.util.SoonJson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/report/")
public class ReportController {
	
	@Autowired
	private ArithmeticService arithmeticService;
	@Autowired
	private RepostService repostService;

	/**
	 * 保存诊断报告
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("submitResult")
	@MethodLog
	public @ResponseBody BaseResponse submitResult(HttpServletRequest request, HttpServletResponse response){
		BaseResponse baseResponse = new BaseResponse();
		String jsonString = request.getParameter("data");
		if(jsonString==null || jsonString.equals("")){
			BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "jsonString");
		}
		JSONObject root = JSONObject.fromObject(jsonString);
		String accuracy = root.get("accuracy").toString();//正确率
		String testCode = root.get("testCode").toString();// 试卷编号
		String userUniqueId = root.get("userUniqueId").toString();// 用户唯一标识
		String userScore = root.get("userScore").toString();// 用户成绩(得分)
		String subjectName = root.get("subjectName").toString();// 学科名称
		String gradeName = root.get("gradeName").toString();// 年级名称
		String subjectCode = root.get("subjectCode").toString();// 学科名称
		String gradeCode = root.get("gradeCode").toString();// 年级名称
		String stageCode = (root.get("stageCode") != null) ? root.get("stageCode").toString() : "";
		String ctbCode = (root.get("ctbCode") != null) ? root.get("ctbCode").toString() : "";
		String booktypeCode = (root.get("booktypeCode") != null) ? root.get("booktypeCode").toString() : "";
		Integer time = Integer.valueOf(root.get("time").toString());// 完成时间
		String testTitle = root.get("testTitle").toString();// 试卷标题
		Double totalScore = Double.valueOf(root.get("totalScore").toString());// 试卷总分

		JSONArray answers = (JSONArray) root.get("answers");
		List<Answer> answerList = new ArrayList<Answer>();

		Double testScore = (double) (totalScore / answers.size());
		JSONArray newAnswers = new JSONArray();
		for (int i = 0; i < answers.size(); i++) {
			JSONObject obj = (JSONObject) answers.get(i);
			String serialNumber = getSerialNumber();
			Boolean isRight = (Boolean) obj.get("isRight");
			String exercisesCode = (String) obj.get("exercisesCode") ;
			String answer = (String) obj.get("answer") ;
			String userAnswer = (String) obj.get("userAnswer") ;
			JSONArray knowledges = (JSONArray) obj.get("knowledges");
			JSONArray produces = (JSONArray) obj.get("produces");
			Answer an = new Answer(serialNumber, testScore, isRight, knowledges.toString(), produces.toString());
			answerList.add(an);
			
			JSONObject newObject = new JSONObject();
			newObject.put("serialNumber", serialNumber);
			newObject.put("isRight", isRight);
			newObject.put("exercisesCode", exercisesCode);
			newObject.put("answer", answer);
			newObject.put("userAnswer", userAnswer);
			newAnswers.add(i,newObject);
		}
		String graspRate = arithmeticService.getGraspRate(answerList);
		String errorFrequency = arithmeticService.getErrorFrequency(answerList);

		Report report = new Report();
		report.setTestCode(testCode);
		report.setUserUniqueId(userUniqueId);
		
		report.setGradeName(gradeName);
		report.setSubjectName(subjectName);
        report.setGradeCode(gradeCode);
        report.setSubjectCode(subjectCode);
        report.setAccuracy(accuracy);
		String str = new String();
		str = newAnswers.toString();
		report.setTestContent(str);
		report.setTitle(testTitle + "-学情诊断");
		report.setGraspRate(graspRate);
		report.setReportDate(new Date());
		report.setUseTime(time);
		long round = Math.round(Double.valueOf(userScore));
		report.setUserScore((int)round);
		report.setTotalScore(totalScore.intValue());
		report.setErrorFrequency(errorFrequency);
		report.setStageCode(stageCode);
		report.setCtbCode(ctbCode);
		report.setBooktypeCode(booktypeCode);
		/**保存报告**/
		repostService.saveReport(report);
		Map<String, Object> map = new LinkedHashMap<>();
		
		map.put("report", report);
		baseResponse.setResult(map);
		return baseResponse;
	}
	
	/**
	 * 获取诊断报告MAP
	 * 
	 * @param r
	 * @return
	 */
	private Map<String, Object> getMap(Report r) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("reportId", r.getReportId());
		map.put("testCode", r.getTestCode());
		map.put("subjectName", r.getSubjectName());
		map.put("subjectCode", r.getSubjectCode());
		map.put("gradeName", r.getGradeName());
		map.put("gradeCode", r.getGradeCode());
		map.put("title", r.getTitle());
		map.put("totalScore", r.getTotalScore());
		map.put("useTime", r.getUseTime());
		map.put("answers", r.getTestContent());
		map.put("graspMap", r.getGraspMap());
		map.put("errorFrequency", r.getErrorFrequency() == "" ? null : r.getErrorFrequency());
		map.put("graspRate", r.getGraspRate());
		map.put("stageCode", r.getStageCode());
		map.put("ctbCode", r.getCtbCode());
		map.put("booktypeCode", r.getBooktypeCode());
		return map;
	}
	
	/**
	 * 根据报告id获取诊断报告
	 * @param request
	 * @param response
	 */
	@RequestMapping("diagnose")
	@MethodLog
	public @ResponseBody BaseResponse getDiagnose(HttpServletRequest request, HttpServletResponse response) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String jsonString = request.getParameter("data");
		if(jsonString==null || jsonString.equals("")){
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString() , "data not find");
		}
		JSONObject root = JSONObject.fromObject(jsonString);
		Integer reportId = Integer.valueOf(root.get("reportId").toString());
		Report report = new Report();
		report.setReportId(reportId);
		Report reportByEntity = repostService.getReportByEntity(report);
		if (reportByEntity == null) {
			baseResponse.setResult(null);
		} else {
			String json = SoonJson.getJson(getMap(reportByEntity));
			baseResponse.setResult(json);
		}
		return baseResponse;
	}
	
	@RequestMapping("examName")
	@MethodLog
	public @ResponseBody BaseResponse getExamName(HttpServletRequest request,HttpServletResponse response,String code) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			if (null != code && 0 < code.length()) {
				List<Map<String, Object>> examNameList = repostService.getExamName(code);
				if (null != examNameList && 0 < examNameList.size()) {
					baseResponse.setMessage("调用资源库查询试题成功");
					baseResponse.setResult(examNameList);
				} else {
					return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "examNameList");
				}
			} else {
				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "code");
			}
		} catch (Exception e) {
			baseResponse.setMessage("调用资源库查询试题失败");
			e.printStackTrace();
		}
		return baseResponse;
	}
	
	/**
	 * 生成流水号
	 * @return
	 */
	private String  getSerialNumber() {
		String a = (int)((Math.random()*9+1)*10000000)+"";
		String b = System.currentTimeMillis()+"";
		return b+a;
	}
}
