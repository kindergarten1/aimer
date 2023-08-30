package com.cilcilclient.userManagement.service.impl;

import com.cilcilclient.userManagement.model.dto.LoginDTO;
import com.cilcilclient.userManagement.model.dto.UserRegisterDTO;
import com.cilcilclient.userManagement.service.UserManagementInterface;

import com.cilcilcommon.unitl.response.ResponseVO;
import org.springframework.stereotype.Service;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 21:44
 * @Version 1.0
 */
@Service
public class UserManagementInterfaceImpl implements UserManagementInterface {



    /**
     *
     * @param loginDTO {@link LoginDTO}
     * @return
     */
    @Override
    public ResponseVO<?> login(LoginDTO loginDTO) {

        return null;
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
