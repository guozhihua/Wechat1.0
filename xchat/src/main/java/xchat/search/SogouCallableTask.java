package xchat.search;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by :Guozhihua
 * Date： 2018/2/9.
 */
public class SogouCallableTask implements Callable<LinkedHashMap<String,Integer>> {
    private String question ;

    private String[] options;

    private Map<String, String> optionAnalyzeItem;

    private  LinkedHashMap<String,Integer> resultMap;

    public SogouCallableTask(String question, String[] options, Map<String, String> optionAnalyzeItem, LinkedHashMap<String, Integer> resultMap) {
        this.question = question;
        this.options = options;
        this.optionAnalyzeItem = optionAnalyzeItem;
        this.resultMap = resultMap;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public LinkedHashMap<String, Integer> call() throws Exception {
        BaiDuSearch.sougouSearch(resultMap, optionAnalyzeItem, question, options);
        BaiDuSearch.sougouSearch2(resultMap, optionAnalyzeItem, question, options);
        return resultMap;
    }
}
