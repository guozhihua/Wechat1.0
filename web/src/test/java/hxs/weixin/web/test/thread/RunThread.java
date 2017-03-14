package hxs.weixin.web.test.thread;

/**
 *
 * synchronized 静态和非静态方法上的锁，是在类上的。
 * 在方法内部锁定this的锁定的实例对象，和类范围的全局锁不同
 * 同级别（都是在方法上加了锁）的同步方法要顺序执行。
 *
 *
 * Created by :Guozhihua
 * Date： 2017/3/2.
 */
public class RunThread  {


    public   void sayHello(){
//        synchronized (this){
            for(int i = 0 ;i<10;i++){
                try{
//                    Thread.sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                System.out.println("hello ------"+i);
            }
//        }

    }

    public  static   synchronized  void sayBye(){
        for(int j =0 ;j<10;j++){
            System.out.println("bye ------"+j);
        }

    }
}
