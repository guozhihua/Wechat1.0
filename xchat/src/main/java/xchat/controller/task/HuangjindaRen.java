package xchat.controller.task;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2018/2/5.
 */
public class HuangjindaRen implements Runnable {

    private WebSocketSession session;

    public HuangjindaRen(WebSocketSession session) {
        this.session = session;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true){
            boolean getNewQuestion = false;
            int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            long sleepTime = 1000L;
            boolean begin = false;
            if (i == 20 || i == 12 || i == 19) {
                begin = true;
            }
            while (session.isOpen()) {
            String questins = HuangjinDarenAnswer.getQuestins();
                Map<String, String> mes = new HashMap<>();
                if (questins == null || "000000".equals(questins)) {
                    mes.clear();
                    mes.put("type", "1");
                    mes.put("val", "题目快来了");
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
                TextMessage text=new TextMessage(msg);
                try {
                    session.sendMessage(text);
                    if (getNewQuestion) {
                        Thread.sleep(12000);
                    } else {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
