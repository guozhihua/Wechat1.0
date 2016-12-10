package hxs.weixin.parent.test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * Created by :Guozhihua
 * Date： 2016/12/5.
 */
public class Test1 {
//    private static JSONObject gson = new JSONObject();
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        A a = new A();
        a.setName("a");
        B b = new B();
        b.setName("a");
//        System.out.println(gson.toJSONString(b));
        System.out.println(gson.toJson(b));
    }
}
