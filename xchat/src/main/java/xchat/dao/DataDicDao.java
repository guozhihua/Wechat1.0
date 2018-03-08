package xchat.dao;


import org.springframework.stereotype.Repository;
import xchat.pojo.DataDic;
import xchat.pojo.User;

/**
 * 
 * <br>
 * <b>功能：</b>UserDao<br>
 */
 @Repository
public interface DataDicDao extends BaseDao<DataDic> {

 public DataDic queryValueByCode(String code);
}
