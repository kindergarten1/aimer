package com.cilcil.modules.login.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.cilcil.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Transient;
import java.io.Serial;
import java.util.List;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */
@Data
@Entity
@Table(name = "sys_user")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "SysUser",description = "用户表实体类")
public class SysUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "昵称不能为空")
    @Size(max = 20, message = "昵称长度不能超过20")
    @Schema(name = "nickname",description ="姓名")
    private String nickname;

    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{4,16}$", message = "账号长度不合法")
    @Schema(name = "username",description ="账号")
    private String username;

    @NotNull(message = "密码不能为空")
    @Schema(name = "password",description ="密码")
    private String password;

    @Size(min = 2)
    @Schema(name = "passStrength",description ="密码强度")
    private String passStrength;

    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    @Schema(name = "mobile",description ="手机号")
    private String mobile;

    @Pattern(regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "邮箱格式错误")
    @Schema(name = "email",description ="邮箱")
    private String email;

    @Schema(name = "sex",description ="性别")
    private String sex;

    @Schema(name = "address",description ="区县")
    private String address;

    @Schema(name = "type",description ="用户类型")
    private Integer type;

    @Schema(name = "status",description = "启用状态")
    private Integer status = 1;

    @Schema(name = "avatar" ,description = "默认头像")
    private String avatar = "";

    @Transient
    @TableField(exist=false)
    @Schema(name = "defaultRole",description ="是否默认角色")
    private Integer defaultRole;

    @Transient
    @TableField(exist=false)
    @Schema( name = "permissions",description ="角色拥有的菜单列表")
    private List<PermissionVO> permissions;

    @Transient
    @TableField(exist=false)
    @Schema(name ="roles",description ="用户拥有的角色列表")
    private List<RoleVO> roles;
}
