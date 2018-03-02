package com.weixin.utils.tyfdc;

import java.io.Serializable;

/**
 *
 *
 * `id` char(10) DEFAULT NULL COMMENT 'id',
 `houseName` varchar(100) DEFAULT NULL COMMENT '项目名称',
 `areaName` varchar(30) DEFAULT NULL COMMENT '区域名称',
 `houseType` varchar(30) DEFAULT NULL COMMENT '房屋类型',
 `yushouCode` varchar(40) DEFAULT NULL COMMENT '预售号',
 `kaifashang` varchar(60) DEFAULT NULL COMMENT '开发商',
 `startTime` varchar(20) DEFAULT NULL COMMENT '开始时间',
 `endTime` varchar(20) DEFAULT NULL COMMENT '完工时间',
 `address` varchar(60) DEFAULT NULL COMMENT '地址',
 `mianji` varchar(10) DEFAULT NULL COMMENT '销售面积',
 `zhuangxiu` varchar(20) DEFAULT NULL COMMENT '装修',
 `nuan` varchar(20) DEFAULT NULL COMMENT '供暖',
 `qi` varchar(20) DEFAULT NULL COMMENT '天然气',
 `water` varchar(20) DEFAULT NULL COMMENT '供水',
 `junjia` varchar(20) DEFAULT NULL COMMENT '均价',
 `shigong` varchar(60) DEFAULT NULL COMMENT '施工
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class House implements Serializable {
    private int pid;

    private String id;

    private String houseName;
    private String areaName;
    private String houseType;
    private String yushouCode;
    private String kaifashang;
    private String startTime;
    private String endTime;
    private String address;
    private String mianji;
    private String zhuangxiu;
    private String nuan;
    private String qi;
    private String water;
    private String junjia;
    private String shigong;
    private String remark;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getYushouCode() {
        return yushouCode;
    }

    public void setYushouCode(String yushouCode) {
        this.yushouCode = yushouCode;
    }

    public String getKaifashang() {
        return kaifashang;
    }

    public void setKaifashang(String kaifashang) {
        this.kaifashang = kaifashang;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMianji() {
        return mianji;
    }

    public void setMianji(String mianji) {
        this.mianji = mianji;
    }

    public String getZhuangxiu() {
        return zhuangxiu;
    }

    public void setZhuangxiu(String zhuangxiu) {
        this.zhuangxiu = zhuangxiu;
    }

    public String getNuan() {
        return nuan;
    }

    public void setNuan(String nuan) {
        this.nuan = nuan;
    }

    public String getQi() {
        return qi;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getJunjia() {
        return junjia;
    }

    public void setJunjia(String junjia) {
        this.junjia = junjia;
    }

    public String getShigong() {
        return shigong;
    }

    public void setShigong(String shigong) {
        this.shigong = shigong;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
