package com.weixin.utils;

/**
 * Created by :Guozhihua
 * Date： 2017/8/9.
 */
public class RandomUtils {

    /**
     * 返回指定个数 随机大小写英文字母
     * @param number
     * @return
     */
    public static String getRandom(int number){
        StringBuilder sb =new StringBuilder("");
        for(int i=0;i<number;i++){
            int a = (int) (Math.random() * 2) + 3;
            char c= ' ';
            if(a==3){
                c='a';
            }else if(a==4){
                c='A';
            }
         c=(char)(c+(int)(Math.random()*26));
            sb.append(c);
        }
        return  sb.toString();
    }
    public static String getUpCaseRandom(int number){
        StringBuilder sb =new StringBuilder("");
        for(int i=0;i<number;i++){
            char c='A';
            c=(char)(c+(int)(Math.random()*26));
            sb.append(c);
        }
        return  sb.toString();
    }
    public static String getLowerCaseRandom(int number){
        StringBuilder sb =new StringBuilder("");
        for(int i=0;i<number;i++){
            char c='a';
            c=(char)(c+(int)(Math.random()*26));
            sb.append(c);
        }
        return  sb.toString();
    }

    /**
     * 生产随机码  ，本例子的 区间每次增值不得超过16,超过之后的charNum自动加1
     * @param charNum  字符个数
     * @param counter  初始系数值
     * @param index 增量值，用于外部控制增值区间 （建议不超过16）
     * @return
     */
    public static  String getRandomCode(int charNum,int index ,long counter){
        if(index>16){
            charNum++;
        }
        counter=counter*17+index;
        String r =getUpCaseRandom(charNum);
        String indexString = r.concat(String.format("%07d", counter));
        return  indexString;
    }

    public static void main(String[] args) {
        getRandomCode(1,2,99999999);
    }


}
