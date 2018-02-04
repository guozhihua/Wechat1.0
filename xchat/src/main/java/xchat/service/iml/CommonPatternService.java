package xchat.service.iml;

import com.baidu.aip.nlp.AipNlp;
import xchat.pojo.Information;
import xchat.service.PatternService;
import xchat.similarity.Similarity;
import xchat.similarity.impl.BaiDuSimilarity;
import xchat.similarity.impl.SimilarityFactory;
import xchat.sys.SearchFactory;
import xchat.sys.Utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by lingfengsan on 2018/1/18.
 *
 * @author lingfengsan
 */
public class CommonPatternService implements PatternService {
    private static final String QUESTION_FLAG = "?";
    private static int[] startX = {100,100, 80};
    private static int[] startY = {300,300, 300};
    private static int[] width = {900,900, 900};
    private static int[] height = {900,900, 700};
    private SearchFactory searchFactory=new SearchFactory();
    private int patterSelection;
    private int searchSelection;

    public void setPatterSelection(int patterSelection) {
        this.patterSelection = patterSelection;
    }

    public void setSearchSelection(int searchSelection) {
        this.searchSelection = searchSelection;
    }




    @Override
    public String run(String question,String[] answers) throws UnsupportedEncodingException {
        //       记录开始时间
        long startTime;
        //       记录结束时间
        long endTime;
        StringBuilder sb = new StringBuilder();
        startTime = System.currentTimeMillis();
        //获取图片
        //获取问题和答案
        System.out.println("检测到题目");
        if (question == null) {
            sb.append("问题不存在，继续运行\n");
            return sb.toString();
        } else if (answers.length < 1) {
            sb.append("检测不到答案，继续运行\n");
            return sb.toString();
        }
        sb.append("问题:").append(question).append("\n");
        sb.append("答案：\n");
        for (String answer : answers) {
            sb.append(answer).append("\n");
        }
        //求相关性
        int numOfAnswer = answers.length > 3 ? 4 : answers.length;
        double[] result=new double[numOfAnswer];
        Similarity[] similarities= new Similarity[numOfAnswer];
        FutureTask[] futureTasks=new FutureTask[numOfAnswer];
        BaiDuSimilarity.setClient(new AipNlp("10732092","pdAtmzlooEbrcfYG4l0kIluf",
                "sHjPBnKt58crPuFogTgQ5Wki0TrHYO2c"));
        for (int i = 0; i < numOfAnswer; i++) {
            similarities[i]= SimilarityFactory.getSimlarity(2,question,answers[i]);
            futureTasks[i]=new FutureTask<Double>(similarities[i]);
            new Thread(futureTasks[i]).start();
        }
        for (int i = 0; i < numOfAnswer; i++) {
            while (true){
                if(futureTasks[i].isDone()){
                    break;
                }
            }
            try {
                result[i]=  (Double) futureTasks[i].get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //搜索

        FutureTask[] futureQuestion = new FutureTask[1];
        futureQuestion[0]=new FutureTask<Long>(searchFactory.getSearch(searchSelection,question,false));
        new Thread(futureQuestion[0]).start();
        //根据pmi值进行打印搜索结果
        int[] rank = Utils.rank(result);
        for (int i : rank) {
            sb.append(answers[i]);
            sb.append(" 相似度为:").append(result[i]).append("\n");
        }
        sb.append("--------最终结果-------\n");
        sb.append(answers[rank[rank.length-1]]);
        endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;
        sb.append("\r\n用时：").append(excTime).append("s").append("\n");
        return sb.toString();
    }
}
