package hxs.weixin.parent.sys.exceptions;

/**
 * Created by :Guozhihua
 * Date： 2016/11/25.
 */
public class ProBaseException extends  Exception{

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
