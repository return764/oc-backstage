package com.oracleclub.server.interceptor;

import com.oracleclub.server.annotation.PassToken;
import com.oracleclub.server.config.WebConfig;
import com.oracleclub.server.exception.AuthenticationException;
import com.oracleclub.server.exception.TokenPastDateException;
import com.oracleclub.server.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器，拦截所有请求
 * 放行未被controller捕获的请求和含有PassToken注解的请求
 * @author RETURN
 * @date 2020/8/13 22:26
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Resource(name = "tokenCache")
    private Cache<String, String> tokenCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果该url没有进入controller的方法
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if(handlerMethod.getBeanType() == BasicErrorController.class){
            throw new RuntimeException("未知错误");
        }
        //如果有不需要验证token的注解
        if (handlerMethod.hasMethodAnnotation(PassToken.class)){
            return true;
        }

        String sourceToken = request.getHeader(WebConfig.TOKEN_NAME);
        String realToken = JwtUtil.getRealToken(sourceToken);
        if (null == realToken){
            throw new AuthenticationException("token格式错误，请重新登录");
        }

        String userId = JwtUtil.getUserId(sourceToken);
        String cacheToken = tokenCache.get(userId);
        request.setAttribute("userId",userId);

        log.debug("cacheToken:{}",cacheToken);
        log.debug("token:{}",realToken);

        //缓存中存在，并且与传递过来的token不相同
        if (null != cacheToken &&!realToken.equals(cacheToken)){
            throw new AuthenticationException("token不匹配");
        }

        //缓存中不存在，但是传递过来的token验证通过
        if (null == cacheToken){
            throw new TokenPastDateException("token已过期，正在重新签发").setRefreshToken(false);
        }

        log.debug("token验证通过");
        return true;
    }
}
