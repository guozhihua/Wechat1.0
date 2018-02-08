package xchat.workers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.listeners.MsgEvent;
import xchat.pojo.Question;
import xchat.search.BaiDuSearch;
import xchat.search.SearchResult;
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
        SearchResult search = new BaiDuSearch().search(question.getQuestion(), question.getOptionArray());
        for(WebSocketSession session:sessionBucket.getAllsessionMap().values()){
            TextMessage textMessage =new TextMessage("answer@"+search.getRightAnswer());
            session.sendMessage(textMessage);
        }


    }

    @Override
    public void stop() {

    }
}
