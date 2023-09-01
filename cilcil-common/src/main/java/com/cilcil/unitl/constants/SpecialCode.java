package com.cilcil.unitl.constants;


/**
 * @Author SlyAimer
 * @Date 2023/8/26 20:41
 * @Version 1.0
 */
public class SpecialCode {

    private SpecialCode (){

    }
    /** 手机号正则校验 */
    public static final String PHONE_REGEXP = "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0-35689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[13589]\\d)\\d{7}$";
    /**
     * JWT 在 Redis 中保存的key前缀
     */
    public static final String REDIS_JWT_KEY_PREFIX = "security:jwt:";
}
