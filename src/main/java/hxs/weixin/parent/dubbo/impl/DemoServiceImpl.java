package hxs.weixin.parent.dubbo.impl;

import hxs.weixin.parent.dubbo.DemoService;
import org.springframework.stereotype.Component;

/**
 * Created by :Guozhihua
 * Date： 2016/12/9.
 */
@Component
public class DemoServiceImpl implements DemoService {

    @Override
    public String welCome(String msg){
        String serverMsg="server response : ==="+System.currentTimeMillis();
        System.out.println("custom coming ,msg is "+msg);
        return  serverMsg;
    }

}
