package hxs.weixin.web.test.thread;

/**
 * Created by :Guozhihua
 * Date： 2017/3/2.
 */
public class MyThreadTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
             new   RunThread().sayHello();
            }
        }).start();
        try{
//            Thread.sleep(1000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                RunThread.sayBye();
            }
        }).start();

    }
}
