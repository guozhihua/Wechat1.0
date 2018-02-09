package xchat.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import xchat.aop.QueueUtils;
import xchat.controller.task.HuangjinDarenAnswer;
import xchat.listeners.MsgEvent;
import xchat.pojo.Question;
import xchat.service.QuestionService;
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

    public static volatile int  count =0;

    public static volatile boolean start = false;

    @Override
    public void stop() {
        this.start = false;
    }

    private static Logger logger = LoggerFactory.getLogger(HJDRWorker.class);
    @Autowired
    private QueueUtils queueUtils;

    @Autowired
    private QuestionService questionService;

    private SessionBucket sessionBucket = SessionBucket.getInstance();

    public boolean startWorker() {
        if (!start) {
            logger.info("开始触发获取黄金答人 题目的任务");
            System.out.println(this);
            queueUtils.sendMsg(null, MessageType.HUANG_JIN_DAREN);
            start = !start;
        }
        return start;
    }


    @Override
    public void execute(MsgEvent msgEvent) throws Exception {
        long sleepTime = 500;
        while (start) {
            if (sessionBucket.getAllsessionMap() == null || sessionBucket.getAllsessionMap().size() == 0) {
                this.start = false;
                break;
            }
            try {
                Question question = HuangjinDarenAnswer.getQuestins();
                Map<String, String> mes = new HashMap<>();
                if (question == null) {
                    int id =new Random().nextInt(3)+35;
                    question = questionService.queryById(id+"");
                    HuangjinDarenAnswer.allQuestions.clear();
                    sleepTime = 8000;
                } else if ("000000".equals(question.getStatus())) {
                    mes.clear();
                    sleepTime = 1400;
                } else if ("999999".equals(question.getStatus())) {
                    mes.clear();
                    mes.put("type", "1");
                    mes.put("val", "票据Auth 出错了,请马上设置Auth!");
                    sleepTime = 6000;
                }
                if (question != null && "200".equals(question.getStatus())) {
                    mes.clear();
                    mes.put("type", "2");
                    mes.put("val", question.getQuestion());
                    if (!HuangjinDarenAnswer.allQuestions.contains(question.getQuestion())) {
                        HuangjinDarenAnswer.allQuestions.add(question.getQuestion());
                        if (question.getQuestionId() == null) {
                            question.setCreateTime(new Date());
                            questionService.insertSelective(question);
                        }
                        System.out.println("question:" + question.getQuestion());
                        //发布查询答案的任务信息
                        Map<String, Object> map = new HashMap<>();
                        map.put("question", question);
                        queueUtils.sendMsg(map, MessageType.HUANG_JIN_DR_ANSWER);
                        sleepTime = 17000;
                    } else {
                        sleepTime = 1500;
                    }
                }
                if (!mes.isEmpty()) {
                    //以下是发送消息
                    String msg = mes.get("type").toString().concat("@").concat(mes.get("val").toString());
                    TextMessage text = new TextMessage(msg);
                    List<String> closedSession = new ArrayList<>();
                    for (WebSocketSession session : sessionBucket.getAllsessionMap().values()) {
                        if (session.isOpen()) {
                                session.sendMessage(text);
                        } else {
                            closedSession.add(session.getId());
                        }
                    }
                    if (closedSession.size() > 0) {
                        for (String sessionId : closedSession) {
                            sessionBucket.removeSessionId(sessionId);
                        }
                    }
                }
                Thread.sleep(sleepTime);
            } catch (Exception ex) {
                logger.error("执行任务异常结束", ex);
            }
        }
    }
}
