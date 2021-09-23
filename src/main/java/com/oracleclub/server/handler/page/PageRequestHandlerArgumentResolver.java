package com.oracleclub.server.handler.page;

import com.oracleclub.server.entity.param.PageRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

/**
 * @author :RETURN
 * @date :2021/9/22 9:03
 */
@Component
public class PageRequestHandlerArgumentResolver implements PageRequestArgumentResolver {
    public static final int MAX_PAGE_NUM = 2147483647;
    public static final int MAX_PAGE_SIZE = 2000;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return PageRequest.class.equals(methodParameter.getParameterType());
    }

    @Override
    public PageRequest resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        String page = nativeWebRequest.getParameter("page");
        String size = nativeWebRequest.getParameter("size");
        //todo 保留对Sort参数的处理
        return this.getPage(methodParameter,page,size);
    }

    private PageRequest getPage(MethodParameter methodParameter, String pageStr, String sizeStr) {
        Optional<PageRequest> pageRequest = getDefaultAnnotationParam(methodParameter).toOptional();
        Optional<Integer> page = parseWebPageRequestParams(pageStr, MAX_PAGE_NUM);
        Optional<Integer> size = parseWebPageRequestParams(sizeStr, MAX_PAGE_SIZE);
        int p = page.orElseGet(() -> pageRequest.map(PageRequest::getPage).orElseThrow(IllegalStateException::new));
        int s = size.orElseGet(() -> pageRequest.map(PageRequest::getSize).orElseThrow(IllegalStateException::new));

        return PageRequest.of(p,s);
    }

    private Optional<Integer> parseWebPageRequestParams(String str, int upper){
        if (!StringUtils.hasText(str)){
            return Optional.empty();
        }
        try {
            Integer parsed = Integer.parseInt(str);
            return Optional.of(parsed < 0 ? 0 : (parsed > upper ? upper : parsed));
        }catch (NumberFormatException e){
            return Optional.of(0);
        }
    }

    private PageRequest getDefaultAnnotationParam(MethodParameter methodParameter){
        PageDefault defaults = methodParameter.getParameterAnnotation(PageDefault.class);
        if (defaults == null){
            return PageRequest.of(0,10);
        }
        Integer size = defaults.size();
        Integer page = defaults.page();

        return PageRequest.of(page, size);
    }

}
