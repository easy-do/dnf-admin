package plus.easydo.dnf.vo;


/**
 * 操作消息提醒
 *
 * @author yuzhanfeng
 */
public class DataResult<T> extends R<T> {

    public static final long serialVersionUID = 1L;


    public DataResult() {
    }

    /**
     * 功能描述
     *
     * @param code code
     * @param message message
     * @author laoyu
     */
    public DataResult(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static <T> DataResult<T> initResult() {
        return new DataResult<>();
    }


    /**
     * OK
     *
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> ok() {
        DataResult<T> r = initResult();
        r.setSuccess(true);
        r.setCode(SUCCESS_CODE);
        r.setMessage(SUCCESS_MSG);
        return r;
    }


    /**
     * OK
     *
     * @param data data
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> ok(T data) {
        DataResult<T> r = initResult();
        r.setData(data);
        r.setSuccess(true);
        r.setCode(SUCCESS_CODE);
        r.setMessage(SUCCESS_MSG);
        return r;
    }

    /**
     * OK
     *
     * @param msg msg
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> okMsg(String msg) {
        DataResult<T> r = initResult();
        r.setSuccess(true);
        r.setCode(SUCCESS_CODE);
        r.setMessage(msg);
        return r;
    }

    /**
     * OK
     *
     * @param data data
     * @param msg  msg
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> ok(T data, String msg) {
        DataResult<T> r = initResult();
        r.setData(data);
        r.setSuccess(true);
        r.setCode(SUCCESS_CODE);
        r.setMessage(msg);
        return r;
    }

    /**
     * FAIL
     *
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> fail() {
        DataResult<T> r = initResult();
        r.setCode(ERROR_CODE);
        r.setMessage(FAIL_MSG);
        r.setSuccess(false);
        return r;
    }

    /**
     * FAIL
     *
     * @param msg msg
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> fail(String msg) {
        DataResult<T> r = initResult();
        r.setCode(ERROR_CODE);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    /**
     * FAIL
     *
     * @param data data
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> fail(T data) {
        DataResult<T> r = initResult();
        r.setData(data);
        r.setSuccess(false);
        r.setCode(ERROR_CODE);
        r.setMessage(FAIL_MSG);
        return r;
    }

    /**
     * FAIL
     *
     * @param data data
     * @param msg  msg
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> fail(T data, String msg) {
        DataResult<T> r = initResult();
        r.setSuccess(false);
        r.setData(data);
        r.setCode(ERROR_CODE);
        r.setMessage(msg);
        return r;
    }

    /**
     * FAIL
     *
     * @param code code
     * @param msg  msg
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> fail(int code, String msg) {
        DataResult<T> r = initResult();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    /**
     * result
     *
     * @param data data
     * @param code code
     * @param msg  msg
     * @param success  success
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> result(T data, int code, String msg, boolean success) {
        DataResult<T> r = initResult();
        r.setData(data);
        r.setSuccess(success);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }
    /**
     * result
     *
     * @param code code
     * @param msg  msg
     * @param success  success
     * @return plus.easydo.common.result
     * @author laoyu
     */
    public static <T> DataResult<T> result(int code, String msg, boolean success) {
        DataResult<T> r = initResult();
        r.setCode(code);
        r.setSuccess(success);
        r.setMessage(msg);
        return r;
    }

}
