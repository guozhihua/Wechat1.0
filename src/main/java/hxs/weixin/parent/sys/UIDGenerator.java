package hxs.weixin.parent.sys;

import com.eeduspace.uuims.comm.util.base.FormatUtils;

import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
/**
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
    public class UIDGenerator {
        private static final int[] DEFAULT_CONFOUNDER = new int[]{3, 6, 7, 1, 8, 9, 5, 2};

        public UIDGenerator() {
        }
        public static String getShortUUID() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }

        public static String getUUID() {
            return UUID.randomUUID().toString();
        }

        private static long confuse(long num, int[] confounder) {
            String tempStr = num + "";
            int length = confounder.length;
            int numLength = tempStr.length();
            if(length < numLength) {
                throw new RuntimeException("confounder length must greater then number length, " + length + " : " + numLength);
            } else {
                String output = "";
                char[] input = tempStr.toCharArray();
                int confounderIndex = Integer.parseInt(input[input.length - 1] + "") % 8;
                int paddingLength = length - numLength;

                int e;
                for(e = 0; e < paddingLength; ++e) {
                    confounderIndex = (confounderIndex + 1) % 8;
                    output = output + confounder[confounderIndex] % 10;
                }

                for(e = 0; e < numLength; ++e) {
                    confounderIndex = (confounderIndex + 1) % 8;
                    output = output + (Integer.parseInt(input[e] + "") + confounder[confounderIndex]) % 10;
                }

                try {
                    return Long.parseLong(output);
                } catch (Exception var11) {
                    throw new RuntimeException("confuse number overflow : " + output);
                }
            }
        }

        public static String generateSysUID(String sysFlag) {
            if(sysFlag != null && sysFlag.length() == 2) {
                return sysFlag + getShortUUID();
            } else {
                throw new RuntimeException("sysFlag must be 2 length");
            }
        }

        public static String generateBizUID(long sequence, String bizFlag) {
            return generateBizUID(sequence, bizFlag, new Date());
        }

        public static String generateBizUID(long sequence, String bizFlag, Date date) {
            if(bizFlag != null && bizFlag.length() == 2) {
                String dateStr = FormatUtils.formatDate(date, "yyMMdd", (TimeZone)null);
                return bizFlag + "A" + dateStr + confuse(sequence, DEFAULT_CONFOUNDER);
            } else {
                throw new RuntimeException("bizFlag must be 2 length");
            }
        }
    }
