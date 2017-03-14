package com.weixin.utils.sys.enums;

/**
 * 不同场景获取不同的券，也有可能不同场景公用一种券，即code相同
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
public enum VoucherWayEnum {

    VOUCHER_WAY_FX("1","朋友圈分享获取代金券",1),
    VOUCHER_WAY_GM("2","通过购买获取代金券",25),
    VOUCHER_WAY_PH("3","排行榜分享获取代金券",20),
    VOUCHER_WAY_DC_YX("4","单词速拼游戏分享取代金券",1),
    VOUCHER_WAY_SS_YX("5","速算赢家游戏分享取代金券",1),
    VOUCHER_WAY_FIRST_LOGIN("6","首次登陆获取代金券",30),
    VOUCHER_WAY_FIRST_LOGIN_1("8","首次登陆获取代金券",50),
    VOUCHER_WAY_INVITED("7","受邀获取代金券",1);


    private String wayCode;

    private  String wayInfo;

    private  int amount;

    VoucherWayEnum(String wayCode, String wayInfo,int amount) {
        this.wayCode = wayCode;
        this.wayInfo = wayInfo;
        this.amount=amount;
    }

    public String getWayCode() {
        return wayCode;
    }

    public void setWayCode(String wayCode) {
        this.wayCode = wayCode;
    }

    public String getWayInfo() {
        return wayInfo;
    }

    public void setWayInfo(String wayInfo) {
        this.wayInfo = wayInfo;
    }

    public static VoucherWayEnum getEnumByCode(String code){
        for (VoucherWayEnum type : VoucherWayEnum.values()) {
            if(type.getWayCode().equals(code)){
               return type;
            }
        }
        return null;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
