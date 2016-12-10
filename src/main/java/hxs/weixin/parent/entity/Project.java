package hxs.weixin.parent.entity;

import java.util.Date;

public class Project extends SuperVO{
    private Integer projectId;

    private String projectName;

    private String projectDesc;

    private String projectPrice;

    private String ctbCode;

    private String projectImg1;

    private String projectImg2;

    private String projectImg3;

    private String projectImg4;

    private String titleImgUrl;

    private Integer status;
    
    private Date lastTime;
    
    private Integer effectiveTime;

    public Integer getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Integer effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	private Date createTime;

    private Date updateTime;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc == null ? null : projectDesc.trim();
    }

    public String getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(String projectPrice) {
        this.projectPrice = projectPrice == null ? null : projectPrice.trim();
    }

    public String getCtbCode() {
        return ctbCode;
    }

    public void setCtbCode(String ctbCode) {
        this.ctbCode = ctbCode == null ? null : ctbCode.trim();
    }

    public String getProjectImg1() {
        return projectImg1;
    }

    public void setProjectImg1(String projectImg1) {
        this.projectImg1 = projectImg1 == null ? null : projectImg1.trim();
    }

    public String getProjectImg2() {
        return projectImg2;
    }

    public void setProjectImg2(String projectImg2) {
        this.projectImg2 = projectImg2 == null ? null : projectImg2.trim();
    }

    public String getProjectImg3() {
        return projectImg3;
    }

    public void setProjectImg3(String projectImg3) {
        this.projectImg3 = projectImg3 == null ? null : projectImg3.trim();
    }

    public String getProjectImg4() {
        return projectImg4;
    }

    public void setProjectImg4(String projectImg4) {
        this.projectImg4 = projectImg4 == null ? null : projectImg4.trim();
    }

    public String getTitleImgUrl() {
        return titleImgUrl;
    }

    public void setTitleImgUrl(String titleImgUrl) {
        this.titleImgUrl = titleImgUrl == null ? null : titleImgUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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