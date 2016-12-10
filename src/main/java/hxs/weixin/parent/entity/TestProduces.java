package hxs.weixin.parent.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产生式
 * @author pc
 *
 */
public class TestProduces {

	/**
	 * 题目分值
	 */
	private Double testScore;

	/** 所有的产生式 */
	private List<String> produces;

	/**
	 * 产生式总分数
	 */
	private Double allScore;

	/** 是否正确 */
	private Boolean isRight;
	
	public static Map<String,Double> producesCoefficient;
	
	
	static {
		producesCoefficient  = new HashMap<>();
		 
		producesCoefficient.put("事实(F)",0.1);
		producesCoefficient.put("具体概念类",0.3);
		producesCoefficient.put("抽象概念类",0.5);
		
		producesCoefficient.put("具体原理类",0.5);
		producesCoefficient.put("抽象原理类",0.7);
		producesCoefficient.put("方法运用类",0.4);
		
		producesCoefficient.put("方法评价类",0.8);
		producesCoefficient.put("方法分析类",1.0);
		producesCoefficient.put("创造策略类",1.2);
		
		producesCoefficient.put("联结型",1.0);
		producesCoefficient.put("表达型",1.0);
		producesCoefficient.put("操作型",1.0);
		
	 
	}
	
	/**
	 * 
	 * @param producesCoefficient 产生式系数表 
	 * @param produces 包含的产生式
	 * @param testScore 本体的分数
	 * @param isRight 是否答对
	 */
	public TestProduces (List<String> produces ,Double testScore,Boolean isRight){
		
		this.produces = produces ;
		this.testScore = testScore;
		this.isRight = isRight;
		Double allScore = 0.0;
		for (String key : produces) {
			allScore = allScore + (producesCoefficient.get(key)==null ?0.0 : producesCoefficient.get(key));
		}
		this.allScore = allScore;
	}

	public Double getTestScore() {
		return testScore;
	}

	public void setTestScore(Double testScore) {
		this.testScore = testScore;
	}

	public List<String> getProduces() {
		return produces;
	}

	public void setProduces(List<String> produces) {
		this.produces = produces;
	}

	public Double getAllScore() {
		return allScore;
	}

	public void setAllScore(Double allScore) {
		this.allScore = allScore;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	@Override
	public String toString() {
		return "{\"testScore\":\"" + testScore + "\",\"produces\":\"" + produces + "\",\"allScore\":\"" + allScore + "\",\"isRight\":\"" + isRight + "\"}  ";
	}
	
	 
	
}
