package com.cilcil.login.model.vo;

import com.cilcil.modules.login.entity.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @Author SlyAimer
 * @Date 2023/9/5 9:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@Schema(name = "UserPrincipalVO",description = "用户登录返回信息")
public class UserPrincipalVO {

    @Schema(name = "accessToken",description = "授权token")
    public  String accessToken;

    @Schema(name = "tokenType",description = "token类型")
    public  String tokenType;

    @Schema(name = "refreshToken",description = "清楚token")
    public  String refreshToken;

    @Schema(name = "sysUser",description = "返回的用户信息")
    public SysUser sysUser;

}
