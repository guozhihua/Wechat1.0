package com.weixin.utils.util;

import java.util.regex.Pattern;

/**
 * 银行卡判断
 * Created by :Guozhihua
 * Date： 2017/8/10.
 */
public class CardCodeValidateUtils {
    public static void main(String[] args) {
        String card = "6214830116573931";
        System.out.println("      card: " + card);
        System.out.println("check code: " + getBankCardCheckCode(card));
        System.out.println("   card id: " + card + getBankCardCheckCode(card));
        System.out.println(checkBankCard(card));
    }


    /**
     * 检验手机号码合不合规则
     * @param phoneNum
     * @return
     */
    public static  boolean checkPhoneNum(String phoneNum){
        return Pattern.matches("^((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$", phoneNum);
    }



    /**
     * 校验银行卡卡号是不是正确的
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            throw new IllegalArgumentException("Bank card code must be number!");
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
