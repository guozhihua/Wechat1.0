package com.test;

import javax.jws.WebMethod;
import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/6/30.
 */
public class WebModel implements Serializable {

    private int responseCode;

    private String responseMsg;

    private Object data;

    public WebModel isFail(int code, String msg) {
        this.responseCode = code;
        this.responseMsg = msg;
        return this;
    }

    public WebModel success(Object data) {
        this.data = data;
        return this;
    }


    public WebModel() {
        this.responseCode = 200;
        this.responseMsg = "Success";
    }

    public WebModel(int responseCode, String responseMsg, Object data) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
