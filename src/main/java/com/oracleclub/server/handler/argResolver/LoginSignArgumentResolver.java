package com.oracleclub.server.handler.argResolver;

import com.oracleclub.server.entity.User;
import com.oracleclub.server.entity.support.LoginSign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Slf4j
public class LoginSignArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return LoginSign.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        User user = (User) nativeWebRequest.getAttribute("user", NativeWebRequest.SCOPE_REQUEST);
        log.info(String.valueOf(user));
        if (Objects.isNull(user)) {
            return null;
        }
        return new LoginSign(user);
    }
}
