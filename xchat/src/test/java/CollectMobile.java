import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xchat.pojo.HuoShanMobileInfo;
import xchat.service.HuoShanMobileInfoService;
import xchat.sys.HuoShanRegsiterUitls;
import xchat.sys.YimaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */
public class CollectMobile  extends  BaseTest{

    @Autowired
    private HuoShanMobileInfoService huoShanMobileInfoService;
    @Test
    public void colletMobile(){
        List<HuoShanMobileInfo> list = new ArrayList<>();
        try {
            YimaUtils.releaseAll();
            for(int i=0;i<20;i++){
                String s = HuoShanRegsiterUitls.checkMobile(null);
                if(s.endsWith(":200")){
                    String phone = s.replace(":200", "");

                    HuoShanMobileInfo huoShanMobileInfo=new HuoShanMobileInfo();
                    huoShanMobileInfo.setMobile(phone);
                    //尚未注册
                    huoShanMobileInfo.setStatus(0);
                    list.add(huoShanMobileInfo);


                }
            }
            for(HuoShanMobileInfo h:list){
                System.out.println("成功的找到了没有注册的号码："+h.getMobile());
                huoShanMobileInfoService.insertSelective(h);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            YimaUtils.releaseAll();
        }


    }


}
