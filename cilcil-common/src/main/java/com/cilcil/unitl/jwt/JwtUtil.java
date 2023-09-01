package com.cilcil.unitl.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cilcil.unitl.exception.CustomException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author SlyAimer
 * @Date 2023/9/1 18:02
 * @Version 1.0
 */
@EnableConfigurationProperties({JwtConfig.class})
@Configuration
@Component
public class JwtUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    @Resource
    private JwtConfig jwtConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public JwtUtil() {
    }

    public String createJWT(Authentication authentication, Boolean rememberMe) {
        return this.createJWT(authentication, rememberMe, (String)null);
    }

    public String createJWT(Authentication authentication, Boolean rememberMe, String keyPrefix) {
        Object principal = authentication.getPrincipal();
        JSONObject userPrincipal = new JSONObject();
        if (principal != null) {
            String jsonString = JSONObject.toJSONString(principal);
            userPrincipal = JSONObject.parseObject(jsonString);
        }

        JSONArray roles = userPrincipal.getJSONArray("roles");
        JSONArray array = userPrincipal.getJSONArray("authorities");
        Collection<? extends GrantedAuthority> authorities = new ArrayList();
        if (array != null && !array.isEmpty()) {
            authorities = array.toJavaList(GrantedAuthority.class);
        }

        return this.createJWT(rememberMe, keyPrefix, userPrincipal.getString("id"), userPrincipal.getString("loginName"), roles, (Collection)authorities);
    }

    public String createJWT(Boolean rememberMe, String keyPrefix, String id, String subject, JSONArray roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, this.jwtConfig.getKey()).claim("roles", roles).claim("authorities", authorities);
        Long ttl = this.jwtConfig.getTtl();
        if (Boolean.TRUE.equals(rememberMe)) {
            ttl = this.jwtConfig.getRemember();
        }

        if (ttl > 0L) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        if (StrUtil.isBlank(keyPrefix)) {
            keyPrefix = "security:jwt:";
        }

        String jwt = builder.compact();
        this.stringRedisTemplate.opsForValue().set(keyPrefix + id, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    public Claims parseJWT(String jwt, String keyPrefix) {
        try {
            Claims claims = (Claims)Jwts.parser().setSigningKey(this.jwtConfig.getKey()).parseClaimsJws(jwt).getBody();
            String id = claims.getId();
            if (StrUtil.isBlank(keyPrefix)) {
                keyPrefix = "security:jwt:";
            }

            String redisKey = keyPrefix + id;
            Long expire = this.stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (!ObjectUtil.isNull(expire) && expire > 0L) {
                String redisToken = (String)this.stringRedisTemplate.opsForValue().get(redisKey);
                if (!StrUtil.equals(jwt, redisToken)) {
                    throw new SecurityException("当前用户已在别处登录，请尝试更改密码或重新登录！");
                } else {
                    this.stringRedisTemplate.expire(redisKey, expire + this.jwtConfig.getExpire(), TimeUnit.MILLISECONDS);
                    return claims;
                }
            } else {
                throw new CustomException("token 已过期，请重新登录！");
            }
        } catch (ExpiredJwtException var8) {
            log.error("Token 已过期");
            throw new SecurityException("token 已过期，请重新登录！");
        } catch (UnsupportedJwtException var9) {
            log.error("不支持的 Token");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        } catch (MalformedJwtException var10) {
            log.error("Token 无效");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        } catch (SignatureException var11) {
            log.error("无效的 Token 签名");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        } catch (IllegalArgumentException var12) {
            log.error("Token 参数不存在");
            throw new SecurityException("token 解析失败，请尝试重新登录！");
        }
    }

    public void invalidateJWT(HttpServletRequest request) {
        this.invalidateJWT(request, (String)null);
    }

    public void invalidateJWT(HttpServletRequest request, String keyPrefix) {
        String jwt = this.getJwtFromRequest(request);
        String username = this.getUserNameFromJWT(jwt, keyPrefix);
        if (StrUtil.isBlank(keyPrefix)) {
            keyPrefix = "security:jwt:";
        }

        this.stringRedisTemplate.delete(keyPrefix + username);
    }

    public String getUserNameFromJWT(String jwt) {
        Claims claims = this.parseJWT(jwt, (String)null);
        return claims.getSubject();
    }

    public String getUserNameFromJWT(String jwt, String keyPrefix) {
        Claims claims = this.parseJWT(jwt, keyPrefix);
        return claims.getSubject();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        return request.getHeader("X-Auth-Token");
    }
}
