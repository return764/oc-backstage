package com.oracleclub.server.config;

import com.oracleclub.server.config.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author :RETURN
 * @date :2021/2/26 13:55
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class HelloConfig {
}
