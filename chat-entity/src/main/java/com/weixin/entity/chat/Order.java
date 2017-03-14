package com.weixin.entity.chat;

import java.util.Date;

public class Order {
    private Integer id;

    private String projectId;

    private Integer buyType;

    private String outTradeNo;

    private String userUniqueId;
    
    private String puserVoucherId;

    public String getPuserVoucherId() {
		return puserVoucherId;
	}

	public void setPuserVoucherId(String puserVoucherId) {
		this.puserVoucherId = puserVoucherId;
	}

	private String state;

    private Date orderDate;

    private String description;

    private String cashAmout;

    private String couponAmout;

    private String totalAmout;

    private String serialNumber;

    private String projectName;
    
    private Date endTime;

    public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId == null ? null : userUniqueId.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCashAmout() {
        return cashAmout;
    }

    public void setCashAmout(String cashAmout) {
        this.cashAmout = cashAmout == null ? null : cashAmout.trim();
    }

    public String getCouponAmout() {
        return couponAmout;
    }

    public void setCouponAmout(String couponAmout) {
        this.couponAmout = couponAmout == null ? null : couponAmout.trim();
    }

    public String getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(String totalAmout) {
        this.totalAmout = totalAmout == null ? null : totalAmout.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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