package com.oracleclub.server.utils;

import cn.hutool.core.util.ReUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oracleclub.server.entity.support.LoginToken;
import com.oracleclub.server.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * token工具类，包括token的签发和认证
 * @author RETURN
 * @date 2020/8/13 22:27
 */
@Slf4j
public class JwtUtil {

    private static final String SECRET = "lhd22;?f3dkvo4hf9sagz;sbfd";
    public static final String BEARER_RE = "Bearer\\s((.*)\\.(.*)\\.(.*))";
    /**
     * 默认过期2小时
     */
    private static final long EXPIRE_TIME = 30 * 1000;

    public static String sign(String claimName, String payLoad){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Map<String,Object> header = new HashMap<>(2);
        header.put("alg","HS256");
        header.put("typ","JWT");
        return JWT.create()
                .withHeader(header)
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim(claimName,payLoad)
                .sign(algorithm);
    }

    public static String signRefreshToken(String claimName, String payLoad){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Map<String,Object> header = new HashMap<>(2);
        header.put("alg","HS256");
        header.put("typ","JWT");
        JWTCreator.Builder builder = JWT.create()
                .withHeader(header)
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withClaim(claimName,payLoad)
                .withClaim("refreshToken",System.currentTimeMillis());
        return builder.sign(algorithm);
    }


    public static Map<String, Claim> verify(String token){
        DecodedJWT jwt = null;
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        try {
            log.debug("尝试验证token：{}",token);
            jwt = JWT.require(algorithm).build().verify(token);
        }catch (JWTVerificationException e){
            throw new AuthenticationException("token验证失败:"+e.getMessage());
        }

        return jwt.getClaims();
    }

    public static LoginToken signUser(Long userId){
        String token = sign("userId", String.valueOf(userId));
        String refreshToken = signRefreshToken("userId", String.valueOf(userId));
        return new LoginToken(token,refreshToken);
    }

    public static Long getUserId(String token) {
        String tokenLast = ReUtil.get(BEARER_RE, token, 1);
        Map<String, Claim> verifyToken = JwtUtil.verify(tokenLast);
        return verifyToken.get("userId").asLong();
    }
}
