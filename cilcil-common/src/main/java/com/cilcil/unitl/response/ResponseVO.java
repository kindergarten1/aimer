package com.cilcil.unitl.response;





import com.cilcil.unitl.exception.BaseException;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:28
 * @Version 1.0
 */

public class ResponseVO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3420391142991247367L;
    private Boolean success;
    private String message;
    private Integer code;
    private T result;
    private Long timestamp;

    public static <T> ResponseVO<T> ok() {
        ResponseVO<T> result = new ResponseVO();
        result.message = "success";
        result.success = true;
        result.code = ResponseCode.OK;
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public static <T> ResponseVO<T> ok(String message) {
        ResponseVO<T> result = new ResponseVO();
        result.success = true;
        result.code = ResponseCode.OK;
        result.message = message;
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public static <T> ResponseVO<T> ok(T data) {
        ResponseVO<T> result = new ResponseVO();
        result.message = "操作成功！";
        result.success = true;
        result.code = ResponseCode.OK;
        result.result = data;
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public static <T> ResponseVO<T> error(String message) {
        ResponseVO<T> result = new ResponseVO();
        result.message = message;
        result.success = false;
        result.code = ResponseCode.PRECONDITION_FAILED;
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public static <T> ResponseVO<T> error(ResponseStatus status) {
        ResponseVO<T> result = new ResponseVO();
        result.message = status.getMessage();
        result.success = false;
        result.code = status.getCode();
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public static <T> ResponseVO<T> error(Integer code, String message) {
        ResponseVO<T> result = new ResponseVO();
        result.message = message;
        result.success = false;
        result.code = code;
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public static <T extends BaseException> ResponseVO<T> ofException(T t) {
        return error(t.getCode(), t.getMessage());
    }

    public static <T> ResponseVO<T> exception(String message) {
        ResponseVO<T> result = new ResponseVO();
        result.message = message;
        result.success = false;
        result.code = ResponseCode.INTERNAL_SERVER_ERROR;
        result.timestamp = System.currentTimeMillis();
        return result;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }

    public T getResult() {
        return this.result;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setResult(final T result) {
        this.result = result;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseVO)) {
            return false;
        } else {
            ResponseVO<?> other = (ResponseVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$success = this.getSuccess();
                    Object other$success = other.getSuccess();
                    if (this$success == null) {
                        if (other$success == null) {
                            break label71;
                        }
                    } else if (this$success.equals(other$success)) {
                        break label71;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                label57: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label57;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label57;
                    }

                    return false;
                }

                Object this$result = this.getResult();
                Object other$result = other.getResult();
                if (this$result == null) {
                    if (other$result != null) {
                        return false;
                    }
                } else if (!this$result.equals(other$result)) {
                    return false;
                }

                Object this$timestamp = this.getTimestamp();
                Object other$timestamp = other.getTimestamp();
                if (this$timestamp == null) {
                    if (other$timestamp == null) {
                        return true;
                    }
                } else if (this$timestamp.equals(other$timestamp)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseVO;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $success = this.getSuccess();
        result = result * 59 + ($success == null ? 43 : $success.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : $result.hashCode());
        Object $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : $timestamp.hashCode());
        return result;
    }

    public String toString() {
        return "ResponseVO(success=" + this.getSuccess() + ", message=" + this.getMessage() + ", code=" + this.getCode() + ", result=" + this.getResult() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public ResponseVO() {
    }
}
