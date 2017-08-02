package xchat.sys;

import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/8/2.
 */
public class WebModel implements Serializable {
    public Object datas;

    public int code;

    public  String msg;

    public  static  WebModel getInstance(){

        return new WebModel(400,"success");
    }

    public void isFail(){
        this.code=500;
        this.msg="error";
    }
    public WebModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public WebModel(Object datas, int code, String msg) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
