package hxs.weixin.parent.entity;

import java.util.List;


public class TestKnowledge {
	/**
	 * 题目分值
	 */
	private Double testScore;

	/** 所有的知识点 */
	private List<Knowledge> knowledges;

	/** 知识点对应的分数 */
	private Double knowlegesScore;

	/** 是否正确 */
	private Boolean isRight;

	/**
	 * 
	 * @param testScore 题目分值 
	 * @param isRight 是否答对
	 * @param knowledges 包含的知识点
	 */
	public TestKnowledge(Double testScore, Boolean isRight, List<Knowledge> knowledges) {
		this.isRight = isRight;
		this.testScore = testScore;
		this.knowledges = knowledges;
		this.knowlegesScore = testScore / knowledges.size();
	}

	public Double getTestScore() {
		return testScore;
	}

	public void setTestScore(Double testScore) {
		this.testScore = testScore;
	}

	public List<Knowledge> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<Knowledge> knowledges) {
		this.knowledges = knowledges;
	}

	public Double getKnowlegesScore() {
		return knowlegesScore;
	}

	public void setKnowlegesScore(Double knowlegesScore) {
		this.knowlegesScore = knowlegesScore;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

}
