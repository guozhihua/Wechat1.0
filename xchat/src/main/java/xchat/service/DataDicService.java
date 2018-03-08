package xchat.service;

import xchat.pojo.DataDic;
import xchat.pojo.User;

/**
 * 
 * <br>
 * <b>功能：</b>UserService<br>
 */
public interface DataDicService extends BaseService<DataDic> {

    DataDic queryValueByCode(String code);
}
