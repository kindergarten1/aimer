package com.cilcil.login.service;

import com.cilcil.modules.login.entity.PermissionVO;
import com.cilcil.modules.login.entity.RoleVO;
import com.cilcil.modules.login.entity.SysUser;
import com.cilcil.unitl.JudgeParam;
import com.cilcil.unitl.constants.CommonMsg;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Author SlyAimer
 * @Date 2023/9/5 9:35
 * @Version 1.0
 */
@Schema(description = "查询用户的角色和菜单权限")
public class SecurityUserDetails extends SysUser  implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<RoleVO> roles;

    private List<PermissionVO> permissions;

    @Override
    @Schema(description = "查询用户的角色和菜单权限")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        // 菜单权限
        if(permissions!=null && permissions.size() > 0){
            for (PermissionVO vo : permissions) {
                if(!JudgeParam.isNullOrUndefined(vo.getTitle()) && !JudgeParam.isNullOrUndefined(vo.getPath())) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(vo.getTitle()));
                }
            }
        }
        // 角色
        if(roles != null && roles.size() > 0){
            roles.forEach(role -> {
                if(!JudgeParam.isNullOrUndefined(role.getName())){
                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
                }
            });
        }
        return grantedAuthorityList;
    }

    @Override
    @Schema(description = "账号是否启用 0/1,1是启用")
    public boolean isEnabled() {
        return Objects.equals(1,this.getStatus());
    }

    @Schema(description = "账号是否过期")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Schema(description = "账号密码是否过期")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Schema(description = "账号是否禁用 0/1,1是启用")
    public boolean isAccountNonLocked() {
        return !Objects.equals(0, this.getStatus());
    }

    /**
     * 自定义类构造器
     * @param user 系统账户
     */
    public SecurityUserDetails(SysUser user) {
        if(user != null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.permissions  = user.getPermissions();
            this.roles = user.getRoles();
        }
    }
}