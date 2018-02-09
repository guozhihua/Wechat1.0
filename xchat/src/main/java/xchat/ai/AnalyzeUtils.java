package xchat.ai;


import com.baidu.aip.nlp.AipNlp;
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

    private static AipNlp getClient(){
        int random= new Random().nextInt(2);
        if(random==0){
            return new AiTuZi().getTuZiCient();
        }
        return  new AiTuZi().getXMCient();
    }


    /**
     * 简单此法分析
     * @param text
     * @return
     */
    public static JSONObject getAisearchReealse(String text) {
       return getClient().lexer(text,null);
    }

    /**
     * 依存句法分析
     * @param text
     * @return
     */
    public static JSONObject depParseText(String text) {
        return getClient().depParser(text,null);
    }











    public static void main(String[] args) {

        JSONObject jsonObject = depParseText("下列哪个场所没有对外开放的卫生间？");
        System.out.println(jsonObject);

    }






}
