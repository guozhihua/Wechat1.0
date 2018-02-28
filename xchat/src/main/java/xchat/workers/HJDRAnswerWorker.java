package xchat.workers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.listeners.MsgEvent;
import xchat.pojo.Question;
import xchat.search.BaiDuSearch;
import xchat.search.SearchResult;
import xchat.search.SecketUtils;
import xchat.sys.SessionBucket;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */
@Component("HJDRWorkerAnswer")
@Scope(value = "singleton")
public class HJDRAnswerWorker extends BaseWorker {

    private SessionBucket sessionBucket = SessionBucket.getInstance();

    @Override
    public void execute(MsgEvent msgEvent) throws Exception {
        Question question = (Question)msgEvent.getDatas().get("question");
        long t1=System.currentTimeMillis();
        SearchResult search = new BaiDuSearch().search(question.getQuestion(), question.getOptionArray());
        String right="这题我不会!";
        if(search!=null){
            right=search.getRightAnswer();
        }
        SecketUtils.sendMsgToAll("answer",right);
        long t2=System.currentTimeMillis();
        System.out.println("答案："+right+"  ，分析用时："+(float)(t2-t1)/1000);


    }

    @Override
    public void stop() {

    }
}
