package xchat.sys;

import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
public class WebModel implements Serializable {
    public Object datas;

    public String code;

    public  String msg;

    public  static  WebModel getInstance(){

        return new WebModel("200","success");
    }

    public void isFail(){
        this.code="500";
        this.msg="error";
    }
    public WebModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public WebModel(Object datas, String code, String msg) {
        this.datas = datas;
        this.code = code;
        this.msg = msg;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
