package hxs.weixin.parent.entity;

import java.util.Date;

/**
 * \
 * <p/>
 * 代金券
 * Created by :Guozhihua
 * Date： 2016/11/24.
 */
public class Voucher extends  SuperVO  {

    private int voucherId;

    private int amount;

    private int effectiveDay;

    private Date creatTime;
    private String descinfo;

    private boolean isDel;



    public Voucher() {
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(int effectiveDay) {
        this.effectiveDay = effectiveDay;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getDescinfo() {
        return descinfo;
    }

    public void setDescinfo(String descinfo) {
        this.descinfo = descinfo;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }


}
