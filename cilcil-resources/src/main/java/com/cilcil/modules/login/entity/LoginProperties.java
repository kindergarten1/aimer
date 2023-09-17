package com.cilcil.modules.login.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */
@Schema(description = "登录常量类")
@Data
@Configuration
public class LoginProperties {

    private static final long serialVersionUID = 1L;

    @Schema(description = "是否单点登录")
    private Boolean ssoFlag = true;

    @Schema(description = "是否保存权限")
    private Boolean saveRoleFlag = true;

    @Schema(description = "用户Token过期天数,保存Token的时间,Token没了也不能自动登录")
    private Integer userTokenInvalidDays = 30;

    @Schema(description = "用户保存登录天数,选择了自动登录,记录保存的时间")
    private Integer userSaveLoginTokenDays = 7;

    @Schema(description = "最大登录失败次数")
    private Integer maxLoginFailTimes = 10;

    @Schema(description = "登录失败超限后锁定分钟")
    private Integer loginFailMaxThenLockTimes = 10;

    @Schema(description = "全局限流")
    private Boolean allLimiting = false;

    @Schema(description = "全局限流个数")
    private Integer allLimitingSize = 100;

    @Schema(description = "全局限流单位时长")
    private Long allLimitingTime = 1000L;

    @Schema(description = "单IP限流")
    private Boolean oneLimiting = false;

    @Schema(description = "单IP限流个数")
    private Integer oneLimitingSize = 100;

    @Schema(description = "单IP限流单位时长")
    private Long oneLimitingTime = 1000L;

    public static final String HTTP_HEADER = "accessToken";

    public static final String SAVE_LOGIN_PRE = "saveLogin";

    public static final String HTTP_TOKEN_PRE = "TOKEN_PRE:";

    public static final String USER_TOKEN_PRE = "USER_TOKEN:";
}
