package com.oracleclub.server;

import com.oracleclub.server.dao.base.BaseDaoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.oracleclub.server.dao", repositoryBaseClass = BaseDaoImpl.class)
@EnableAsync
@EnableCaching
public class ServerApplication{

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerApplication.class);
        System.setProperty("spring.config.additional-location", "file:${user.home}/.oc/");
        application.run(args);
    }

}
