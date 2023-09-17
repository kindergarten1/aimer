package com.cilcil.unitl.security;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cilcil.modules.login.entity.*;
import com.cilcil.modules.login.mapper.SysUserMapper;
import com.cilcil.unitl.JudgeParam;
import com.cilcil.unitl.exception.CustomException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:29
 * @Version 1.0
 */
@Schema(name = "鉴权工具类")
@Component
public class SecurityUtil {

    @Resource  private LoginProperties loginProperties;
    @Autowired private StringRedisTemplate redisTemplate;
    @Resource  private SysUserMapper sysUserMapper;
//    @Autowired private IRoleService iRoleService;
//    @Autowired private IPermissionService iPermissionService;

    private static final String TOKEN_REPLACE_FRONT_STR = "-";
    private static final String TOKEN_REPLACE_BACK_STR = "";

    @Schema(description = "查询当前Token的用户对象")
    public SysUser getCurrUser(){
        Object selectUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.equals("anonymousUser",selectUser.toString())){
            throw new CustomException("登录失效");
        }
        UserDetails user = (UserDetails) selectUser;
        return selectByUserName(user.getUsername());
    }

    private SysUser selectByUserName(String username) {
        QueryWrapper<SysUser> userQw = new QueryWrapper<>();
        userQw.eq("username",username);
        SysUser user = sysUserMapper.selectOne(userQw);
//        if(user == null) {
//            return null;
//        }
//        /**
//         * 填充角色
//         */
//        QueryWrapper<Role> roleQw = new QueryWrapper<>();
//        roleQw.inSql("id","SELECT role_id FROM a_user_role WHERE del_flag = 0 AND user_id = '" + user.getId() + "'");
//        List<Role> roleList = iRoleService.list(roleQw);
//        List<RoleVO> roles = new ArrayList<>();
//        for (Role role : roleList) {
//            roles.add(new RoleVO(role.getName(),role.getId(),role.getDescription()));
//        }
//        user.setRoles(roles);
//        /**
//         * 填充菜单
//         */
//        QueryWrapper<Permission> permissionQw = new QueryWrapper<>();
//        permissionQw.inSql("id","SELECT role_id FROM a_role_permission WHERE del_flag = 0 AND permission_id = '" + user.getId() + "'");
//        List<Permission> permissionList = iPermissionService.list(permissionQw);
//        List<PermissionVO> permissions = new ArrayList<>();
//        for (Permission permission : permissionList) {
//            if(!Objects.equals(1,permission.getType())) {
//                permissions.add(new PermissionVO(permission.getPath(),permission.getTitle()));
//            }
//        }
//        user.setPermissions(permissions);
        return user;
    }

    @Schema(description = "登录时获取新的用户Token")
    public String getToken(String username, Boolean saveLogin){

        JudgeParam.paramIsNotNull(username,"username不能为空");

        boolean saved = false;
        if(saveLogin == null || saveLogin){
            saved = true;
        }
        //查询用户的 角色、菜单
        SysUser selectUser = selectByUserName(username);

        // 菜单和角色的数组
        List<String> permissionTitleList = new ArrayList<>();
        if(loginProperties.getSaveRoleFlag()){
//            for(PermissionVO p : selectUser.getPermissions()){
//                if(JudgeParam.isNullOrUndefined(p.getTitle()) && JudgeParam.isNullOrUndefined(p.getPath())) {
//                    permissionTitleList.add(p.getTitle());
//                }
//            }
//            for(RoleVO r : selectUser.getRoles()){
//                permissionTitleList.add(r.getName());
//            }
        }
        String ansUserToken = UUID.randomUUID().toString().replace(TOKEN_REPLACE_FRONT_STR, TOKEN_REPLACE_BACK_STR);
        TokenUser tokenUser = new TokenUser(selectUser.getUsername(), permissionTitleList, saved);
        // 单点登录删除旧Token
        if(loginProperties.getSsoFlag()) {
            String oldToken = redisTemplate.opsForValue().get(LoginProperties.USER_TOKEN_PRE + selectUser.getUsername());
            if (StrUtil.isNotBlank(oldToken)) {
                redisTemplate.delete(LoginProperties.HTTP_TOKEN_PRE + oldToken);
            }
        }
        // 保存至Redis备查
        if(saved){
            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + selectUser.getUsername(), ansUserToken, loginProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + ansUserToken, JSON.toJSONString(tokenUser), loginProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
        }else{
            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + selectUser.getUsername(), ansUserToken, loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + ansUserToken, JSON.toJSONString(tokenUser), loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        return ansUserToken;
    }

    @Schema(description = "查询指定用户的权限列表")
    public List<GrantedAuthority> getCurrUserPerms(String userName){
        List<GrantedAuthority> ans = new ArrayList<>();
        SysUser selectUser = selectByUserName(userName);
        if(selectUser == null){
            return ans;
        }
        List<PermissionVO> perList = selectUser.getPermissions();
        if(perList.size() < 1) {
            return ans;
        }
        for(PermissionVO vo : perList){
            ans.add(new SimpleGrantedAuthority(vo.getTitle()));
        }
        return ans;
    }


}
