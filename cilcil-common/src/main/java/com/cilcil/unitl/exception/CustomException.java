package com.cilcil.unitl.exception;

/**
 * @Author SlyAimer
 * @Date 2023/9/1 17:24
 * @Version 1.0
 */

public class CustomException extends RuntimeException{
    private Integer code;
    private String message;
    private String description;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CustomException)) {
            return false;
        } else {
            CustomException other = (CustomException)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label49: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label49;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label49;
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

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CustomException;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = super.hashCode();
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String toString() {
        return "CustomException(code=" + this.getCode() + ", message=" + this.getMessage() + ", description=" + this.getDescription() + ")";
    }
}
