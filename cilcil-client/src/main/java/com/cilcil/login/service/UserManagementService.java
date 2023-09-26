package com.cilcil.login.service;


import com.cilcil.login.model.dto.LoginDTO;
import com.cilcil.login.model.dto.UserRegisterDTO;
import com.cilcil.unitl.response.ResponseVO;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:39
 * @Version 1.0
 */
public interface UserManagementService {

    /**
     * 用户登录
     * @param loginDTO {@link LoginDTO}
     * @return {@link ResponseVO}
     */
    ResponseVO<?> login(LoginDTO loginDTO);

    /**
     * 获取当前登录用户信息
     * @return
     */
    ResponseVO<?> getUserInfo();

    /**
     * 用户注册
     * @param registerDTO {@link UserRegisterDTO}
     * @return {@link ResponseVO}
     */
    ResponseVO<?> register(UserRegisterDTO registerDTO);

}
