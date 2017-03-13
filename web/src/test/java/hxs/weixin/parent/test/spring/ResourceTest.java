package hxs.weixin.parent.test.spring;

import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by :Guozhihua
 * Date： 2017/3/2.
 */

public class ResourceTest {

    /**
     * 使用resourceBundle 的话，加载的时候，不要带后缀，切此种
     * 方式能够自己实现国际化，注意文件编码问题
     */
    @Test
    public void  loadPropertiersBundle(){
        try {
            //使用本机的local 为zhCN.
//            ResourceBundle  resourceBundle = ResourceBundle.getBundle("message");
            //使用美国的，默认本机
            Locale locale = new Locale("en","US");
            ResourceBundle  resourceBundle = ResourceBundle.getBundle("message", locale);
            Object val =  resourceBundle.getString("user_password");
            System.out.println(val);
        }catch (Exception ex){
            System.out.println("load properties exception,system will be exit!");
            ex.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * properties 首先读取的时候，必须是带后缀的，本身不带缓存，
     */
    @Test
    public void  loadPropertiersProper(){
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("message.properties");
            Object val =  properties.getProperty("user_password");
            System.out.println(val);
        }catch (Exception ex){
            System.out.println("load properties exception,system will be exit!");
            ex.printStackTrace();
            System.exit(0);
        }
    }

}
