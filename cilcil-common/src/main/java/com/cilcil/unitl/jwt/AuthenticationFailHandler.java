package com.cilcil.unitl.jwt;


import com.cilcil.modules.login.entity.LoginProperties;
import com.cilcil.unitl.JudgeParam;
import com.cilcil.unitl.response.ResponseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */
@Schema( description = "登录失败回调")
@Slf4j
@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired private LoginProperties tokenProperties;
    @Autowired private StringRedisTemplate stringRedisTemplate;
    private static final String LOGIN_FAIL_TIMES_PRE = "LOGIN_FAIL_TIMES_PRE:";
    private static final String REQUEST_PARAMETER_USERNAME = "username:";
    private static final boolean RESPONSE_FAIL_FLAG = false;
    private static final int RESPONSE_FAIL_CODE = 500;

    @Schema( description = "查询登录失败的次数")
    public boolean recordLoginTime(String username) {
        String loginFailTimeStr = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_TIMES_PRE + username);
        int loginFailTime = 0;
        // 已错误次数
        if(!JudgeParam.isNullOrUndefined(loginFailTimeStr)){
            loginFailTime = Integer.parseInt(loginFailTimeStr) + 1;
        }
        stringRedisTemplate.opsForValue().set(LOGIN_FAIL_TIMES_PRE + username, loginFailTime + "", tokenProperties.getLoginFailMaxThenLockTimes(), TimeUnit.MINUTES);
        if(loginFailTime >= tokenProperties.getMaxLoginFailTimes()){
            stringRedisTemplate.opsForValue().set("userLoginDisableFlag:"+username, "fail", tokenProperties.getLoginFailMaxThenLockTimes(), TimeUnit.MINUTES);
            return false;
        }
        return true;
    }

    @Override
    @Schema( description = "登录失败回调")
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {

            recordLoginTime(request.getParameter(REQUEST_PARAMETER_USERNAME));
            String failTimesStr = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_TIMES_PRE + request.getParameter(REQUEST_PARAMETER_USERNAME));
            //已错误的次数
            int userFailTimes = 0;

            if(!JudgeParam.isNullOrUndefined(failTimesStr)){

                userFailTimes = Integer.parseInt(failTimesStr);
            }
            int restLoginTime = tokenProperties.getMaxLoginFailTimes() - userFailTimes;

            if(restLoginTime < 5 && restLoginTime > 0){

                ResponseVO.error(RESPONSE_FAIL_CODE,"账号密码不正确，还能尝试登录" + restLoginTime + "次");
            } else if(restLoginTime < 1) {

                ResponseVO.error(RESPONSE_FAIL_CODE,"重试超限，请您" + tokenProperties.getLoginFailMaxThenLockTimes() + "分后再登录");
            } else {

                ResponseVO.error(RESPONSE_FAIL_CODE,"账号密码不正确");
            }
        }else if (exception instanceof DisabledException) {

            ResponseVO.error(RESPONSE_FAIL_CODE,"账户处于禁用状态，无法登录");
        } else {

            ResponseVO.error(RESPONSE_FAIL_CODE,"系统当前不能登录，请稍后再试");
        }
    }
}
