package com.oracleclub.server.listener;

import com.oracleclub.server.entity.OperationLog;
import com.oracleclub.server.event.LogEvent;
import com.oracleclub.server.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author :RETURN
 * @date :2021/9/23 11:28
 */
@Slf4j
@Component
public class LogListener {

    @Resource
    OperationLogService logService;

    @Async
    @EventListener
    public void onApplicationEvent(LogEvent event) {
        // Convert to log
        OperationLog logToCreate = event.getLog().convertTo();
        logToCreate.setName("管理员");
        logToCreate.setResult(1);
        log.debug("保存日志中:{}",logToCreate);
        // Create log
        logService.create(logToCreate);
    }
}
