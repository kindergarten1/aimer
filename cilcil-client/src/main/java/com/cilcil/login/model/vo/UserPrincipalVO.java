package com.cilcil.login.model.vo;

import com.cilcil.modules.sysuser.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Author: izanagi
 * @Date: 2020-06-28 15:06
 * @Description: UserPrincipalVO
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipalVO implements UserDetails {
    public UserPrincipalVO(){

    }

    /**
     * id
     */
    private String id;

    private String uid;

    /**
     * 登陆名 存放用户id
     */
    private String loginName;

    /**
     * 密码
     */
    private String loginPwd;

    /**
     * 头像
     */
    private String commonAvatar;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 备注名称
     */
    private String noteName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 电话
     */
    private String memberPhone;

    /**
     * 备注
     */
    private String remark;

    /**
     * isLocked
     */
    private Integer isLocked;

    /**
     * isDeleted
     */
    private Integer isDeleted;

    /**
     * 用户角色
     */
    List<UserRoleVO> roles;

    /**
     * 用户权限列表
     */
    private Collection<? extends GrantedAuthority> authorities;


    public static UserPrincipalVO createMemberInfo(SysUser sysUser) {
        UserPrincipalVO  userPrincipalVO = new UserPrincipalVO();
        userPrincipalVO.setLoginName(sysUser.getLoginName());
        return userPrincipalVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPwd;
    }

    @Override
    public String getUsername() {
        return loginName;
    }

    /**
     * 账号未过期
     * @return {@link Boolean}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号未锁定
     * @return {@link Boolean}
     */
    @Override
    public boolean isAccountNonLocked() {
        return Objects.equals(this.isLocked, 0);
    }

    /**
     * 凭证未过期
     * @return {@link Boolean}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号未禁用
     * @return {@link Boolean}
     */
    @Override
    public boolean isEnabled() {
        return Objects.equals(this.isDeleted, 0);
    }
}
