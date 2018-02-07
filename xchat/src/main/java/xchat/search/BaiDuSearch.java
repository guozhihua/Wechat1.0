package xchat.search;

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
        List<SearchCounter> searchResultList=new ArrayList<>();
        for(String  option: options){
            SearchCounter searchCounter=new SearchCounter(option,0);
            searchResultList.add(searchCounter);
        }
        String url = zhidaoUrl.concat(URLEncoder.encode(question));
        String result = HttpUtils.getBaiduSearch(url);
        if (StringUtils.isNotBlank(result)) {
            System.out.println("-------------------------------analyse start---------------------------------------");
            Document  document = Jsoup.parse(result);
            if(document!=null){
                List<JSONObject> reslt=null;
                Elements answer1 = document.select("dd.answer");
                AnalyzeUtils.clearResult();
                for (Element element : answer1) {
                    String text = element.text();
                    reslt = AnalyzeUtils.getAllAnalyzeResult(text);
                }
                getReslutList(reslt,searchResultList);
                Collections.sort(searchResultList, new SortByCount());

            }
        }
        SearchCounter searchCounter = searchResultList.get(0);
        SearchResult searchResult =new SearchResult(true,searchCounter.getOption());
        return searchResult;
    }

    /**
     * 倒叙
     */
    class SortByCount implements Comparator {
        public int compare(Object o1, Object o2) {
            SearchCounter s1 = (SearchCounter) o1;
            SearchCounter s2 = (SearchCounter) o2;
            if(s1.getCount()>s2.getCount()){
                return -1;
            }else if(s1.getCount()==s2.getCount()){
                return 0;
            } else{
                return 1;
            }
        }

    }

    public SearchResult zhidao(String question, String[] options) throws Exception {
        SearchResult search = search(question, options);
        return search;
    }

    public SearchResult wenku(String question, String[] options) {
        return null;
    }

    public static void main(String[] args) {
        try {
            SearchResult zhidao = new BaiDuSearch().zhidao("夜盲症是缺少哪种维生素？", new String[]{"维生素A", "维生素B"});
            System.out.println(zhidao.getRightAnswer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void  getReslutList(List<JSONObject> jsonObjects,List<SearchCounter> searchCounters){
        HashSet<String> set = new HashSet<>();
        for (SearchCounter searchCounter : searchCounters) {
            set.add(searchCounter.getOption());
        }
        if(jsonObjects!=null){
            for (JSONObject jsonObject : jsonObjects) {
                JSONArray items = jsonObject.getJSONArray("items");
                if(items!=null&&items.length()>0){
                    for (Object item : items) {
                        JSONObject oj= (JSONObject) item;
                        String item1 = oj.getString("item").trim();
                        if(set.contains(item1)){
                            for (SearchCounter searchCounter : searchCounters) {
                                if(searchCounter.getOption().equals(item1)){
                                    searchCounter.setCount(searchCounter.getCount()+1);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

}
/**
 * 每个选项投票算法
 */
class SearchCounter{

    private String option ;

    private  Integer count ;

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