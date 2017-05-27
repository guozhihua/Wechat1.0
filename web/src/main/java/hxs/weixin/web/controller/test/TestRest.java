package hxs.weixin.web.controller.test;

import com.weixin.entity.chat.User;
import com.weixin.utils.util.HTTPClientUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.Response;

/**
 * Created by :Guozhihua
 * Date： 2017/5/27.
 */
public class TestRest {
    public static void main(String[] args) {
        try{
            ResteasyClient client = new ResteasyClientBuilder().build();  // <-- Error occurs here
            ResteasyWebTarget target = client.target("http://localhost:20881/servers/user/getUser?ticket=123131");
            Response response = target.request().get();
            User value = response.readEntity(User.class);
            System.out.println(value.getUserName());
            response.close();

//            String res = HTTPClientUtils.httpGetRequestJson("http://localhost:20881/servers/user/getUser?ticket=123131");
//            System.out.println(res);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
