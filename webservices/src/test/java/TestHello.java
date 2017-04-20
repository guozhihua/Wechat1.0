import com.yj.ws.service.IsService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;

/**
 * Created by :Guozhihua
 * Date： 2017/4/20.
 */
public class TestHello {


    /**
     * 简单的通过完整的接口地址访问
     * 这种方式无法对于接口对象的限制太死了
     */
    @Test
    public void hell() {
        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setServiceClass(com.yj.ws.service.IsService.class);
        bean.setAddress("http://localhost:8082/ws/soap/helloService/hi");
        IsService helloWorldService = (IsService)bean.create();
        String result = helloWorldService.sayHi("Kevin");
        System.out.println(result);
    }


}
