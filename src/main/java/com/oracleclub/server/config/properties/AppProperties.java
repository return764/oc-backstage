package com.oracleclub.server.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.oracleclub.server.entity.support.AppConstant.*;

/**
 * @author :RETURN
 * @date :2021/2/25 23:56
 */
@Data
@Configuration
@ConfigurationProperties("hello")
public class AppProperties {
    private String adminPath = "admin";

    private String workPath = USER_HOME + FILE_SEPARATOR + BASE_DIR + FILE_SEPARATOR;

}
