package com.cilcil.unitl.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author SlyAimer
 * @Date 2023/9/1 17:24
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseVO {

    /**
     * token 字段
     */
    private String token;
    /**
     * token类型
     */
    private String tokenType;

    public JwtResponseVO(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }
}
