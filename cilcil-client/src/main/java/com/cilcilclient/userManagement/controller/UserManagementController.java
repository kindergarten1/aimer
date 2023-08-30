package com.cilcilclient.userManagement.controller;



import com.cilcilclient.userManagement.model.dto.LoginDTO;
import com.cilcilclient.userManagement.model.dto.UserRegisterDTO;
import com.cilcilclient.userManagement.service.UserManagementInterface;
import com.cilcilcommon.unitl.response.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @Resource
    UserManagementInterface userManagementInterface;

    @Operation(summary = "用户登录 -- SlyAimer", tags = "Login")
    @PostMapping("/login")
    public ResponseVO<?> userLogin(@Valid @RequestBody LoginDTO loginDTO) {

        return userManagementInterface.login(loginDTO);
    }

    @Operation(summary = "用户注册 -- SlyAimer", tags = "Login")
    @PostMapping("/register")
    public ResponseVO<?> userRegistration(@Valid @RequestBody UserRegisterDTO registerDTO) {

        return userManagementInterface.register(registerDTO);
    }

}
