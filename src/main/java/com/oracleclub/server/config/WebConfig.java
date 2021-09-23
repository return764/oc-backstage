package com.oracleclub.server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.oracleclub.server.config.properties.AppProperties;
import com.oracleclub.server.dao.inject.MySqlInject;
import com.oracleclub.server.handler.page.PageRequestHandlerArgumentResolver;
import com.oracleclub.server.interceptor.TokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * springMVC的配置文件
 * 包括拦截器，过滤器等
 * @author RETURN
 * @date 2020/8/14 0:42
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    private static final String FILE_PROTOCOL = "file:///";
    @Resource
    TokenInterceptor tokenInterceptor;
    @Resource
    AppProperties appProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/pictures/**")
                .excludePathPatterns("/api/content/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedOrigins("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageRequestHandlerArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workDir = FILE_PROTOCOL + appProperties.getWorkPath();

//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/admin/")
//                .addResourceLocations(workDir + "static/");
        log.debug("工作目录:"+workDir);

        registry.addResourceHandler("/upload/**")
                .setCacheControl(CacheControl.maxAge(7L, TimeUnit.DAYS))
                .addResourceLocations(workDir + "upload/");

        registry.addResourceHandler("/pictures/**")
                .setCacheControl(CacheControl.maxAge(7L, TimeUnit.DAYS))
                .addResourceLocations(workDir + "pictures/");
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    @Bean
    public MySqlInject mySqlInject() {
        return new MySqlInject();
    }
}
