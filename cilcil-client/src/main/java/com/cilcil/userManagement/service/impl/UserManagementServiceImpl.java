package com.cilcil.userManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cilcil.userManagement.model.dto.LoginDTO;
import com.cilcil.userManagement.model.dto.UserRegisterDTO;
import com.cilcil.userManagement.service.UserManagementService;

import com.cilcil.unitl.response.ResponseVO;
import com.cilcil.modules.sysuser.entity.SysUser;
import com.cilcil.modules.sysuser.mapper.SysUserMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:44
 * @Version 1.0
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {


    @Resource SysUserMapper sysUserMapper;

    /**
     *
     * @param loginDTO {@link LoginDTO}
     * @return
     */
    @Override
    public ResponseVO<?> login(LoginDTO loginDTO) {

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account",loginDTO.getLoginAccount());
        queryWrapper.eq("login_password",loginDTO.getLoginPassword());

        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(sysUser)){
            return ResponseVO.ok(sysUser);
        }
        return ResponseVO.error("查无此人");
    }

    /**
     *
     * @param registerDTO {@link UserRegisterDTO}
     * @return
     */
    @Override
    public ResponseVO<?> register(UserRegisterDTO registerDTO) {
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
