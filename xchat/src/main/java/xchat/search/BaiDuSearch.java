package xchat.search;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xchat.ai.AnalyzeUtils;
import xchat.sys.HttpUtils;

import java.net.URLEncoder;
import java.util.*;

/**
 * Created by 618 on 2018/1/12.
 *
 * @author lingfengsan
 */

public class BaiDuSearch implements Search {
    private static final String zhidaoUrl = "https://zhidao.baidu.com/search?lm=0&rn=10&pn=0&fr=search&ie=gbk&word=";
    private static final String msearch = "https://m.baidu.com/s?sa=ikb&word=";

    /**
     * https://zhidao.baidu.com/index?word=%E5%A4%9C%E7%9B%B2%E7%97%87
     * 百度知道接口 百度文档接口  百度分词接口 提取有用的信息
     *
     * @param question
     * @param options
     * @return
     * @throws Exception
     */
    @Override
    public SearchResult search(String question, String[] options) throws Exception {
        Map<String, Integer> reslutmap = new LinkedHashMap<>();
        //初始化选项统计情况
        for (String option : options) {
            if(!reslutmap.containsKey(option)){
                reslutmap.put(option,0);
            }
        }
        //选项分词
        Map<String, String> optionAnalyzeItem = getOptionAnalyzeItem(options);
        String url = zhidaoUrl.concat(URLEncoder.encode(question));
        String result = HttpUtils.getBaiduSearch(url);
        if (StringUtils.isNotBlank(result)) {
            StringBuilder sb=new StringBuilder();
            System.out.println("-------------------------------analyse start---------------------------------------");
            Document document = Jsoup.parse(result);
            if (document != null) {
                Elements answer1 = document.select("dd.answer");
//                AnalyzeUtils.clearResult();
                for (Element element : answer1) {
                    String text = element.text();
                    sb.append(text).append("     ");
                }
                for(String option:options){
                    optionItemsAnalyze(reslutmap, optionAnalyzeItem, sb.toString(), option);
                    String trim = getOptionKey(option);
                    int number = StringUtils.countMatches(sb.toString(),trim);
                    reslutmap.put(option,number);
                }

            }
        }
        mSearch(reslutmap,optionAnalyzeItem,question, options);
        //合并投票简单
        boolean unkoown=true;
        for(Integer integer:reslutmap.values()){
            if(integer>0){
                unkoown=false;
                break;
            }
        }
        if(unkoown){
            return null;
        }
        List<SearchCounter> seachCount=new ArrayList<>();
        //通过list进行排序
        for(String key:options){
            Integer integer = reslutmap.get(key);
            SearchCounter searchCounter=new SearchCounter(key,integer);
            seachCount.add(searchCounter);
            System.out.println(key+":"+searchCounter.getCount());
        }
        Collections.sort(seachCount, new SortByCount());
        SearchCounter searchCounter = null;
        if (isAskWronng(question)) {
            searchCounter = seachCount.get(seachCount.size() - 1);
        } else {
            searchCounter = seachCount.get(0);
        }
        SearchResult searchResult = new SearchResult(true, searchCounter.getOption());
        return searchResult;
    }

    private void optionItemsAnalyze(Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem, String sb, String option) {
        String optionAnalyzItems = optionAnalyzeItem.get(option);
        if(StringUtils.isNotBlank(optionAnalyzItems)){
            String[] split = optionAnalyzItems.split("&");
            if(split.length>0){
                for (String sp : split) {
                     if(StringUtils.isBlank(sp)){
                         continue;
                     }else{
                         int number = StringUtils.countMatches(sb,sp);
                         reslutmap.put(option,reslutmap.get(option)+number);
                     }
                }

            }
        }
    }

    /**
     * 倒叙
     */
    class SortByCount implements Comparator {
        public int compare(Object o1, Object o2) {
            SearchCounter s1 = (SearchCounter) o1;
            SearchCounter s2 = (SearchCounter) o2;
            if (s1.getCount() > s2.getCount()) {
                return -1;
            } else if (s1.getCount() == s2.getCount()) {
                return 0;
            } else {
                return 1;
            }
        }

    }

    /**
     * 简答百度搜索
     * @param question
     * @param options
     * @return
     */
    public void mSearch( Map<String, Integer> reslutmap ,  Map<String, String> optionAnalyzeItem,String question, String[] options) {
        String url = msearch.concat(URLEncoder.encode(question));
        String s = HttpUtils.get(url);
        Document parse = Jsoup.parse(s);
        Element results = parse.getElementById("results");
        String text = results.text();
        for(String option:options){
            optionItemsAnalyze(reslutmap, optionAnalyzeItem, text, option);
            String trim =getOptionKey(option);
            int number = StringUtils.countMatches(text,trim);
            reslutmap.put(option,reslutmap.get(option)+number);
        }

    }



    public SearchResult wenku(String question, String[] options) {
        return null;
    }

    public static void main(String[] args) {
        try {
            SearchResult zhidao = new BaiDuSearch().search("人体正常体温大概是多少度？\u2028", new String[]{"A 41℃ ～47℃", "B 28℃ ～30℃","C 36℃ ～37℃"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> getOptionAnalyzeItem(String[] options){
        Map<String,String> optionItems=new HashMap<>();
        Map<String,Integer> counters=new HashMap<>();
        for (String option : options) {
            String optionKey = getOptionKey(option);
            StringBuilder optinAnalyzeVal = new StringBuilder();
            JSONObject aisearchReealse = AnalyzeUtils.getAisearchReealse(optionKey);
            if(aisearchReealse!=null&&aisearchReealse.has("items")){
                JSONArray items = aisearchReealse.getJSONArray("items");
                if(items!=null){
                    for (Object item : items) {
                         JSONObject obj= (JSONObject) item;
                        if(obj.has("item")){
                            String itemVal =obj.getString("item");
                            if(StringUtils.isNotBlank(itemVal.trim())){
                                if(counters.containsKey(itemVal.trim())){
                                    counters.put(itemVal.trim(),counters.get(itemVal.trim())+1);
                                }else{
                                    counters.put(itemVal.trim(),1);
                                }
                                optinAnalyzeVal.append(itemVal.trim()).append("&");
                            }
                        }
                    }
                    optionItems.put(option,optinAnalyzeVal.toString());
                }
            }
        }
        for (String  opkey : optionItems.keySet()) {
            String value=optionItems.get(opkey);
            for (String key : counters.keySet()) {
                if(value.contains(key)){
                    Integer integer = counters.get(key);
                    if(integer>2){
                        value=value.replaceAll(key,"");
                        value= value.replaceAll("&&","&");
                        value=value.replaceAll("&&&","&");
                    }
                }
            }
           optionItems.put(opkey,value);
        }
        return optionItems;
    }

    public static String getOptionKey(String option){
        String reg="ABCDEFGH";
        String reslut =option.trim();
        String first = reslut.charAt(0)+"";
        if(reslut.indexOf(".")>0&&reslut.indexOf(".")<3){
            reslut=  reslut.replaceFirst(".","");
        }
        if(reg.contains(first)){
            reslut=  reslut.replaceFirst(first,"");
        }
        return reslut.trim();

    }

    /**
     * 是否是问的错误的答案,如不包含 不是 不等于
      * @return
     */
   public static boolean isAskWronng(String question){

       if(question.contains("“")&&question.contains("”")){
           question =question.substring(question.indexOf("”"));
       }
       if(question.contains("《")&&question.contains("》")){
           question =question.substring(question.indexOf("》"));
       }
       if(question.contains("‘")&&question.contains("’")){
           question =question.substring(question.indexOf("’"));
       }
       if(question.contains("不是") || question.contains("不正确") || question.contains("不属于") ||
               question.contains("没有") || question.contains("不存在")){
           return true;
       }

       return  false;
   }

}



/**
 * 每个选项投票算法
 */
class SearchCounter {

    private String option;

    private Integer count;

    public SearchCounter(String option, int count) {
        this.option = option;
        this.count = count;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}