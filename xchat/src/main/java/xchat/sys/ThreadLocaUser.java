package xchat.sys;

import xchat.pojo.UserTicket;

/**
 * Created by :Guozhihua
 * Date： 2018/1/25.
 */
public class ThreadLocaUser {
    private static ThreadLocal<UserTicket> userTicketThreadLocal=new ThreadLocal<>();
    public static void set(UserTicket userTicket){
        userTicketThreadLocal.set(userTicket);
    }
    public  static UserTicket get(){
        return userTicketThreadLocal.get();
    }
    public static void remove(){
        userTicketThreadLocal.remove();
    }

}
