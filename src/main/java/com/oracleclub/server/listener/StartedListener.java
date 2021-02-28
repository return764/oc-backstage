package com.oracleclub.server.listener;

import com.oracleclub.server.config.properties.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author :RETURN
 * @date :2021/2/26 15:50
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Resource
    AppProperties appProperties;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        this.initDirectory();
    }

    /**
     * 工作目录初始化
     */
    private void initDirectory(){
        Path workPath = Paths.get(appProperties.getWorkPath());

        try {
            if (Files.notExists(workPath)){
                Files.createDirectories(workPath);
                log.info("创建工作目录: [{}]", workPath);
            }
        }catch (IOException e){
            throw new RuntimeException("创建目录失败",e);
        }
    }
}
