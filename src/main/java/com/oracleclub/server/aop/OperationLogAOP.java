package com.oracleclub.server.aop;


import com.oracleclub.server.annotation.OperationLogMarker;
import com.oracleclub.server.config.WebConfig;
import com.oracleclub.server.entity.OperationLog;
import com.oracleclub.server.entity.param.LogParam;
import com.oracleclub.server.service.OperationLogService;
import com.oracleclub.server.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class OperationLogAOP {

    @Resource
    OperationLogService logService;

    @Pointcut("@annotation(com.oracleclub.server.annotation.OperationLogMarker)")
    private void operationLogPointcut() {}

    @AfterReturning("operationLogPointcut()")
    public void saveLog(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        System.out.println("接口访问成功，日志记录中...");
        if (request == null) {
            throw new RuntimeException("日志记录错误，请求未找到");
        }
        String userId = JwtUtil.getUserId(request.getHeader(WebConfig.TOKEN_NAME));
        LogParam logParam = new LogParam();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLogMarker marker = method.getAnnotation(OperationLogMarker.class);
        if (marker != null) {
            logParam.setType(marker.operaType().getValue());
            logParam.setContent(marker.operaContent());
            logParam.setUserId(Long.valueOf(userId));
        }
        OperationLog operationLog = logParam.convertTo();
        operationLog.setResult(1);
        logService.create(operationLog);
    }
}
