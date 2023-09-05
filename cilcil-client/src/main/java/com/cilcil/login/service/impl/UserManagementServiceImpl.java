package com.cilcil.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cilcil.login.model.dto.LoginDTO;
import com.cilcil.login.model.dto.UserRegisterDTO;
import com.cilcil.login.model.vo.UserPrincipalVO;
import com.cilcil.login.service.UserManagementService;

import com.cilcil.modules.sysuser.entity.SysUser;
import com.cilcil.unitl.response.ResponseVO;
import com.cilcil.modules.sysuser.mapper.SysUserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:44
 * @Version 1.0
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {


    @Resource
    SysUserMapper sysUserMapper;

    /**
     * @param loginDTO {@link LoginDTO}
     * @return
     */
    @Override
    public ResponseVO<?> login(LoginDTO loginDTO) {

        return null;
    }

    /**
     * @param registerDTO {@link UserRegisterDTO}
     * @return
     */
    @Override
    public ResponseVO<?> register(UserRegisterDTO registerDTO) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account",username);
        SysUser users = sysUserMapper.selectOne(queryWrapper);
        return UserPrincipalVO.createMemberInfo(users) ;
    }
}
