package xchat.dao;


import org.springframework.stereotype.Repository;
import xchat.pojo.UserTicket;

/**
 * 
 * <br>
 * <b>功能：</b>UserTicketDao<br>
 */
 @Repository
public interface UserTicketDao extends BaseDao<UserTicket> {
	
	public UserTicket selectByTicket(String ticket);
}
