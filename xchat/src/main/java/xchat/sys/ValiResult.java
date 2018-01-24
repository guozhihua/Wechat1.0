package xchat.sys;


import com.weixin.utils.responsecode.ResponseCode;

import java.io.Serializable;

/**
 * Created by :Guozhihua
 * Date： 2017/6/12.
 */
public class ValiResult implements Serializable {
    private  boolean isSuccess;
    private String message ;
    private ResponseCode httpCode;

    public ValiResult(boolean isSuccess, String message, ResponseCode httpCode) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.httpCode=httpCode;
    }

    public ValiResult() {
    }

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseCode getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(ResponseCode httpCode) {
		this.httpCode = httpCode;
	}
}
