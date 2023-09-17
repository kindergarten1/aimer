package com.cilcil.unitl.jwt;


import com.alibaba.fastjson.JSONObject;
import com.cilcil.modules.login.entity.LoginProperties;
import com.cilcil.modules.login.entity.TokenUser;
import com.cilcil.unitl.JudgeParam;
import com.cilcil.unitl.response.ResponseVO;
import com.cilcil.unitl.security.SecurityUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */
@Schema(name = "自定义权限过滤")
@Slf4j
public class JwtTokenOncePerRequestFilter extends OncePerRequestFilter {

    @Autowired private final SecurityUtil securityUtil;

    @Autowired private final StringRedisTemplate redisTemplate;

    @Autowired private final LoginProperties loginProperties;

    private static final boolean RESPONSE_FAIL_FLAG = false;
    private static final int RESPONSE_NO_ROLE_CODE = 401;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader(LoginProperties.HTTP_HEADER);

        if(JudgeParam.isNullOrUndefined(tokenHeader)){

            //从header中获取token
            tokenHeader = request.getParameter(LoginProperties.HTTP_HEADER);
        }
        //头部token为空就直接返回
        if (JudgeParam.isNullOrUndefined(tokenHeader)) {

            filterChain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken token = getUsernamePasswordAuthenticationToken(tokenHeader, response);
            SecurityContextHolder.getContext().setAuthentication(token);
        }catch (Exception e){
            log.warn("自定义权限过滤失败" + e);
        }
        filterChain.doFilter(request, response);
    }

    @Schema(description = "判断登录是否失效")
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String header, HttpServletResponse response) {
        String userName = null;
        String tokenInRedis = redisTemplate.opsForValue().get(LoginProperties.HTTP_TOKEN_PRE + header);

        if(JudgeParam.isNullOrUndefined(tokenInRedis)){

            ResponseVO.error("登录状态失效，需要重登！");
            return null;
        }

        TokenUser tokenUser = JSONObject.parseObject(tokenInRedis,TokenUser.class);
        userName = tokenUser.getUsername();
        List<GrantedAuthority> permissionList = new ArrayList<>();
        if(loginProperties.getSaveRoleFlag()){
            for(String permission : tokenUser.getPermissions()){
                permissionList.add(new SimpleGrantedAuthority(permission));
            }
        } else{
            permissionList = securityUtil.getCurrUserPerms(userName);
        }
        if(!tokenUser.getSaveLogin()){
            redisTemplate.opsForValue().set(LoginProperties.USER_TOKEN_PRE + userName, header, loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(LoginProperties.HTTP_TOKEN_PRE + header, tokenInRedis, loginProperties.getUserTokenInvalidDays(), TimeUnit.MINUTES);
        }
        if(!JudgeParam.isNullOrUndefined(userName)) {
            User user = new User(userName, "", permissionList);
            return new UsernamePasswordAuthenticationToken(user, null, permissionList);
        }
        return null;
    }

    public JwtTokenOncePerRequestFilter(StringRedisTemplate redis, SecurityUtil securityUtil,LoginProperties loginProperties) {
        this.redisTemplate = redis;
        this.securityUtil = securityUtil;
        this.loginProperties = loginProperties;
    }
}
