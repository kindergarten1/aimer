package com.cilcil.userManagement.service;


//import org.springframework.security.core.userdetails.UserDetailsService;

import com.cilcil.userManagement.model.dto.LoginDTO;
import com.cilcil.userManagement.model.dto.UserRegisterDTO;
import com.cilcil.unitl.response.ResponseVO;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:39
 * @Version 1.0
 */
public interface UserManagementService
        // extends UserDetailsService 后面需要重写这个里面的 loadUserByUsername 方法
{

    /**
     * 用户登录
     * @param loginDTO {@link LoginDTO}
     * @return {@link ResponseVO}
     */
    ResponseVO<?> login(LoginDTO loginDTO);

    /**
     * 用户注册
     * @param registerDTO {@link UserRegisterDTO}
     * @return {@link ResponseVO}
     */
    ResponseVO<?> register(UserRegisterDTO registerDTO);

}
