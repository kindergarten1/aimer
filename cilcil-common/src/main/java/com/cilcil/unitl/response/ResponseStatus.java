package com.cilcil.unitl.response;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:31
 * @Version 1.0
 */
public enum ResponseStatus {

    CONTINUE(ResponseCode.CONTINUE, "服务器仅接收到部分请求，但一旦服务器并没有拒绝该请求，客户端应该继续发送其余请求"),
    SWITCHING_PROTOCOLS(ResponseCode.SWITCHING_PROTOCOLS, "服务器转换协议：服务器将遵从客户端的请求转换到另一种协议"),
    OK(ResponseCode.OK, "请求成功"),
    CREATED(ResponseCode.CREATED, "请求被创建完成"),
    ACCEPTED(ResponseCode.ACCEPTED, "供处理的请求已被接受，但是处理未完成"),
    NON_AUTHORITATIVE_INFORMATION(ResponseCode.NON_AUTHORITATIVE_INFORMATION, "文档已经正常的返回，但一些应答头可能不正确，因为使用的是文档拷贝"),
    NO_CONTENT(ResponseCode.NO_CONTENT, "没有新文档，浏览器应该继续显示原来的文档"),
    RESET_CONTENT(ResponseCode.RESET_CONTENT, "没有新文档，但浏览器应该重置它所显示的内容"),
    PARTIAL_CONTENT(ResponseCode.PARTIAL_CONTENT, "客户端发起了一个带有Range头的GET请求，服务器完成了它"),
    MULTIPLE_CHOICES(ResponseCode.MULTIPLE_CHOICES, "多重选择，链接列表，用户可以选择某链接到达的目的地，最多允许5个地址"),
    MOVED_PERMANENTLY(ResponseCode.MOVED_PERMANENTLY, "所有请求的页面已经转移至新的URL"),
    FOUND(ResponseCode.FOUND, "所有请求的页面已经临时转移至新的URL"),
    SEE_OTHER(ResponseCode.SEE_OTHER, "所有请求的页面可以在别的URL下被找到"),
    NOT_MODIFIED(ResponseCode.NOT_MODIFIED, "未按预期修改文档"),
    USE_PROXY(ResponseCode.USE_PROXY, "客户端请求的文档应该通过Location头所指明的代理服务器提取"),
    UNUSED(ResponseCode.UNUSED, "此代码被用于前一版本，目前已不再使用"),
    TEMPORARY_REDIRECT(ResponseCode.TEMPORARY_REDIRECT, "被请求的页面已经临时移至新的URL"),
    BAD_REQUEST(ResponseCode.BAD_REQUEST, "服务器未能理解请求"),
    UNAUTHORIZED(ResponseCode.UNAUTHORIZED, "被请求的页面需要用户名和密码"),
    PAYMENT_REQUIRED(ResponseCode.PAYMENT_REQUIRED, "此代码尚无法使用"),
    FORBIDDEN(ResponseCode.FORBIDDEN, "被请求页面的访问被禁止"),
    NOT_FOUND(ResponseCode.NOT_FOUND, "服务器无法找到被请求的页面"),
    METHOD_NOT_ALLOWED(ResponseCode.METHOD_NOT_ALLOWED, "请求中指定的方法不被允许"),
    NOT_ACCEPTABLE(ResponseCode.NOT_ACCEPTABLE, "服务器生成的响应无法被客户端接收"),
    PROXY_AUTHENTICATION_REQUIRED(ResponseCode.PROXY_AUTHENTICATION_REQUIRED, "用户必须首先使用代理服务器进行验证"),
    REQUEST_TIMEOUT(ResponseCode.REQUEST_TIMEOUT, "请求超出了服务器的等待时间"),
    CONFLICT(ResponseCode.CONFLICT, "由于冲突，请求无法被完成"),
    GONE(ResponseCode.GONE, "被请求的页面不可用"),
    LENGTH_REQUIRED(ResponseCode.LENGTH_REQUIRED, "\"Content-Length\"未被定义"),
    PRECONDITION_FAILED(ResponseCode.PRECONDITION_FAILED, "请求中的前提条件被服务器评估为失败"),
    REQUEST_ENTITY_TOO_LARGE(ResponseCode.REQUEST_ENTITY_TOO_LARGE, "请求的实体过大"),
    REQUEST_URL_TOO_LONG(ResponseCode.REQUEST_URL_TOO_LONG, "url过长"),
    UNSUPPORTED_MEDIA_TYPE(ResponseCode.UNSUPPORTED_MEDIA_TYPE, "媒介类型不被支持"),
    REQUESTED_RANGE_NOT_SATISFIABLE(ResponseCode.REQUESTED_RANGE_NOT_SATISFIABLE, "服务器不能满足客户在请求中指定的Range头"),
    EXPECTATION_FAILED(ResponseCode.EXPECTATION_FAILED, "Expect 的内容无法被满足"),
    INTERNAL_SERVER_ERROR(ResponseCode.INTERNAL_SERVER_ERROR, "请求未完成，服务器遇到不可预知的情况"),
    NOT_IMPLEMENTED(ResponseCode.NOT_IMPLEMENTED, "请求未完成"),
    BAD_GATEWAY(ResponseCode.BAD_GATEWAY, "请求未完成"),
    SERVICE_UNAVAILABLE(ResponseCode.SERVICE_UNAVAILABLE, "请求未完成，服务器临时过载宕机"),
    GATEWAY_TIMEOUT(ResponseCode.GATEWAY_TIMEOUT, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(ResponseCode.HTTP_VERSION_NOT_SUPPORTED, "服务器不支持请求中指明的HTTP协议版本");

    private Integer code;
    private String message;

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResponseStatus() {
    }

    private ResponseStatus(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }
}
