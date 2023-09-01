package com.cilcil.unitl.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author SlyAimer
 * @Date 2023/9/1 18:03
 * @Version 1.0
 */
@ConfigurationProperties( prefix = "jwt.config")
public class JwtConfig {
    private String key;
    private Long ttl;
    private Long remember;
    private Long expire;

    public JwtConfig() {
    }

    public String getKey() {
        return this.key;
    }

    public Long getTtl() {
        return this.ttl;
    }

    public Long getRemember() {
        return this.remember;
    }

    public Long getExpire() {
        return this.expire;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setTtl(final Long ttl) {
        this.ttl = ttl;
    }

    public void setRemember(final Long remember) {
        this.remember = remember;
    }

    public void setExpire(final Long expire) {
        this.expire = expire;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof JwtConfig)) {
            return false;
        } else {
            JwtConfig other = (JwtConfig)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$key = this.getKey();
                    Object other$key = other.getKey();
                    if (this$key == null) {
                        if (other$key == null) {
                            break label59;
                        }
                    } else if (this$key.equals(other$key)) {
                        break label59;
                    }

                    return false;
                }

                Object this$ttl = this.getTtl();
                Object other$ttl = other.getTtl();
                if (this$ttl == null) {
                    if (other$ttl != null) {
                        return false;
                    }
                } else if (!this$ttl.equals(other$ttl)) {
                    return false;
                }

                Object this$remember = this.getRemember();
                Object other$remember = other.getRemember();
                if (this$remember == null) {
                    if (other$remember != null) {
                        return false;
                    }
                } else if (!this$remember.equals(other$remember)) {
                    return false;
                }

                Object this$expire = this.getExpire();
                Object other$expire = other.getExpire();
                if (this$expire == null) {
                    if (other$expire != null) {
                        return false;
                    }
                } else if (!this$expire.equals(other$expire)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JwtConfig;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $key = this.getKey();
        result = result * 59 + ($key == null ? 43 : $key.hashCode());
        Object $ttl = this.getTtl();
        result = result * 59 + ($ttl == null ? 43 : $ttl.hashCode());
        Object $remember = this.getRemember();
        result = result * 59 + ($remember == null ? 43 : $remember.hashCode());
        Object $expire = this.getExpire();
        result = result * 59 + ($expire == null ? 43 : $expire.hashCode());
        return result;
    }

    public String toString() {
        return "JwtConfig(key=" + this.getKey() + ", ttl=" + this.getTtl() + ", remember=" + this.getRemember() + ", expire=" + this.getExpire() + ")";
    }
}
