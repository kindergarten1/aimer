package com.cilcil.unitl.constants;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 20:41
 * @Version 1.0
 */
public class CommonMsg {

    private CommonMsg() {

    }

    /**
     * 登录使用
     */
    public static final String INCORRECT_PHONE_FORMAT = "请填写正确的手机号!";
    public static final String PHONE_NUMBER_COULD_BE_NULL = "手机号不能为空!";
    public static final String LOGIN_NAME_COULD_NOT_BE_NULL = "登录名称不能为空";
    public static final String LOGIN_ACCOUNT_COULD_NOT_BE_NULL = "登录账号不能为空";
    public static final String LOGIN_PASSWORD_COULD_NOT_BE_NULL = "登录密码不能为空";
    public static final String ACCOUNT_OR_PASSWORD_ERROR = "账号或密码错误!";
    public static final String TOKEN_EXPIRED = "token 已过期，请重新登录！";
    public static final String TOKEN_OUT_OF_CTRL = "当前用户已在别处登录，请尝试更改密码或重新登录！";
    public static final String TOKEN_PARSE_ERROR = "token 解析失败，请尝试重新登录！";

    /**
     * 注册使用
     */
    public static final String  LOGIN_NAME_FORMAT_ERROR_FOUR_TO_TEN_DIGIT = "登录名称请限制在4-10位!";
    public static final String PASSWORD_FORMAT_ERROR_SIX_TO_FIFTEEN_DIGIT = "密码请限制在6-15位!";
    public static final String VERIFICATION_CODE_COULD_NOT_BE_NULL = "验证码不能为空";

}
