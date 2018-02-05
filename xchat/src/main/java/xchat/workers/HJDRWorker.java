package xchat.workers;

import com.weixin.utils.RandomUtils;
import com.weixin.utils.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.aop.QueueUtils;
import xchat.controller.task.HuangjinDarenAnswer;
import xchat.listeners.MsgEvent;
import xchat.sys.MessageType;
import xchat.sys.SessionBucket;

import java.util.*;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
@Component("HJDRWorker")
@Scope(value = "singleton")
public class HJDRWorker extends BaseWorker {

    public static boolean start = false;

    @Override
    public  void stop(){
        this.start=false;
    }
    private static Logger logger = Logger.getLogger(HJDRWorker.class);
    @Autowired
    private QueueUtils queueUtils;

private  SessionBucket sessionBucket=SessionBucket.getInstance();

    public boolean startWorker() {
        if (!start) {
            queueUtils.sendMsg(null, MessageType.HUANG_JIN_DAREN);
            start=!start;
        }
        return start;
    }


    @Override
    public void execute(MsgEvent msgEvent) throws Exception {

        while (start) {
            if(sessionBucket.getAllsessionMap()==null||sessionBucket.getAllsessionMap().size()==0){
                start=false;
            }
            try {
                boolean getNewQuestion = false;
                int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                String questins = null;
                if (i == 20 || i == 12 || i == 19) {
                    questins = HuangjinDarenAnswer.getQuestins();
                }
                Map<String, String> mes = new HashMap<>();
                if (questins == null || "000000".equals(questins)) {
                    mes.clear();
                    mes.put("type", "1");
                    mes.put("val", "题目快来了."+ DateUtil.getCurrentTime());
                } else {
                    mes.clear();
                    mes.put("type", "2");
                    mes.put("val", questins);
                    if (!HuangjinDarenAnswer.allQuestions.contains(questins)) {
                        HuangjinDarenAnswer.allQuestions.add(questins);
                        getNewQuestion = true;
                    }
                }
                String msg = mes.get("type").toString().concat("@").concat(mes.get("val").toString());
                TextMessage text = new TextMessage(msg);
                  List<String> closedSession=new ArrayList<>();
                    for (WebSocketSession session : sessionBucket.getAllsessionMap().values()) {
                        if (session.isOpen()) {
                            try{
                                session.sendMessage(text);
                            }catch (Exception ex){
                                logger.error("执行任务异常结束",ex);
                            }
                        }else{
                            closedSession.add(session.getId());
                        }
                    }
                if(closedSession.size()>0){
                    for (String sessionId : closedSession) {
                        sessionBucket.removeSessionId(sessionId);
                    }
                }
                try {
                    if (getNewQuestion) {
                        Thread.sleep(12000);
                    } else {
                        Thread.sleep(1000);
                    }
                }catch (Exception ex){
                  logger.error("执行任务异常结束",ex);
                }

            } catch (Exception ex) {
                logger.error("执行任务异常结束",ex);
            }
        }

    }
}
