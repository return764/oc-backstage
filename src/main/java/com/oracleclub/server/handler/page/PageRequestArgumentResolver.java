package com.oracleclub.server.handler.page;

import com.oracleclub.server.entity.param.PageRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author :RETURN
 * @date :2021/9/22 9:08
 */
public interface PageRequestArgumentResolver extends HandlerMethodArgumentResolver {

    @Override
    PageRequest resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception;
}
