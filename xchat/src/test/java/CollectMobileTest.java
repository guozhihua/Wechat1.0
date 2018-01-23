import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xchat.pojo.DeviceInfo;
import xchat.pojo.HuoShanMobileInfo;
import xchat.service.DeviceInfoService;
import xchat.service.HuoShanMobileInfoService;
import xchat.sys.HuoShanRegsiterUitls;
import xchat.sys.YimaCodeConfig;
import xchat.sys.YimaUtils;

import java.util.*;

/**
 * Created by :Guozhihua
 * Date： 2018/1/16.
 */
public class CollectMobileTest extends  BaseTest{

    @Autowired
    private HuoShanMobileInfoService huoShanMobileInfoService;

    @Autowired
    private DeviceInfoService deviceInfoService;
    @Test
    public void colletMobile(){
        List<HuoShanMobileInfo> list = new ArrayList<>();
        try {
            YimaUtils.releaseAll();
            Map<String,Object> paramns= new HashMap<>();
            paramns.put("status","0");
            List<DeviceInfo> deviceInfos = deviceInfoService.queryList(paramns, 1, 10);
            for(int i=0;i<10;i++){
                DeviceInfo deviceInfo = deviceInfos.get(i);
                HuoShanRegsiterUitls.setRandomInfo(deviceInfo);
                String s = HuoShanRegsiterUitls.checkMobile(null,"170_171_172");
                if(s.endsWith(":200")){
                    String phone = s.replace(":200", "");
                    HuoShanMobileInfo huoShanMobileInfo=new HuoShanMobileInfo();
                    huoShanMobileInfo.setMobile(phone);
                    //尚未注册
                    huoShanMobileInfo.setStatus(0);
                    list.add(huoShanMobileInfo);
                    break;
                }
            }
            for(HuoShanMobileInfo h:list){
                System.out.println("找到了没有注册的号码："+h.getMobile());
                huoShanMobileInfoService.insertSelective(h);
            }
            for(HuoShanMobileInfo h:list){
                String authCode = YimaUtils.getAuthCode(YimaCodeConfig.Huoshan_code, h.getMobile());
                System.out.println(h.getMobile()+"的验证码信息是:"+authCode);
                String s = HuoShanRegsiterUitls.registeruUser(h.getMobile(), authCode);
                if(s.startsWith("200:")){
                    s=s.substring(s.indexOf(":")+1);
                    String[] split = s.split("@");
                    String sessionKey=split[0];
                    String a38YB = HuoShanRegsiterUitls.setCode("A38YB", sessionKey);
                    System.out.println(a38YB);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            YimaUtils.releaseAll();
        }


    }

    @Test
    public void releaseAll(){
        YimaUtils.releaseAll();
    }


    /**
     * 初始化设备信息进行注册
     */
    @Test
    public void deviceRegistger(){
        for(int i=0;i<10;i++){
            String s = HuoShanRegsiterUitls.localPush();
            if(StringUtils.isNotBlank(s)){
                JSONObject jsonObject = HuoShanRegsiterUitls.diviceRegister(s);
                if(jsonObject!=null){
                    DeviceInfo deviceInfo = new DeviceInfo();
                    deviceInfo.setStatus(0);
                    deviceInfo.setCreateTime(new Date());
                    deviceInfo.setDeviceid(jsonObject.getString("device_id"));
                    deviceInfo.setIid(jsonObject.getString("install_id"));
                    deviceInfo.setUuid(jsonObject.getString(s));
                    deviceInfo.setOpenid(jsonObject.getString("openId"));
                    deviceInfoService.insertSelective(deviceInfo);
                }
            }
        }
    }


}
