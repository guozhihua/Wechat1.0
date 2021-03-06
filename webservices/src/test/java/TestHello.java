import com.weixin.utils.util.JsonUtils;
import com.yj.ws.service.App;
import com.yj.ws.service.IsService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

import javax.xml.namespace.QName;

/**
 * Created by :Guozhihua
 * Date： 2017/4/20.
 */
public class TestHello {


    /**
     * 获取webserice对象，可以调用所有的方法，累死于hessian 代理
     */
    @Test
    public void hell() {
        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setServiceClass(com.yj.ws.service.IsService.class);
        bean.setAddress("http://localhost:8080/ws/soap/helloService");
        IsService helloWorldService = (IsService) bean.create();
        String result = helloWorldService.sayHi("Kevin");
        App result2 = helloWorldService.getAppById("234");
        System.out.println(result);
        System.out.println(result2.getName());
    }


    /**
     * 简单的通过完整的接口地址访问
     * 这种方式无法对于接口对象的限制太死了
     */
    @Test
    public void hell2() {
        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setServiceClass(com.yj.ws.service.IsService.class);
        bean.setAddress("http://localhost:8082/ws/soap/helloService/app");
        IsService helloWorldService = (IsService) bean.create();
        App result = helloWorldService.getAppById("1231");
        System.out.println(JsonUtils.toJson(result));
    }

    /**
     * 简单的通过完整的接口地址访问
     * 这种方式无法对于接口对象的限制太死了
     */
    @Test
    public void wsdl() {

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf
                .createClient("http://localhost:8080/ws/soap?wsdl");
        // url为调用webService的wsdl地址
        QName name = new QName("http://service.ws.yj.com/", "getAppById");
        // namespace是命名空间，methodName是方法名
        String xmlStr = "aaaaaaaa";
        // paramvalue为参数值
        Object[] objects;
        try {
            objects = client.invoke(name, xmlStr);
            System.out.println( objects[0].toString());
            App app = (App) objects[0];
            System.out.println(app.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
