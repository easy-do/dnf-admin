package plus.easydo.dnf.exception;


/**
 * 基础异常
 *
 * @author ruoyi
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_ERR_CODE = "BASE_ERROR";

    private static final String DEFAULT_ERR_MESSAGE = "基础异常";

    /**
     * 错误码
     */
    private String errCode;


    public BaseException() {
        super(DEFAULT_ERR_MESSAGE);
        this.errCode = DEFAULT_ERR_CODE;
    }

    public BaseException(String message) {
        super(message);
        this.errCode = DEFAULT_ERR_CODE;
    }

    public BaseException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public BaseException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public BaseException(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}
