package hxs.weixin.parent.entity;

import java.util.ArrayList;

import java.util.List;

import net.sf.json.JSONArray;

public class Answer {
 
	private String serialNumber;
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	private Double testScore;
	
	private Boolean isRight;
	
	private String knowledges;
	
	private String produces;
	
	private TestKnowledge testKnowledge;
	
	private TestProduces testProduces;
	
	@SuppressWarnings("unchecked")
	public Answer(String serialNumber ,Double testScore,Boolean isRight,String knowledges,String produces){
		this.serialNumber= serialNumber;
		this.testScore = testScore;
		this.isRight = isRight;
		this.knowledges = knowledges;
		this.produces = produces;
		
		List<Knowledge> knowledgesList = new ArrayList<>();
		String str = this.knowledges;
		
		 JSONArray jsonarray = JSONArray.fromObject(str);  
		knowledgesList =  (List<Knowledge>) JSONArray.toCollection(jsonarray, Knowledge.class);

		this.testKnowledge = new TestKnowledge(this.testScore, isRight, knowledgesList);
		
		List<String> producesList = new ArrayList<>();
		JSONArray proArray = JSONArray.fromObject(produces);
		 
		List<String>  proes =(List<String>)JSONArray.toCollection(proArray, String.class);
		for (String s : proes) {
			producesList.add(s);
		}
		
		this.testProduces = new TestProduces(producesList, this.testScore, this.isRight);
	}

	public TestKnowledge getTestKnowledge() {
		return testKnowledge;
	}


	public Double getTestScore() {
		return testScore;
	}

	public void setTestScore(Double testScore) {
		this.testScore = testScore;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	public String getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(String knowledges) {
		this.knowledges = knowledges;
	}

	public String getProduces() {
		return produces;
	}

	public void setProduces(String produces) {
		this.produces = produces;
	}

	public void setTestKnowledge(TestKnowledge testKnowledge) {
		this.testKnowledge = testKnowledge;
	}

	public TestProduces getTestProduces() {
		return testProduces;
	}

	public void setTestProduces(TestProduces testProduces) {
		this.testProduces = testProduces;
	}
	
	public static void main(String[] args) {
		//Gson gs=new Gson();
		List<String> ss=new ArrayList<>();
		ss.add("223,asea");
		ss.add("0000");
		//System.out.println(gs.toJson(ss));
	}
}
