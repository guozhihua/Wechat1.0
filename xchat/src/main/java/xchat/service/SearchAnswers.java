package xchat.service;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xchat.pojo.Information;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by 志华 on 2018/2/4.
 */
public class SearchAnswers {
    private static Logger logger = LoggerFactory.getLogger(SearchAnswers.class);
    /**
     * Gets answer.
     *
     * @param question the question
     * @since millions-heros-core-be 1.0.0
     */
    public static String getAnswer(Information question) {
        try {
            String title = URLEncoder.encode(question.getQuestion());
            // 调用百度 "简单搜索" APP接口，搜索结果更准确
            String url = "https://m.baidu.com/s?sa=ikb&word=" + title;
            String searchResult = GETRequest(url);
            Map<String, Integer> answerMap = new HashMap<>(16);
            if (question.getAns().length>0) {
                for (String answer : question.getAns()) {
                    answerMap.put(answer, getAnswerCount(searchResult, answer));
                }
            } else {
                logger.error("识别答案出错 ,答案为空!");
                return null;
            }
           return getFinalAnswer(answerMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * Get request string.
     *
     * @param path the path
     * @return the string
     * @since millions-heros-core-be 1.0.0
     */
    private static String GETRequest(String path) {
        long startTime = System.currentTimeMillis();
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            logger.info("HTTP请求共耗时: " + (System.currentTimeMillis() - startTime) + "ms");
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Gets answer count.
     *
     * @param result the string
     * @param answer the a
     * @since millions-heros-core-be 1.0.0
     */
    private static Integer getAnswerCount(String result, String answer) {
        int i = result.length() - result.replace(answer, "").length();
        Integer count = i / answer.length();
        logger.info("匹配 " + answer + " 个数 :" + count);
        return count;
    }

    /**
     * Gets final answer.
     *
     * @param result the result
     * @since millions-heros-core-be 1.0.0
     */
    private static String getFinalAnswer(Map<String, Integer> result) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(result.entrySet());
        // 按照答案匹配个数降序排列
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        String answer = entryList.get(0).getKey();
        logger.info("------------------>>>>>>>>>>>>>>>>>>>>>>> 本题建议答案为: " + answer);
        return answer;
    }
}
