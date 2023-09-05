package com.cilcil.login.config;

import com.cilcil.login.service.UserDetailsServiceImpl;
import com.cilcil.login.service.UserManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Value("${ignores}") String ignores;
    @Resource  private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .authorizeHttpRequests((authorize) -> authorize
                        //.requestMatchers(ignores).permitAll()//无需授权即可访问的url，多个地址可以这样写。
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()//也可以写一个地址。
                        .anyRequest().authenticated())//其它页面需要授权才可以访问。
                .formLogin(form -> form
                        //.loginPage("/login-form")//自定义的表单，可以不用框架给的默认表单。
                        .loginProcessingUrl("/login.do")//这个地址就是摆设，给外人看的，只要跟form表单的action一致就好，真正起作用的还是UserDetailsService。
                        .permitAll()
                        //.successForwardUrl("/"))
                        .defaultSuccessUrl("/"))//建议用这个，successForwardUrl只能返回指定页，而这个可以返回来源页，没有来源页才会返回指定页，体验较好。
                .logout()//注销登录
                .logoutUrl("/logout")//这个地址也只是给人看的，只要跟前端注销地址一致就可。
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))//主要是指定注销时用什么请求方法，GET和POST都可以吧。
                .logoutSuccessUrl("/login-form")//注销后要跳转的页面，那个页面一定是不需要授权就可以访问的，否则会出现意外结果。一般是首页，这里随便写的一个页面。
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unAuth.html");//自定义没权限访问的提示页面。
        return http.build();
    }


    //密码加密用的，不然没法做密码比对。
    @Bean
    public PasswordEncoder getPwdEncoder() {
        return new BCryptPasswordEncoder();
    }
}