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
import xchat.sys.PropertiesUtil;

import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 618 on 2018/1/12.
 *
 * @author lingfengsan
 */

public class BaiDuSearch implements Search {
    private static String logPath = "";


    private static final String zhidaoUrl = "https://zhidao.baidu.com/search?lm=0&rn=10&pn=0&fr=search&ie=gbk&word=";
    private static final String msearch = "https://m.baidu.com/s?sa=ikb&word=";

    private static final String sogouSearch = "http://www.sogou.com/sogou?ie=utf8&&_asf=null&dp=1&s_from=result_up&query=";
    private static final String sogouSearch2 = "https://www.sogou.com/web?query=";

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
        setBdLogPath();
        final Map<String, Integer> reslutmap = new LinkedHashMap<>();
        //初始化选项统计情况
        for (String option : options) {
            if (!reslutmap.containsKey(option)) {
                reslutmap.put(option, 0);
            }
        }
        LinkedHashMap<String, Integer> reversermap = new LinkedHashMap<>();
        reversermap.putAll(reslutmap);

        LinkedHashMap<String, Integer> baiduMap = new LinkedHashMap<>();
        baiduMap.putAll(reslutmap);

        LinkedHashMap<String, Integer> sougouMap = new LinkedHashMap<>();
        sougouMap.putAll(reslutmap);

        //问错误相关的题，那么可以转换为那个是正确的，一种思路是查询题干中重要信息排除选项，一种是通过选项反推主干
        boolean isWrong = isAskWrong(question);
        Map<String, Integer> allTaskStatus = new HashMap<>();
        String questionMainInfo = getQuestionMainInfo(question);
        //选项分词
        Map<String, String> optionAnalyzeItem = getOptionAnalyzeItem(options);
        //开启线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<LinkedHashMap<String, Integer>>> futureList = new ArrayList<>();
        //题目选项反推
        ReverseSearchCallableTask reverseTask = new ReverseSearchCallableTask(question, options, questionMainInfo, reversermap);
        futureList.add(executorService.submit(reverseTask));
        allTaskStatus.put(futureList.get(0).toString(), 0);

        BaiduCallableTask baiduCallableTask = new BaiduCallableTask(question, options, optionAnalyzeItem, baiduMap);
        futureList.add(executorService.submit(baiduCallableTask));
        allTaskStatus.put(futureList.get(1).toString(), 0);

        SogouCallableTask sogouCallableTask = new SogouCallableTask(question, options, optionAnalyzeItem, sougouMap);
        futureList.add(executorService.submit(sogouCallableTask));
        allTaskStatus.put(futureList.get(2).toString(), 0);
        //开始任务集合
        while (true) {
            int counter = 0;
            for (Integer integer : allTaskStatus.values()) {
                counter += integer;
            }
            if (counter == allTaskStatus.size()) {
                if (!executorService.isShutdown()) {
                    executorService.shutdown();
                }
                break;
            }
            for (Future<LinkedHashMap<String, Integer>> future : futureList) {
                if (!future.isDone()) {
                    Thread.sleep(200);
                } else {
                    allTaskStatus.put(future.toString(), 1);
                }
            }
        }
        for (Future<LinkedHashMap<String, Integer>> future : futureList) {
            int i = 0;
            if (future.isDone() && !future.isCancelled()) {
                LinkedHashMap<String, Integer> linkedHashMap = future.get();
                for (String key : linkedHashMap.keySet()) {
                    if (reslutmap.containsKey(key)) reslutmap.put(key, linkedHashMap.get(key) + reslutmap.get(key));
                }
            }
        }
        //合并投票简单
        boolean unkoown = true;
        for (Integer integer : reslutmap.values()) {
            if (integer > 0) {
                unkoown = false;
                break;
            }
        }
        if (unkoown) {
            System.out.println("==========================AI分析完毕。我不会做呀================================");
            return null;
        }
        List<SearchCounter> seachCount = new ArrayList<>();
        //通过list进行排序
        for (String key : options) {
            Integer integer = reslutmap.get(key);
            SearchCounter searchCounter = new SearchCounter(key, integer);
            seachCount.add(searchCounter);
            System.out.println(key + ":" + searchCounter.getCount());
        }
        Collections.sort(seachCount, new SortByCount());
        SearchCounter searchCounter = null;
        if (isWrong) {
            searchCounter = seachCount.get(seachCount.size() - 1);
        } else {
            searchCounter = seachCount.get(0);
        }
        SearchResult searchResult = new SearchResult(true, searchCounter.getOption());
        System.out.println("==========================AI分析完毕================================");
        return searchResult;
    }

    /**
     * 返回一个新map,
     * 应该屏蔽推导出来名次的通用属性 如游戏，歌曲
     *
     * @param options
     * @param reslutmap
     * @param questionMainInfo
     */
    public static LinkedHashMap<String, Integer> reveseAnsweroptions(String[] options, LinkedHashMap<String, Integer> reslutmap, String questionMainInfo) {
        LinkedHashMap<String, Map<String, Integer>> allInfo = new LinkedHashMap<>();
        String[] optioins1 = questionMainInfo.split("@");
        //options 是原来的题目选项
        for (String opt : options) {
            if (StringUtils.isBlank(opt)) {
                continue;
            }
            Map<String, Integer> reslutmap11 = new LinkedHashMap<>();
            //初始化选项统计情况
            for (String option : optioins1) {
                if (!reslutmap.containsKey(option) && StringUtils.isNotBlank(option)) {
                    reslutmap11.put(option, 0);
                }
            }
            mSearch(reslutmap11, null, getOptionKey(opt), optioins1);
            allInfo.put(opt, reslutmap11);
        }
        //排除通用属性，这里定义3个选项中分词结果都出现16次以上为通用的属性
        Set<String> strings = allInfo.keySet();
        Map<String, Integer> filterMap = new HashMap<>();

        for (String string : strings) {
            Map<String, Integer> stringIntegerMap = allInfo.get(string);
            for (String opt1 : stringIntegerMap.keySet()) {
                if (stringIntegerMap.get(opt1) > 4) {
                    if (filterMap.get(opt1) == null) {
                        filterMap.put(opt1, 1);
                    } else {
                        filterMap.put(opt1, filterMap.get(opt1) + 1);
                    }
                }
            }
        }
        if (!filterMap.isEmpty()) {
            StringBuilder sb=new StringBuilder();
            for (String filterKey : filterMap.keySet()) {
                if (filterMap.get(filterKey) == strings.size()) {
                    Collection<Map<String, Integer>> values = allInfo.values();
                    for (Map<String, Integer> value : values) {
                        value.remove(filterKey);
                        System.out.println("一级通用属性："+filterKey);
                    }
                }else{
                    sb.append(filterKey).append(" ");
                }
            }
           // 名词关联度
            SecketUtils.sendMsgToAll("commonword", sb.toString());
        }
        for (String allKey : allInfo.keySet()) {
            Map<String, Integer> stringIntegerMap = allInfo.get(allKey);
            int allA = 0;
            for (Integer integer : stringIntegerMap.values()) {
                allA += integer;
                reslutmap.put(allKey, allA);
            }
        }
        return reslutmap;
    }

    private static String getQuestionMainInfo(String question) {
        StringBuilder questionMain = new StringBuilder();
        JSONObject jsonObject = AnalyzeUtils.depParseText(question);
        if (jsonObject.has("items")) {
            JSONArray items = jsonObject.getJSONArray("items");

            for (Object item : items) {
                JSONObject obj = (JSONObject) item;
                if (obj != null) {
                    boolean contains = obj.getString("postag").contains("n");
                    if (contains) {
                        questionMain.append(obj.getString("word")).append("@");
                    }
                }
            }


        }
        return questionMain.toString();
    }

    private void setBdLogPath() {
        if (logPath.equals("")) {
            URL fileUrl = PropertiesUtil.class.getResource("/log4j2.xml");//得到文件路径
            logPath = fileUrl.getPath();
            System.setProperty("aip.log4j.conf", logPath);
        }
    }

    public static void bdZhidao(String question, String[] options, Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem) {
        String url = zhidaoUrl.concat(URLEncoder.encode(question));
        String result = HttpUtils.getBaiduSearch(url);
        try {
            if (StringUtils.isNotBlank(result)) {
                StringBuilder sb = new StringBuilder();
                System.out.println("-------------------------------analyse start---------------------------------------");
                Document document = Jsoup.parse(result);
                if (document != null) {
                    Elements answer1 = document.select("dd.answer");
//                AnalyzeUtils.clearResult();
                    for (Element element : answer1) {
                        String text = element.text();
                        sb.append(text).append("     ");
                    }
                    for (String option : options) {
                        optionItemsAnalyze(reslutmap, optionAnalyzeItem, sb.toString(), option);
                        String trim = getOptionKey(option);
                        if (trim == null) {
                            continue;
                        }
                        int number = StringUtils.countMatches(sb.toString(), trim);
                        reslutmap.put(option, number);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void optionItemsAnalyze(Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem, String sb, String option) {
        String optionAnalyzItems = optionAnalyzeItem.get(option);
        if (StringUtils.isNotBlank(optionAnalyzItems)) {
            String[] split = optionAnalyzItems.split("&");
            if (split.length > 0) {
                for (String sp : split) {
                    if (StringUtils.isBlank(sp)) {
                        continue;
                    } else {
                        int number = StringUtils.countMatches(sb, sp);
                        reslutmap.put(option, reslutmap.get(option) + number);
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
     *
     * @param question
     * @param options
     * @return
     */
    public static void mSearch(Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem, String question, String[] options) {
        try {
            String url = msearch.concat(URLEncoder.encode(question));
            String s = HttpUtils.get(url);
            Document parse = Jsoup.parse(s);
            Element results = parse.getElementById("results");
            String text = results.text();
            for (String option : options) {
                String trim = getOptionKey(option);
                if (trim == null) {
                    continue;
                }
                if (optionAnalyzeItem != null) {
                    optionItemsAnalyze(reslutmap, optionAnalyzeItem, text, option);
                }
                int number = StringUtils.countMatches(text, trim);
                reslutmap.put(option, reslutmap.get(option) + number);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    /**
     * 搜狗搜索
     *
     * @param question
     * @param options
     * @return
     */
    public static void sougouSearch(Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem, String question, String[] options) {
        try {
            question = question.replace("\n", "");
            question = question.replace("\u2028", "");
            String url = sogouSearch.concat(question);
            System.out.println(url);
            parseDocumentInfo(reslutmap, optionAnalyzeItem, options, url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private static void parseDocumentInfo(Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem, String[] options, String url) {
        String s = HttpUtils.get(url);
        if (StringUtils.isNotBlank(s)) {
            Document parse = Jsoup.parse(s);
            StringBuffer sogouApper = new StringBuffer();
            for (Element element : parse.getElementsByClass("results")) {
                sogouApper.append(element.text());
            }
            for (String option : options) {
                if (optionAnalyzeItem != null) {
                    optionItemsAnalyze(reslutmap, optionAnalyzeItem, sogouApper.toString(), option);
                }
                String trim = getOptionKey(option);
                if (trim == null) {
                    continue;
                }
                int number = StringUtils.countMatches(sogouApper.toString(), trim);
                reslutmap.put(option, reslutmap.get(option) + number);
            }
        }
        System.out.println(s.length());

    }

    /**
     * 搜狗搜索
     *
     * @param question
     * @param options
     * @return
     */
    public static void sougouSearch2(Map<String, Integer> reslutmap, Map<String, String> optionAnalyzeItem, String question, String[] options) {
        try {
            question = question.replace("\n", "");
            question = question.replace("\u2028", "");
            String url = sogouSearch2.concat(question);
            System.out.println(url);
            parseDocumentInfo(reslutmap, optionAnalyzeItem, options, url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public SearchResult wenku(String question, String[] options) {
        return null;
    }

    public static Map<String, String> getOptionAnalyzeItem(String[] options) {
        Map<String, String> optionItems = new HashMap<>();
        Map<String, Integer> counters = new HashMap<>();
        for (String option : options) {
            String optionKey = getOptionKey(option);
            if (optionKey == null) {
                continue;
            }
            StringBuilder optinAnalyzeVal = new StringBuilder();
            JSONObject aisearchReealse = AnalyzeUtils.getAisearchReealse(optionKey);
            if (aisearchReealse != null && aisearchReealse.has("items")) {
                JSONArray items = aisearchReealse.getJSONArray("items");
                if (items != null) {
                    for (Object item : items) {
                        JSONObject obj = (JSONObject) item;
                        if (obj.has("item")) {
                            String itemVal = obj.getString("item");
                            if (StringUtils.isNotBlank(itemVal.trim())) {
                                if (counters.containsKey(itemVal.trim())) {
                                    counters.put(itemVal.trim(), counters.get(itemVal.trim()) + 1);
                                } else {
                                    counters.put(itemVal.trim(), 1);
                                }
                                optinAnalyzeVal.append(itemVal.trim()).append("&");
                            }
                        }
                    }
                    optionItems.put(option, optinAnalyzeVal.toString());
                }
            }
        }
        for (String opkey : optionItems.keySet()) {
            String value = optionItems.get(opkey);
            for (String key : counters.keySet()) {
                if (value.contains(key)) {
                    Integer integer = counters.get(key);
                    if (integer > 2) {
                        value = value.replaceAll(key, "");
                        value = value.replaceAll("&&", "&");
                        value = value.replaceAll("&&&", "&");
                    }
                }
            }
            optionItems.put(opkey, value);
        }
        return optionItems;
    }

    public static String getOptionKey(String option) {
        String reg = "ABCDEFGH";
        String reslut = option.trim();
        if (StringUtils.isBlank(reslut)) {
            return null;
        }
        String first = reslut.charAt(0) + "";
        if (reslut.indexOf(".") > 0 && reslut.indexOf(".") < 3) {
            reslut = reslut.replaceFirst(".", "");
        }
        if (reg.contains(first)) {
            reslut = reslut.replaceFirst(first, "");
        }
        return reslut.trim();

    }

    /**
     * 是否是问的错误的答案,如不包含 不是 不等于
     *
     * @return
     */
    public static boolean isAskWrong(String question) {

        question = question.replace(getMatchPatterDanYin(question), "");
        question = question.replace(getMatchPatterYinHao(question), "");
        question = question.replace(getMatchPatterShu(question), "");

        if (question.contains("不是") || question.contains("不正确") || question.contains("不属于") ||
                question.contains("没有") || question.contains("不存在")) {
            return true;
        }
        return false;
    }


    /**
     * 双引号匹配
     *
     * @param question
     * @return
     */
    public static String getMatchPatterYinHao(String question) {
        Pattern p = Pattern.compile("“(.*?)”");
        Matcher m = p.matcher(question);
        if (m.find()) {
            String group = m.group();
            return group;
        }
        return "-1209";
    }

    /**
     * 书名匹配
     *
     * @param question
     * @return
     */
    public static String getMatchPatterShu(String question) {
        Pattern p = Pattern.compile("《(.*?)》");
        Matcher m = p.matcher(question);
        if (m.find()) {
            String group = m.group();
            return group;
        }
        return "-1209";
    }

    /**
     * 单引号匹配
     *
     * @param question
     * @return
     */
    public static String getMatchPatterDanYin(String question) {
        Pattern p = Pattern.compile("‘(.*?)’");
        Matcher m = p.matcher(question);
        if (m.find()) {
            String group = m.group();
            return group;
        }
        return "-1209";
    }

    public static void main(String[] args) {
        String str = "“你好”，我擦";
        JSONObject jsonObject = AnalyzeUtils.depParseText(str);
        System.out.println(jsonObject);
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