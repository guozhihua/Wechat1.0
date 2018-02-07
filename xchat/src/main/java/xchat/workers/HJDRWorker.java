package xchat.workers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.aop.QueueUtils;
import xchat.controller.task.HuangjinDarenAnswer;
import xchat.listeners.MsgEvent;
import xchat.pojo.Question;
import xchat.sys.MessageType;
import xchat.sys.SessionBucket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/3/9.
 */
@Component("HJDRWorker")
@Scope(value = "singleton")
public class HJDRWorker extends BaseWorker {

    public static boolean start = false;

    @Override
    public void stop() {
        this.start = false;
    }

    private static Logger logger = Logger.getLogger(HJDRWorker.class);
    @Autowired
    private QueueUtils queueUtils;

    private SessionBucket sessionBucket = SessionBucket.getInstance();

    public boolean startWorker() {
        if (!start) {
            logger.info("开始触发获取黄金答人 题目的任务");
            queueUtils.sendMsg(null, MessageType.HUANG_JIN_DAREN);
            start = !start;
        }
        return start;
    }


    @Override
    public void execute(MsgEvent msgEvent) throws Exception {
        long sleepTime = 100;
        while (start) {
            if (sessionBucket.getAllsessionMap() == null || sessionBucket.getAllsessionMap().size() == 0) {
                this.start = false;
                break;
            }
            try {
                System.out.println("............");
                Question questins = HuangjinDarenAnswer.getQuestins();
                Map<String, String> mes = new HashMap<>();
//                questins = new Question("夜盲症是缺少那种维生素？", new String[]{"维生素A", "维生素E", "维生素E"});
//                questins = new Question("以下哪个人不是唐朝的诗人？", new String[]{"李白", "白居易", "苏轼"});
//                questins.setStatus("200");
                if (questins == null || "000000".equals(questins.getStatus())) {
                    mes.clear();
                    mes.put("type", "1");
                    mes.put("val", "题目正在路上狂奔......");
                    sleepTime = 1300;
                } else if ("999999".equals(questins.getStatus())) {
                    mes.clear();
                    mes.put("type", "1");
                    mes.put("val", "票据Auth 出错了,请马上设置Auth!");
                    sleepTime = 6000;
                } else if ("unstart".equals(questins.getStatus())) {
                    mes.clear();
                    mes.put("type", "1");
                    mes.put("val", "还没有到直播的时间呢！");
                    sleepTime = 50000;
                } else if ("200".equals(questins.getStatus())) {
                    mes.clear();
                    mes.put("type", "2");
                    mes.put("val", questins.getQuestion());
                    if (!HuangjinDarenAnswer.allQuestions.contains(questins.getQuestion())) {
                        HuangjinDarenAnswer.allQuestions.add(questins.getQuestion());
                        //发布查询答案的任务信息
                        Map<String, Object> map = new HashMap<>();
                        map.put("question", questins);
                        queueUtils.sendMsg(map, MessageType.HUANG_JIN_DR_ANSWER);
                        sleepTime = 15000;
                    } else {
                        sleepTime = 1500;
                    }
                }
                //以下是发送消息
                String msg = mes.get("type").toString().concat("@").concat(mes.get("val").toString());
                TextMessage text = new TextMessage(msg);
                List<String> closedSession = new ArrayList<>();
                for (WebSocketSession session : sessionBucket.getAllsessionMap().values()) {
                    if (session.isOpen()) {
                        try {
                            session.sendMessage(text);
                        } catch (Exception ex) {
                            logger.error("执行任务异常结束", ex);
                        }
                    } else {
                        closedSession.add(session.getId());
                    }
                }
                if (closedSession.size() > 0) {
                    for (String sessionId : closedSession) {
                        sessionBucket.removeSessionId(sessionId);
                    }
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception ex) {
                    logger.error("执行任务异常结束", ex);
                }

            } catch (Exception ex) {
                logger.error("执行任务异常结束", ex);
            }
        }

    }
}
