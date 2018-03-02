package com.weixin.utils.tyfdc;

import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class HouseObj implements Serializable {
    private  int nid;
    private String houseId;
    private String houseName;
    
    private String remark;
    private String objId;

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
