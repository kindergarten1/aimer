package com.cilcil.login.config;


import com.cilcil.login.service.UserDetailsServiceImpl;
import com.cilcil.modules.login.entity.LoginProperties;
import com.cilcil.unitl.jwt.AuthenticationFailHandler;
import com.cilcil.unitl.jwt.AuthenticationSuccessHandler;
import com.cilcil.unitl.jwt.JwtTokenOncePerRequestFilter;
import com.cilcil.unitl.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired  private LoginProperties loginProperties;
    @Autowired private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired private AuthenticationFailHandler authenticationFailHandler;
    @Autowired private StringRedisTemplate redisTemplate;
    @Autowired private SecurityUtil securityUtil;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
//                .userDetailsService(userDetailsService)
                .authorizeHttpRequests((authorize) -> authorize
                        //放行资源
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/user/login")).permitAll()//放行登录接口获取token
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/user/register")).permitAll()//放行注册接口
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()//swagger首页页面
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/doc.html")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/**")).permitAll()//swagger 资源获取请求
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-resources/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/configuration/security")).permitAll()
                        .anyRequest().authenticated())//其它页面需要授权才可以访问。
                .formLogin(form -> form//登录请求监听
                        .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailHandler)
                        )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userDetailsService.loadUserByUsername(username);
    }

    //密码加密用的，不然没法做密码比对。
    @Bean
    public PasswordEncoder getPwdEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(getPwdEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtTokenOncePerRequestFilter authenticationJwtTokenFilter() throws Exception {
        return new JwtTokenOncePerRequestFilter(redisTemplate, securityUtil, loginProperties);
    }
}