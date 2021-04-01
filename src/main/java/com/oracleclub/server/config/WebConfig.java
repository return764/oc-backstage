package com.oracleclub.server.config;

import com.oracleclub.server.config.properties.AppProperties;
import com.oracleclub.server.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * springMVC的配置文件
 * 包括拦截器，过滤器等
 * @author RETURN
 * @date 2020/8/14 0:42
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String FILE_PROTOCOL = "file:///";
    @Resource
    TokenInterceptor tokenInterceptor;
    @Resource
    AppProperties appProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .allowedOrigins("*");
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workDir = FILE_PROTOCOL + appProperties.getWorkPath();

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/admin/")
                .addResourceLocations(workDir + "static/");

        registry.addResourceHandler("/upload/**")
                .setCacheControl(CacheControl.maxAge(7L, TimeUnit.DAYS))
                .addResourceLocations(workDir + "upload/");
    }

}
