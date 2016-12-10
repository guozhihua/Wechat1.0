package hxs.weixin.parent.entity;

import java.util.Date;

/**
 * 检测报告
 * 
 * @author HeChen
 *
 */

public class Report implements java.io.Serializable {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	/** 检测报告编号 */
	private Integer reportId;
	/** 用户唯一标识 */
	private String userUniqueId;

	/** 试题编号 */
	private String testCode;
	/**
	 * 试卷总分
	 */
	private Integer totalScore;

	/**
	 * 用户成级
	 */
	private Integer userScore;

	/**
	 * 耗时
	 */
	private Integer useTime;

	/** 标题 */
	private String title;

	/** 试卷内容 */
	private String testContent;

	/** 错误 可提分列表 */
	private String addScore;
	/** 掌握率表征图 */
	private String graspMap;
	/** 掌握率 */
	private String graspRate;

	/** 错误频率 */
	private String errorFrequency;

	/** 提分修炼 */
	private String reflections;

	private String gradeName;

	private String subjectName;
	
	private String gradeCode;
	
	private String subjectCode;

	private String stageCode;
	
	private String ctbCode;
	
	private String booktypeCode;
	
	private String booktypeName;
	
	/** 日期 */
	private Date reportDate;

	private String reportDateString;
	
	private String accuracy;//正确率

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getTestContent() {
		return testContent;
	}

	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getAddScore() {
		return addScore;
	}

	public void setAddScore(String addScore) {
		this.addScore = addScore;
	}

	public String getGraspMap() {
		return graspMap;
	}

	public void setGraspMap(String graspMap) {
		this.graspMap = graspMap;
	}

	public String getGraspRate() {
		return graspRate;
	}

	public void setGraspRate(String graspRate) {
		this.graspRate = graspRate;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getUserScore() {
		return userScore;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	public Integer getUseTime() {
		return useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	public String getErrorFrequency() {
		return errorFrequency;
	}

	public void setErrorFrequency(String errorFrequency) {
		this.errorFrequency = errorFrequency;
	}

	public String getReflections() {
		return reflections;
	}

	public void setReflections(String reflections) {
		this.reflections = reflections;
	}
	
	

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	public String getCtbCode() {
		return ctbCode;
	}

	public void setCtbCode(String ctbCode) {
		this.ctbCode = ctbCode;
	}

	public String getBooktypeCode() {
		return booktypeCode;
	}

	public void setBooktypeCode(String booktypeCode) {
		this.booktypeCode = booktypeCode;
	}

	public String getBooktypeName() {
	
		return booktypeName;
	}

	public void setBooktypeName(String booktypeName) {
	
		this.booktypeName = booktypeName;
	}

	public String getReportDateString() {
	
		return reportDateString;
	}

	public void setReportDateString(String reportDateString) {
	
		this.reportDateString = reportDateString;
	}

	@Override
	public String toString() {
		return "Report [reportId=" + reportId + ", userUniqueId="
				+ userUniqueId + ", testCode=" + testCode + ", totalScore="
				+ totalScore + ", userScore=" + userScore + ", useTime="
				+ useTime + ", title=" + title + ", testContent=" + testContent
				+ ", addScore=" + addScore + ", graspMap=" + graspMap
				+ ", graspRate=" + graspRate + ", errorFrequency="
				+ errorFrequency + ", reflections=" + reflections
				+ ", gradeName=" + gradeName + ", subjectName=" + subjectName
				+ ", gradeCode=" + gradeCode + ", subjectCode=" + subjectCode
				+ ", stageCode=" + stageCode + ", ctbCode=" + ctbCode
				+ ", booktypeCode=" + booktypeCode + ", booktypeName="
				+ booktypeName + ", reportDate=" + reportDate
				+ ", reportDateString=" + reportDateString + ", accuracy="
				+ accuracy + "]";
	}

	
}
