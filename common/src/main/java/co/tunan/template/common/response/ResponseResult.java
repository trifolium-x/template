package co.tunan.template.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Title:请求返回的Json包装类
 * Description:请求返回的Json包装类
 *
 * @author trifolium
 * @version 1.0
 */
@Data
@Slf4j
public class ResponseResult<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String message = "";

    private boolean success;

    private ResultCode code;

    /**
     * 返回成功结果
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        result.success = true;
        result.code = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 成功结果
     */
    public static <T> ResponseResult<T> success() {
        ResponseResult<T> result = new ResponseResult<>();
        result.success = true;
        result.code = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 成功结果
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.success = true;
        result.data = data;
        result.message = message;
        result.code = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 返回失败的结果
     */
    public static <T> ResponseResult<T> failure(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setMessage(message);
        result.setCode(ResultCode.FAILURE);
        result.success = false;
        return result;
    }

    /**
     * 返回失败的结果
     */
    public static <T> ResponseResult<T> failure(String message, ResultCode code) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setMessage(message);
        result.setCode(code);
        result.success = false;
        return result;
    }

    /**
     * 异常结果
     */
    public static <T> ResponseResult<T> error(String message, Throwable throwable) {
        log.error(message, throwable);
        ResponseResult<T> result = new ResponseResult<>();
        result.success = false;
        result.setCode(ResultCode.EXCEPTION);
        result.setMessage(message);
        return result;
    }

    /**
     * 返回异常结果
     */
    public static <T> ResponseResult<T> error(String message, Throwable throwable, ResultCode code) {
        log.error(message, throwable);
        ResponseResult<T> result = new ResponseResult<>();
        result.setMessage(message);
        result.setCode(code);
        result.success = false;

        return result;
    }

}
