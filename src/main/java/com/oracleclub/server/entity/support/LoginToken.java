package com.oracleclub.server.entity.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :RETURN
 * @date :2021/4/12 17:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginToken {
    /**
     * token
     */
    private String token;
    /**
     * refreshToken
     */
    private String refreshToken;
}
