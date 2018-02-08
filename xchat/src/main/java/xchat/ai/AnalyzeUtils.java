package xchat.ai;


import org.json.JSONArray;
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
     * 清楚上次分析的结果集
     */
    public static void clearResult(){
        result.clear();
    }



    public static void main(String[] args) {
        String[] strings = {"41℃ ～47℃", "28℃ ～30℃", "36℃ ～37℃"};
        List<JSONObject> allAnalyzeResult = new ArrayList<>();
        for(String key:strings){
            JSONObject aisearchReealse = getAisearchReealse(key);
            JSONArray items = aisearchReealse.getJSONArray("items");
            if(items!=null){
                for (Object item : items) {
                    System.out.println(((JSONObject) item).getString("item"));
                }
            }
            allAnalyzeResult.add(aisearchReealse);
        }




    }


}
