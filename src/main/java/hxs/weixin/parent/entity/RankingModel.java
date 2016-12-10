package hxs.weixin.parent.entity;

/**
 * 对战结果model
 *
 * @author zhuchaowei
 *         2016年7月13日
 *         Description
 */
public class RankingModel {

    private String userId;

    private String userName;

    private String currentRank;

    private Long userUseTime;

    private Double userGetScore;

    private String headImgUrl;

    private String gradeCode;

    private String subjectCode;

    private String pageNum;

    private String pageSize;

    private String bookType;

    private String ctbCode;

    private String score;

    private String sex;

    private String mobile;

    public RankingModel() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCtbCode() {
        return ctbCode;
    }

    public void setCtbCode(String ctbCode) {
        this.ctbCode = ctbCode;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
    }

    public Long getUserUseTime() {
        return userUseTime;
    }

    public void setUserUseTime(Long userUseTime) {
        this.userUseTime = userUseTime;
    }

    public Double getUserGetScore() {
        return userGetScore;
    }

    public void setUserGetScore(Double userGetScore) {
        this.userGetScore = userGetScore;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}
