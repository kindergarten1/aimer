package com.cilcil.login.controller;



import com.cilcil.login.model.dto.LoginDTO;
import com.cilcil.login.model.dto.UserRegisterDTO;
import com.cilcil.login.service.UserManagementService;
import com.cilcil.modules.login.entity.SysUser;
import com.cilcil.unitl.response.ResponseVO;
import com.cilcil.unitl.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @Author SlyAimer
 * @Date 2023/8/26 20:41
 * @Version 1.0
 */
@Tag(name = "Login", description = "用户登录")
@RestController
@RequestMapping("/user")
public class UserManagementController {


    @Resource UserManagementService userManagementService;

    @Operation(summary = "用户登录 -- SlyAimer", tags = "Login")
    @PostMapping("/login")
    public ResponseVO<?> userLogin(@Valid @RequestBody LoginDTO loginDTO) {

        return userManagementService.login(loginDTO);
    }

    @Operation(summary = "获取用户信息 -- SlyAimer", tags = "Login")
    @GetMapping("/getUserInfo")
    public ResponseVO<?> getUserInfo(){

        return userManagementService.getUserInfo();
    }

    @Operation(summary = "用户注册 -- SlyAimer", tags = "Login")
    @PostMapping("/register")
    public ResponseVO<?> userRegistration(@Valid @RequestBody UserRegisterDTO registerDTO) {

        return userManagementService.register(registerDTO);
    }

}
