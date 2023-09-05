package com.cilcil.login.model.dto;



import com.cilcil.unitl.constants.CommonMsg;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * @Author SlyAimer
 * @Date 2023/8/26 20:41
 * @Version 1.0
 */
@Data
@Schema(name = "UserRegisterDTO", description = "用户注册信息")
public class UserRegisterDTO {

    @Size(min = 4, max = 10, message = CommonMsg.LOGIN_NAME_FORMAT_ERROR_FOUR_TO_TEN_DIGIT)
    @NotBlank(message = CommonMsg.LOGIN_NAME_COULD_NOT_BE_NULL)
    @Schema(name = "loginName",description = "登录名称")
    private String loginName;

    @Schema(name = "loginAccount",description = "登录账号")
    private String loginAccount;

    @Size(min = 6, max = 15, message = CommonMsg.PASSWORD_FORMAT_ERROR_SIX_TO_FIFTEEN_DIGIT)
    @NotBlank(message = CommonMsg.LOGIN_PASSWORD_COULD_NOT_BE_NULL)
    @Schema(name = "loginPassword",description = "登陆密码")
    private String loginPassword;

    @Schema(name = "RegisterType", description = "注册类型（邮箱、qq、微信 等 目前只做邮箱注册）")
    private String RegisterType;

    @NotBlank(message = CommonMsg.VERIFICATION_CODE_COULD_NOT_BE_NULL)
    @Schema(name = "verificationCode", description = "验证码")
    private String verificationCode;

    @Schema(name = "inviterCode", description = "邀请人邀请码")
    private String inviterCode;

    @Schema(name = "isForceValidateCode", description = "是否强制校验邀请码")
    private Boolean isForceValidateCode;

}
