package com.oracleclub.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class ServerApplication{

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
        System.setProperty("spring.config.additional-location", "file:${user.home}/.oc/");
        application.run(args);
    }

}
