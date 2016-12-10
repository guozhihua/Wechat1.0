package hxs.weixin.parent.entity;

import java.util.Date;

public class WGame {
    private Long id;

    private String userId;

    private Long gameTime;

    private Long overplusTimes;

    private String userNick;

    private Long voucherFlag;

    private Date createTime;

    private Date updateTime;

    private Long rank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Long getGameTime() {
        return gameTime;
    }

    public void setGameTime(Long gameTime) {
        this.gameTime = gameTime;
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
}