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

    protected String message;

    protected boolean success;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

}
