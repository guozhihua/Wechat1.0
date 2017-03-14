package hxs.weixin.web.dubbo.impl;

import hxs.weixin.web.dubbo.DemoService;
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
