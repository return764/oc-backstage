package com.oracleclub.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
@MapperScan("com.oracleclub.server.dao")
public class ServerApplication{

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
        System.setProperty("spring.config.additional-location", "file:${user.home}/.oc/");
        application.run(args);
    }

}
