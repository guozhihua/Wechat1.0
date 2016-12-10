package hxs.weixin.parent.sys.exceptions;

/**
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
public class ProBaseException extends  Exception{
    //service 层业务逻辑异常
    private  static final  String SERVICE_EXP_CODE_SP="HSX_WX_10001";

    private String code ;
    public ProBaseException() {
    }
    private  String message;

    public ProBaseException(String message, String code) {
        super(message);
        this.code = code;
    }
    public ProBaseException(String message) {
        super(message);
    }
}
