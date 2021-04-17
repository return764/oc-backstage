package com.oracleclub.server.controller;

import cn.hutool.core.util.ReUtil;
import com.auth0.jwt.interfaces.Claim;
import com.oracleclub.server.annotation.PassToken;
import com.oracleclub.server.entity.vo.R;
import com.oracleclub.server.exception.AuthenticationException;
import com.oracleclub.server.exception.TokenPastDateException;
import com.oracleclub.server.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.oracleclub.server.utils.JwtUtil.BEARER_RE;

/**
 * @author :RETURN
 * @date :2021/4/13 0:33
 */
@Slf4j
@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Resource(name = "tokenCache")
    private Cache<String, String> tokenCache;
    @Resource(name = "refreshTokenCache")
    private Cache<String,String> refreshTokenCache;


    @PassToken
    @PostMapping("refresh_token")
    public R refreshToken(@RequestHeader("Authorization")String token,
                          @RequestParam("refresh_token") String refreshToken){
        Assert.notNull(token,"令牌不能为空");
        Assert.notNull(refreshToken,"刷新令牌不能为空");
        log.debug("----------正在刷新token----------");

        String newToken = "";
        String tokenLast = ReUtil.get(BEARER_RE, token, 1);
        if (tokenLast == null){
            throw new AuthenticationException("token验证失败");
        }
        Map<String, Claim> verifyToken = JwtUtil.verify(tokenLast);
        Map<String, Claim> verifyRefreshToken = JwtUtil.verify(refreshToken);
        String userId = verifyToken.get("userId").asString();
        String refreshUserId = verifyRefreshToken.get("userId").asString();
        String cacheRefreshToken = refreshTokenCache.get(userId);
        if (cacheRefreshToken == null){
            throw new TokenPastDateException("refreshToken已过期,需重新登录").setRefreshToken(true);
        }
        if (userId.equals(refreshUserId)){
            //验证通过
            newToken = JwtUtil.sign("userId", userId);
            log.debug("刷新token成功!!!");
        }
        tokenCache.put(userId,newToken);
        Map<String ,String> map = new HashMap<>();
        map.put("newToken",newToken);
        return R.success("刷新token成功",map);
    }
}
