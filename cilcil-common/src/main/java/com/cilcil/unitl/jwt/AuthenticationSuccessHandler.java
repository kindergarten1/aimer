package com.cilcil.unitl.jwt;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.cilcil.modules.login.entity.LoginProperties;
import com.cilcil.modules.login.entity.TokenUser;
import com.cilcil.unitl.JudgeParam;
import com.cilcil.unitl.response.ResponseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */
@Schema(description = "登录成功回调")
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired private LoginProperties tokenProperties;
    @Autowired private StringRedisTemplate redisTemplate;
    private static final String TOKEN_REPLACE_STR_FRONT = "-";
    private static final String TOKEN_REPLACE_STR_BACK = "";

    @Override
    @Schema(description = "登录成功回调")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication ac) throws IOException, ServletException {

        String saveLogin = request.getParameter(LoginProperties.SAVE_LOGIN_PRE);
        boolean  saveLoginFlag = false;

        if(!JudgeParam.isNullOrUndefined(saveLogin) && Objects.equals(saveLogin,"true")){

            saveLoginFlag = true;
        }
        List<String> permissionsList = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)ac.getPrincipal()).getAuthorities();
        for(GrantedAuthority g : authorities){

            permissionsList.add(g.getAuthority());
        }
        String token = UUID.randomUUID().toString().replace(TOKEN_REPLACE_STR_FRONT, TOKEN_REPLACE_STR_BACK);
        String username = ((UserDetails)ac.getPrincipal()).getUsername();
        TokenUser user = new TokenUser(username, permissionsList, saveLoginFlag);
        // 判断是否存储菜单权限
        if(!tokenProperties.getSaveRoleFlag()){

            user.setPermissions(null);
        }
        // 单点登录判断
        if(tokenProperties.getSsoFlag()){

            String oldToken = redisTemplate.opsForValue().get(LoginProperties.USER_TOKEN_PRE + username);
            if(StrUtil.isNotBlank(oldToken)){

                redisTemplate.delete(LoginProperties.HTTP_TOKEN_PRE + oldToken);
            }
        }
        if(saveLoginFlag){

            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + username, token, tokenProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + token, JSON.toJSONString(user), tokenProperties.getUserSaveLoginTokenDays(), TimeUnit.DAYS);
        }else{

            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + username, token, tokenProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + token, JSON.toJSONString(user), tokenProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        ResponseVO.ok(token);
    }
}
