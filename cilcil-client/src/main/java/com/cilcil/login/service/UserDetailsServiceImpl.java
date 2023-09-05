package com.cilcil.login.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author SlyAimer
 * @Date 2023/9/5 9:35
 * @Version 1.0
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //通过构造方法，将我们获取数据库的数据的接口服务装配到类中，供后面获取数据。
    //也可以用注解@Autowired来自动装配，只是总报提示建议用构造方法，作为有强迫症的我就用了构造方法。
    private final UserManagementService userService;
    public UserDetailsServiceImpl(UserManagementService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询数据库判断用户名是否存在，如果不存在抛出UsernameNotFoundException。
        //这里引用的security.model.User就是我们自己定义的实体类，与我们的数据库对应。
        UserDetails userDetails  = userService.loadUserByUsername(username);
        //判断帐号是否存在，如果查不到，SpringSecurity不会让你认证通过，就是利用UsernameNotFoundException来实现的。
        //我的数据库里都没有你的帐号，怎么会让你通过呢？
        if(ObjectUtils.isEmpty(userDetails)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //这样就从数据库查到我们的用户信息啦，将这些信息注入我们的实体类中。
//        User user = users.get(0);

        //System.out.println(username);

        //从实体类中获取用户的密码。
//        String password = userDetails.getPassword();
        //System.out.println(password);

        //收集角色和权限，一个用户可以对应多个角色，一个角色可以对应多个权限（比如访问某个菜单或方法的权限）
        //你可以定义一个角色表，一个权限表。同时要建一个用户到角色的关联表和角色到权限的关联表。这些比较简单，这里不再赘述。
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        //获取用户角色。
//        List<Role> roles = userService.loadRoleByUid(user.getId());
//        for(Role role:roles){
//            //角色前一定要加上"ROLE_"前缀，否则SpringSecurity会视为无效，它是通过这个前缀识别角色的。
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_".concat(role.getName()));
//            grantedAuthorityList.add(simpleGrantedAuthority);
            //System.out.println(role.getName());
//        }
        //获取用户各模块或菜单的权限。
//        List<Authority> authorities = userService.loadAuthorityByUid(user.getId());
//        for(Authority authority:authorities){
//            //权限不需要什么前缀，比较省心。
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.getName());
//            grantedAuthorityList.add(simpleGrantedAuthority);
            //System.out.println(authority.getName());
//        }

        //以下是做授未用数据库时测试用的，我是先测试通过了下面的代码，又开始连接数据库的，你可以不用理会，但也有参考价值。
        /*if(!username.eq("admin")){
            throw new UsernameNotFoundException("用户名不存在");
        }*/
        //密码一定要用BCryptPasswordEncoder加密过的，否则认证不通过，下面的乱码就是123456加密而来的，吼吼。
        String password = "$2a$10$UUbMgKe4ozOFfcfJcC6KoOQHLixmMNF.Evx.E5/AkidUExBGXuq6m";
        //grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//一定要加"ROLE_"前缀，否则无效。
        //grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));//每个用户最低要有一个USER角色。
        //grantedAuthorityList.add(new SimpleGrantedAuthority("CART"));//这是添加权限，不需要什么前缀。

        //来啦来啦，关键的关键来啦，要把我们收集到的用户信息一股脑的捅进SpringSecurity容器里。
        //SpringSecurity会自动验证你的用户密码是否正确，如果密码不正确就会返回登录页，正确就把用户信息注入内存以备用。
        return new User(username,password, grantedAuthorityList);
    }
}
