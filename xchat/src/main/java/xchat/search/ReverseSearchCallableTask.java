package xchat.search;

import com.alibaba.fastjson.JSON;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.sys.SessionBucket;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by :Guozhihua
 * Date： 2018/2/9.
 */
public class ReverseSearchCallableTask implements Callable<LinkedHashMap<String, Integer>> {
    private String question;

    private String[] options;

    private String questionMainInfo;

    private LinkedHashMap<String, Integer> resultMap;

    public ReverseSearchCallableTask(String question, String[] options, String questionMainInfo, LinkedHashMap<String, Integer> resultMap) {
        this.question = question;
        this.options = options;
        this.questionMainInfo = questionMainInfo;
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
        long time1 = System.currentTimeMillis();
        BaiDuSearch.reveseAnsweroptions(options, resultMap, questionMainInfo);
        SecketUtils.sendMsgToAll("relation", JSON.toJSONString(resultMap));
        System.out.println("reverse used time is :" + (float) (System.currentTimeMillis() - time1) / 1000);
        return resultMap;
    }
}
