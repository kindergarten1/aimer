package com.cilcil.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cilcil.login.model.dto.LoginDTO;
import com.cilcil.login.model.dto.UserRegisterDTO;

import com.cilcil.login.model.vo.UserPrincipalVO;
import com.cilcil.login.service.SecurityUserDetails;
import com.cilcil.login.service.UserManagementService;
import com.cilcil.modules.login.entity.SysUser;
import com.cilcil.unitl.JudgeParam;
import com.cilcil.unitl.constants.CommonMsg;
import com.cilcil.unitl.response.ResponseVO;
import com.cilcil.modules.login.mapper.SysUserMapper;
import com.cilcil.unitl.security.SecurityUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.annotation.Resource;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:44
 * @Version 1.0
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {


    @Resource SysUserMapper sysUserMapper;
    @Resource SecurityUtil securityUtil;
    @Resource private PasswordEncoder bCryptPasswordEncoder;

    /**
     * @param loginDTO {@link LoginDTO}
     * @return
     */
    @Override
    public ResponseVO<?> login(LoginDTO loginDTO) {

        //判断登录账号/密码为不为空
        JudgeParam.paramIsNotNull(loginDTO.getUsername(), CommonMsg.LOGIN_ACCOUNT_COULD_NOT_BE_NULL);
        JudgeParam.paramIsNotNull(loginDTO.getPassword(), CommonMsg.LOGIN_PASSWORD_COULD_NOT_BE_NULL);

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginDTO.getUsername());

        SysUser user = sysUserMapper.selectOne(queryWrapper);

        //用户密码正确则获取token
        if(ObjectUtils.isNotEmpty(user)){
            // 验证密码准确性
            if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
                // 密码错误
                return ResponseVO.error(CommonMsg.PASSWORD_ERROR);
            }
            //获取token
            String accessToken = securityUtil.getToken(loginDTO.getUsername(), false);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new SecurityUserDetails(user), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //返回登录的信息
            return ResponseVO.ok(new UserPrincipalVO(accessToken,null,null,user));
        }

        return ResponseVO.error("查无此人");
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @Override
    public ResponseVO<?> getUserInfo() {

        return ResponseVO.ok(securityUtil.getCurrUser());
    }


    /**
     * @param registerDTO {@link UserRegisterDTO}
     * @return
     */
    @Override
    public ResponseVO<?> register(UserRegisterDTO registerDTO) {
        return ResponseVO.ok();
    }

}
