package xchat.sys;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by :Guozhihua
 * Date： 2018/1/9.
 */
public class HuaJiaoInviteCode {
    public static void main(String[] args) {

        String codes = getCode().trim();
        int num = 0;
        String[] codearrc = codes.split(" ");
        Set<String> codesSet =new HashSet<>();
        for (String code11 : codearrc) {
            if (!code11.trim().equals("") && code11.length() == 6) {
                codesSet.add(code11);
            }
        }
        String url = "https://activity.huajiao.com/Answer/activeByCode?code=XXXX&callback=_jsonpc8kswhch3upoxtjy1763whfr";
        for (String code11 : codesSet) {
            if (!code11.trim().equals("") && code11.length() == 6) {
                String zz = url.replace("XXXX", code11);
                StringBuilder json = new StringBuilder();
                String result = "";
                try {
                    URL u = new URL(zz);
                    HttpURLConnection uc = (HttpURLConnection) u.openConnection();
                    uc.setDoOutput(true);
                    uc.setDoInput(true);
                    uc.setUseCaches(false);
                    uc.setRequestMethod("GET");
                    //我的
//                    uc.addRequestProperty("Cookie", "token=ZWUJJXqVWl2iyiYpZw--FEKoyRIqQ76l5581");
                    //朱森
//                    uc.addRequestProperty("Cookie", "token=ZWUJK.dqWl7CgqAIAQ--XFCKXuoCggdze185");
                    //王亮
//                    uc.addRequestProperty("Cookie", "token=ZWUJK_W0Wl7FIDi.bw--SLaGHznfZDfic6ee");
                    //mhf
                    uc.addRequestProperty("Cookie", "token=ZWUJKOZaWmHjogoUbA--3hdLSHizRN1ie978");

                    // 志强的
//                    uc.addRequestProperty("Cookie", "token=ZWUDsXMKWl3FfjvN.w--2DU2A6Cpipfb8a50");
                    //海枝
//                    uc.addRequestProperty("Cookie", "token=ZWUJKZAIWl3GOFTI1w--deFsBqBt8O9D84ea");
                    BufferedReader bd = new BufferedReader(new InputStreamReader(uc.getInputStream(), "GBK"));
                    String s = null;
                    while ((s = bd.readLine()) != null) {
                        json.append(s);
                    }
                    bd.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result = json.toString();
                System.out.println(result);
                num++;

            }
        }
        System.out.println("一共增加了：" + num);

    }


    public static  String getCode(){

        String codes = "BWJYXV BRDF84 BTTP1B BR6K53 BNQ119 BTWPUA BBHCSL BXH2K6 BK2MPC BKE8CH BKNGYS BFHJBG " +
                "BM2VHY BXFJ8Y BRRMVG BXWN0T BGQXWH BWXR5Z bhhr2j bp5U8V BLQJJC BMVJ6Q B3LJVJ BX2CNW BFUSTH  " +
                "Bneamc br7gne b34htq blm5v7 BCW5WL B7S109 BGCGSN BVN410 brblan BN6TMR BH1FN6 BRZSJ6 " +
                "BFDF9X BXAKUG BNHQUB BF8JRW BCPEM4 BMWUMM B7JFAV BB1BA8 bmz219 BKFSJA BFFWYJ  " +
                "BK5TBS BV17KV BKV1SX BH1F7U BL78VT B7LJ95 BPLF7F BP8036 BMBF9F BKJG9B BRTYGG BM4EE3 BLQHP7  " +
                "BFAZBV BXAYCJ B785MP BMU2P6 BHFXM6 BPXLDB BMLQ3C BKVA3B bm7f9j  " +
                "B3WL3R BR7HXC BM1X2W BMXADS BF3CNV BL8SDL BBMKAK BGRN4V  bkqgkn BG89YF " +
                "BNFX7V BCYS9P BWLS22 BVYZAK BRWUPZ bp60en bwnthk BCL9GJ BGB76B BBPY1L BB5VMU BNCRVJ BFJGZ4 BXXSW2 " +
                " BVX703 BNMEUL B7U05S BCGLTY BF37F3 BCL3UK BVQR9C BRTEN1 BLFMBH BL7D3N " +
                "B3SY73 BKUY0Q BLDYZL BLX7K9 BXZP9X BL99Z2 BXSKKJ BWQBQE " +
                "BTV7VD BLWJTG BV7BUV BX7BSJ BTCHBP BL5W12 BGVDMA BB0N83 " +
                "BLX4WE BBON83 B34KDM BF8BXK " +
                "BMG3JV BC0HR2 BC0MDE BGSA9B " +
                "B7NMCM BHXTCR BB2CUH BP0QZ0 BWKYQF BXGREA BC5DUU BXJXUN " +
                "BR53V5 BMR9SJ BHV9JA BBS5WB BB0M6J BHW80E BTRFSH BH6VZC " +
                "B7L2CT BBU14B BP3EED B74MH5 BBX5DT BKC686 BGNX3G BBX5DT BKC686 BGNX3G B38KGA  BX45BN BFM01D BF1BZ3 BB3EMZ BP10YE BRHVPL B72DSD BN8U6Y B7P23F BKFBZ1 BL2LF8 BP7QRG " +
                " BL971U  BRBLAN BN6TMR BH1FN6 BRZSJ6 BFYFCC BL2GPG bwzqs2 br1c88 BKRSFS BLT2Q7 BNYXSH BXFCPH B7H0QD BR6K53 BWJPHP BTWPUA BBHCSL BXH2K6 BK2MPC BKE8CH BKNGYS BFHJBG  " +
                " BWWA4V BP4MLD BGF57Z BF09DX  BL2GPG bwzqs2 br1c88  " +
                " BX4AK1" + "BRDB2D BP6LZ2 BFT0ZH B7YXLL BV04TL " + "BBMDC3  " + "BLGX96 " + "BCZJFJ " + "BHR4W5 " +
                "BRVVA0 " + "BFWDMR " + "BG69C " + "BG69CF " + "BM0ZSX " + "BVBN5Y " + "BNCTBY " + "B7AWNC " + "BMS364 " + "BNCTBY " + "BWRLTN " +
                "BG69CF " + "BH1F7U " + "BL78VT " + "BXYUK7 " + "BTVSTA " + "BC9YEK " + "BGP9L6 " + "BLJUH5 " + "BBYQQW " + "B70NQ4 " + "BKKTQ0 " + "BP0F81 " +
                "BLXUPG " + "BG69CF " + "BKKTQ0 BP0F81 BLXUPG BG69CF BCGEFT BXYW5A BCANFFBVWX0C BGWPNG B7X590 BXLQXL BF23N1 BBHVRG BTMTT8 BVF3AS BFVPZG BCL4GH "
                + "BBMDC3 BLGX96  BCZJFJ BFWDMR BHR4W5 BRVVA0 " +
                " BG69CF BH1F7U BL78VT BXYUK7 BTVSTA BC9YEK BGP9L6 BLJUH5 BBYQQW B70NQ4 BPE0GV B7DCVJ BWMJ5X " +
                " BNQPHK BWMJ5X BG69CF BM0ZSX BVBN5Y BNCTBY B7AWNC BMS364 BNCTBY BWRLTN BGMDKY  BR20KJ ";
        String code2 = "BRZD2Y BCWT8H BH2GPE BL7XQN BNW5SB BFUTQ5 BP7UFH BG117X  BRBLAN BN6TMR BH1FN6 BRZSJ6 BFYFCC BVBN5Y " +
                " BX45BN BFM01D BF1BZ3 BB3EMZ BP10YE BRHVPL B72DSD BN8U6Y B7P23F BKFBZ1 BL2LF8 BTMTT8  BFVPZG BWMJ5X " +
                " BFZGW4 BR6K53 BNQ119 BTWPUA BBHCSL BXH2K6 BK2MPC BKE8CH BKNGYS BFHJBGBM2VHY BXFJ8Y BRRMVG BXWN0T BGQXWH BWXR5Z bhhr2j bp5U8V " +
                " BWEUYS BH8PKB BH9RJ5 BBBWH1 B3EL5W BCEDRZ BBV5KY BVCZ9U brblan BN6TMR BH1FN6 BRZSJ6  BRBLAN BN6TMR BH1FN6 BRZSJ6 BFYFCC  " +
                " BVYZAK BRWUPZ BHLYLS  BP7UFH BJKE8CH BKNGYS  bwq27p btrg0h BBNXSW BVNQ9S BB1PVD BKS5FD B73BH7 BF0LJE BP7466 BN7UVX  " +
                " bp60en bwnthk BCL9GJ BPHEJ4 BGSZ7P BV2MB2 BLLUSY BL7XQN BNW5SB BMBF9F BKJG9B BRTYGG BM4EE3 BLQHP7 BL71VY BPX9L5 BBYLUX " +
                " BGB76B BBPY1L BB5VMU b35jne bvkvkr bbgaf7 bha64v BHPBWX bc54h BL7XQN BNW5SB bfb9x8 bp672t bm640w BL7XQN BNW5SB " +
                " BNCRVJ BFJGZ4  BXXSW2 BLJUH5 BBYQQW B70NQ4 BKKTQ0 BP0F81 BLXUPG BX45BN BFM01D BF1BZ3 BNCTBY BNB232 BVY70C BG2N2J " +
                " BG69CF BM0ZSX BWRLTN  BNCTBY BTMTT8 BK5FS3  BPX6ZZ B3KYD1 BWTKBB BHBYKD BK65C2 B3R85L BVGX9Y B35LEK brblan BN6TMR BH1FN6 BRZSJ6 "
                + " BBMDC3  BLGX96  BCZJFJ  BFWDMR  BHR4W5   BRVVA0   BCNRQ1    BV3QKD  BMKBRV  BF1Y4P  BKNPAW  BBYLUX  BPHEJ4  BGSZ7P  BHLYLS" +
                " BVXZY9  BM7F9J  BBMDC3  BLGX96   BCZJFJ    BFWDMR  BHR4W5   BRVVA0   BG69CF  BH1F7U  BL78VT  BXYUK7  BTVSTA  BC9YEK  BGP9L6  BLJUH5 " +
                " BBYQQW  B70NQ4  BKKTQ0  BP0F81  BLXUPG  BG69CF  BCGEFT  BXYW5A  BCANFF  BVWX0C   BGWPNG   B7X590   BXLQXL   BF23N1   BBHVRG   BTMTT8  " +
                " BGJZJB  BWP759  BBBWH1 B3EL5W BCEDRZ BBV5KY BVCZ9U B7RFLX BPAJWM BPHKA7 BV7QEM BX6FZB";
        code2 = codes + code2;

        return  code2 ;
    }
}
