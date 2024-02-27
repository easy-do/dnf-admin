package plus.easydo.dnf.exception;

/**
 * @author yuzhanfeng
 * @Date 2024-01-12 17:34
 * @Description 许可异常
 */
public class LicenseException extends BaseException{

    public LicenseException() {
        super();
    }

    public LicenseException(String message) {
        super(message);
    }

    public LicenseException(String errCode, String message) {
        super(errCode, message);
    }

    public LicenseException(String errCode, String message, Throwable cause) {
        super(errCode, message, cause);
    }

    public LicenseException(String errCode, Throwable cause) {
        super(errCode, cause);
    }
}
