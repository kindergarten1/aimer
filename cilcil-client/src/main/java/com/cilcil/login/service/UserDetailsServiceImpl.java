package com.cilcil.login.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cilcil.login.service.impl.UserManagementServiceImpl;
import com.cilcil.modules.login.entity.SysUser;
import com.cilcil.modules.login.mapper.SysUserMapper;
import com.cilcil.unitl.JudgeParam;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author SlyAimer
 * @Date 2023/9/5 9:35
 * @Version 1.0
 */

@Component
@Schema(description = "登录判断类")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private StringRedisTemplate redisTemplate;
    @Resource  private SysUserMapper sysUserMapper;

    private static final String LOGIN_FAIL_DISABLED_PRE = "userLoginDisableFlag:";

    /**
     * 重写 UserDetailsService 以此来实现 登录时请求token 并存到redis里面
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Schema(description = "根据账号查询用户所有信息")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //登录失败标识
        String loginFailFlag = LOGIN_FAIL_DISABLED_PRE + username;
        //存到redis里面用来记录失败次数
        String value = redisTemplate.opsForValue().get(loginFailFlag);
        //限制登录重试次数
        Long timeRest = redisTemplate.getExpire(loginFailFlag, TimeUnit.MINUTES);

        //判断如果 value 有值则提示 重试
        if(!JudgeParam.isNullOrUndefined(value)){

            throw new UsernameNotFoundException("试错超限，请您在" + timeRest + "分钟后再登");
        }
        QueryWrapper<SysUser> userQw = new QueryWrapper<>();
        userQw.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(userQw);
        if(sysUser == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }
        return new SecurityUserDetails(sysUser);
    }
}
