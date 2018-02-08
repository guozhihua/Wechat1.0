package xchat.pojo;

import xchat.sys.SuperVO;

import java.util.Date;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */

public class Question extends SuperVO {
    private Integer questionId;
    private java.util.Date createTime;//
    private String status ;

    private String question;

    private String options;

    public Question(String question, String options) {
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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String[] getOptionArray(){
        String[] split = options.split("#");
        return split;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
