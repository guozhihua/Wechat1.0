package com.weixin.entity.chat;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    @Override
	public String toString() {
		return "User [userId=" + userId + ", sex=" + sex + ", openId=" + openId
				+ ", userName=" + userName + ", trueName=" + trueName
				+ ", mobile=" + mobile + ", headImgUrl=" + headImgUrl
				+ ", firstLoginFlag=" + firstLoginFlag + ", status=" + status
				+ ", userToken=" + userToken + ", lastTime=" + lastTime
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

	private String userId;

    private Integer sex;

    private String openId;

    private String userName;

    private String trueName;

    private String mobile;

    private String headImgUrl;

    private Boolean firstLoginFlag;

    private Boolean status;

    private String userToken;

    private Date lastTime;

    private Date createTime;

    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

    public Boolean getFirstLoginFlag() {
        return firstLoginFlag;
    }

    public void setFirstLoginFlag(Boolean firstLoginFlag) {
        this.firstLoginFlag = firstLoginFlag;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken == null ? null : userToken.trim();
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}