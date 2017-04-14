package com.weixin.utils.tyfdc;

import java.io.Serializable;

/**
 *
 * 具体到每一间的房子实体
 * `uid` int(11) NOT NULL AUTO_INCREMENT,
 `houseId` varchar(30) DEFAULT NULL COMMENT '小区id',
 `houseObjId` varchar(30) DEFAULT NULL COMMENT '几号楼',
 `floor` varchar(20) DEFAULT NULL COMMENT '层',
 `floorNum` varchar(20) DEFAULT NULL COMMENT '门牌号',
 `sumMianji` varchar(20) DEFAULT NULL COMMENT '总面积',
 `useMianji` varchar(20) DEFAULT NULL COMMENT '使用面积',
 `gongtanMianji` varchar(20) DEFAULT NULL COMMENT '公摊面积',
 `status` varchar(20) DEFAULT NULL COMMENT '状态',
 `forUsed` varchar(60) DEFAULT NULL COMMENT '用途',
 `type` varchar(30) DEFAULT NULL COMMENT '性质',
 `price` varchar(10) DEFAULT NULL COMMENT '价格',
 `address` varchar(70) DEFAULT NULL COMMENT '详细地址',
 * Created by :Guozhihua
 * Date： 2017/4/14.
 */
public class Mhouse implements Serializable {
    public static  String insertSql="insert into m_house(uid,houseId,houseObjId,floor,floorNum" +
            ",sumMianji,useMianji,gongtanMianji,status,forUsed,type,price,address)" +
            " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static String updateSql="update m_house set status =? and price =? where mid=?";
    private  int uid;
    private String houseId;
    private String houseObjId;
    private String floor;
    private String floorNum;
    private String sumMianji;
    private String useMianji;
    private String gongtanMianji;
    private String status;
    private String forUsed;
    private String type;
    private String price;
    private String address;
    private String mid;//具体的房间唯一标识

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseObjId() {
        return houseObjId;
    }

    public void setHouseObjId(String houseObjId) {
        this.houseObjId = houseObjId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getSumMianji() {
        return sumMianji;
    }

    public void setSumMianji(String sumMianji) {
        this.sumMianji = sumMianji;
    }

    public String getUseMianji() {
        return useMianji;
    }

    public void setUseMianji(String useMianji) {
        this.useMianji = useMianji;
    }

    public String getGongtanMianji() {
        return gongtanMianji;
    }

    public void setGongtanMianji(String gongtanMianji) {
        this.gongtanMianji = gongtanMianji;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getForUsed() {
        return forUsed;
    }

    public void setForUsed(String forUsed) {
        this.forUsed = forUsed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
