package xchat.service;


import xchat.pojo.UserTicket;

/**
 * 
 * <br>
 * <b>功能：</b>UserTicketService<br>
 */
public interface UserTicketService extends BaseService<UserTicket> {
    public UserTicket selectByTicket(String ticket);
}
