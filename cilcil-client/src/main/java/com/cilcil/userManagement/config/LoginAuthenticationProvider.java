package com.cilcil.userManagement.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author SlyAimer
 * @Date 2023/8/28 18:46
 * @Version 1.0
 */
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

    public LoginAuthenticationProvider(UserDetailsService userDetailsService) {

        super();
        // 这个地方一定要对userDetailsService赋值，不然userDetailsService是null (这个坑有点深)
        setUserDetailsService(userDetailsService);
    }
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {


    }
}
