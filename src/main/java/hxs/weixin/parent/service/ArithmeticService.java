package hxs.weixin.parent.service;

import hxs.weixin.parent.entity.Answer;
import hxs.weixin.parent.entity.Knowledge;
import hxs.weixin.parent.entity.TestKnowledge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 算法服务
 * 
 * @author pc
 *
 */
@Service
public class ArithmeticService {

	
	/**
	 * 掌握率
	 * @param answers,每道题的信息集合，包含每道题的平均分数，所有知识点集合，是否正确，每道题每个知识点对应的分数（TestKnowledge中的属性knowlegesScore）
	 * @return
	 */
	public  String getGraspRate(List<Answer> answers){
		
		List<TestKnowledge> tests = new ArrayList<>();//集合中的每个对象的解释：题目分值，所有的知识点，知识点对应的分数，是否正确 
		//Answer中已经将属性赋值给TestKnowledge
		for (Answer an : answers) {
			tests.add(an.getTestKnowledge());
		}
		Map<String, Integer> map = getRight(tests);
		JSONObject json = JSONObject.fromObject(map); 
		return json.toString();
	}
	
	/**
	 * 获取知识点错误频率
	 * @param answers
	 * @return
	 */
	public  String getErrorFrequency(List<Answer> answers){
		
		List<TestKnowledge> list = new ArrayList<>();
		
		for (Answer an : answers) {
			list.add(an.getTestKnowledge());
		}
		Map<String, Knowledge> map = errorFrequency(list);
		if(map.size()==0){
			return "";
		}
		JSONArray ar = new JSONArray();
		for(Entry<String, Knowledge> en : map.entrySet()){
			Knowledge kn = en.getValue();
			JSONObject obj = new JSONObject();
			obj.put("knowledgeName",kn.getKnowledgeName());
			obj.put("knowledgeCode",kn.getKnowledgeCode());
			obj.put("errorSize",kn.getErrorSize());
			ar.add(obj);
		}
		 
		return ar.toString();
	}
	
	/**
	 * 知识点掌握程度
	 * 
	 * @param tests
	 * @return
	 */
	private static Map<String, Integer> getRight(List<TestKnowledge> tests) {
		Map<String, Double> rightMap = new HashMap<>();//这套试卷中每个知识点正确的总得分（去除错误的分数）
		Map<String, Double> allMap = new HashMap<>();//这套试卷中每个知识点的总得分（包含错误与正确的分数）
		//利用map键的唯一性和if判断进行累加和去重
		for (TestKnowledge test : tests) {
			for (Knowledge kn : test.getKnowledges()) {
				Double d = rightMap.get(kn.getKnowledgeName()) == null ? 0.0 : rightMap.get(kn.getKnowledgeName());
				if (test.getIsRight() == true) {
					rightMap.put(kn.getKnowledgeName(), d + test.getKnowlegesScore());
				}else{
					rightMap.put(kn.getKnowledgeName(), d);
				}
				Double all = allMap.get(kn.getKnowledgeName())==null ? 0.0:allMap.get(kn.getKnowledgeName());
				allMap.put(kn.getKnowledgeName(), all + test.getKnowlegesScore());
			}
		}
		Map<String, Integer> newMap = new HashMap<>();//每个知识点的正确率，键值对
		for (Entry<String, Double> en : rightMap.entrySet()) {
			Double d = en.getValue() / allMap.get(en.getKey())*100;
			newMap.put(en.getKey(),d.intValue());
		}

		return newMap;
	}
	
	/**
	 * 获取错误频率
	 * 
	 * @param tests
	 * @return
	 */
	private static Map<String, Knowledge> errorFrequency(List<TestKnowledge> tests) {
		Map<String, Knowledge> map = new HashMap<>();
		for (TestKnowledge test : tests) {
			for (Knowledge kn : test.getKnowledges()) {
				if (test.getIsRight() == false) {
					 Knowledge k = map.get(kn.getKnowledgeName());
					 Integer n = k==null ? 0: k.getErrorSize();
					 if(k==null){
						 k = new Knowledge();
						 k.setKnowledgeName(kn.getKnowledgeName());
						 k.setKnowledgeCode(kn.getKnowledgeCode());
					 }
					 k.setErrorSize(n+1);
					 map.put(kn.getKnowledgeName(),k);
				}
			}
		}
		return map;
	}
	
}
