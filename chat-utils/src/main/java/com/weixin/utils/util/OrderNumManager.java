package com.weixin.utils.util;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by :Guozhihua
 * Date： 2017/4/12.
 */
public class OrderNumManager {


    /**
     * 东八区时间
     */
    private static Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

    /**
     * 24位订单号。年月日+1000内随机数字+uuid随机哈希码
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineId = new Random().nextInt(10000);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表最终长度为4
        // d 代表参数为正数型
        return  ""+calendar.get(Calendar.YEAR)+String.format("%02d",calendar.get(Calendar.MONTH)+1)+String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH))+String.format("%04d",machineId) + String.format("%012d", hashCodeV);
    }
}
