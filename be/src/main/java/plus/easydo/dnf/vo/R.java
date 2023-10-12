package plus.easydo.dnf.vo;




import cn.hutool.http.HttpStatus;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author yuzhanfeng
 */
public class R<T> implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS_CODE = HttpStatus.HTTP_OK;

    /**
     * 失败
     */
    public static final int ERROR_CODE = HttpStatus.HTTP_INTERNAL_ERROR;

    /**
     * 成功消息
     */
    public static final String SUCCESS_MSG = "操作成功";

    /**
     * 失败消息
     */
    public static final String FAIL_MSG = "操作失败";

    protected int code;

    protected T data;

    protected String msg;

    protected boolean success;

    protected int errorCode;

    protected String errorMessage;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
