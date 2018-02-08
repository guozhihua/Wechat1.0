package xchat.ai;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */
public class AnalyzeUtils {

   static List<JSONObject> result = Collections.synchronizedList(new ArrayList<JSONObject>());
    public static JSONObject getAisearchReealse(String text) {
        int random= new Random().nextInt(2);
        if(random==0){
            return new AiTuZi().getTuZiCient().lexer(text, null);
        }
        return  new AiTuZi().getXMCient().lexer(text,null);
    }

    /**
     * 获取结果集合
     * @param options
     * @return
     */
    public  static List<JSONObject> getAllAnalyzeResult(String[] options){
        for(String text:options){
            result.add(getAisearchReealse(text));
        }
        return result;
    }
    /**
     * 清楚上次分析的结果集
     */
    public static void clearResult(){
        result.clear();
    }

    public static void main(String[] args) {
        List<JSONObject> allAnalyzeResult = getAllAnalyzeResult(new String[]{"A  维生素A维生素E", "B. 维生素C", " C  SOS"});
        for (JSONObject jsonObject : allAnalyzeResult) {
            System.out.println(jsonObject.getString("items"));
        }

    }


}
