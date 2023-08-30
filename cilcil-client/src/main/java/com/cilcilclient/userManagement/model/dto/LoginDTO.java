package com.cilcilclient.userManagement.model.dto;



import com.cilcilcommon.unitl.constants.CommonMsg;
import com.cilcilresources.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @Author SlyAimer
 * @Date 2023/8/26 20:41
 * @Version 1.0
 */
@Data
@Schema(name = "LoginDTO", description = "登录信息")
public class LoginDTO extends BaseEntity {

    @NotBlank(message = CommonMsg.LOGIN_ACCOUNT_COULD_NOT_BE_NULL)
    @Schema(name = "loginAccount", description = "登录账号")
    private String loginAccount;

    @NotBlank(message = CommonMsg.LOGIN_PASSWORD_COULD_NOT_BE_NULL)
    @Schema(name = "loginPassword", description = "登录密码")
    private String loginPassword;

    @NotBlank(message = CommonMsg.VERIFICATION_CODE_COULD_NOT_BE_NULL)
    @Schema(name = "verificationCode", description = "验证码")
    private String verificationCode;

}
