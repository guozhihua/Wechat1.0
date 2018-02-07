package xchat.pojo;

import xchat.sys.SuperVO;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */

public class Question extends SuperVO {
    private Integer quesionId;

    private String status ;

    private String question;
    private String[] options;

    public Question(String question, String[] options) {
        this.question = question;
        this.options = options;
    }

    public Question() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuesionId() {
        return quesionId;
    }

    public void setQuesionId(Integer quesionId) {
        this.quesionId = quesionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
