package hxs.weixin.parent.entity;

import java.util.Date;

public class VGame {
    private Long id;

    private String userId;

    private Long score;

    private Long overplusTimes;
    
    private Long rank;

    private String userNick;

    private Long voucherFlag;

    private Date createTime;

    private Date updateTime;
    
    private String flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getOverplusTimes() {
        return overplusTimes;
    }

    public void setOverplusTimes(Long overplusTimes) {
        this.overplusTimes = overplusTimes;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick == null ? null : userNick.trim();
    }

    public Long getVoucherFlag() {
        return voucherFlag;
    }

    public void setVoucherFlag(Long voucherFlag) {
        this.voucherFlag = voucherFlag;
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

	public Long getRank() {
	
		return rank;
	}

	public void setRank(Long rank) {
	
		this.rank = rank;
	}

	
	public String getFlag() {
	
		return flag;
	}

	public void setFlag(String flag) {
	
		this.flag = flag;
	}

	public String getUserId() {
	
		return userId;
	}

	public void setUserId(String userId) {
	
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "VGame [id=" + id + ", userId=" + userId + ", score=" + score
				+ ", overplusTimes=" + overplusTimes + ", rank=" + rank
				+ ", userNick=" + userNick + ", voucherFlag=" + voucherFlag
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", flag=" + flag + "]";
	}
	
}