package com.oracleclub.server.config;

import com.oracleclub.server.config.properties.AppProperties;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * EhCache的配置文件，使用EhCache的好处：
 * 少量数据缓存在堆内内存   速度快
 * 部分数据缓存在堆外内存   速度比堆内慢
 * 大量数据缓存在硬盘中     速度最慢
 *
 * 不需要安装额外的软件比如redis
 * @author RETURN
 * @date 2020/8/14 13:09
 */
@Configuration
public class EhCacheConfig {

    @Resource
    private AppProperties appProperties;

    private CacheManager cacheManager;
    private final CacheConfiguration<String,String> cacheConfiguration;
    private final CacheConfiguration<String, String> tokenCacheConfiguration;
    private final CacheConfiguration<String, String> refreshTokenCacheConfiguration;
    private final ResourcePools build;

    {
        /**
         * 配置资源池
         * heap表示堆内 offheap表示堆外内存 disk表示硬盘
         */
        build = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(2000L, EntryUnit.ENTRIES)
//                .offheap(20L, MemoryUnit.MB)
//                .disk(500L, MemoryUnit.MB, true)
                .build();

        /**
         * 创建缓存配置
         * 使用tti最大空闲时间默认为60s(缓存中资源60s没有获取或更新就删除)
         */
        cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class,String.class,build)
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(
                        Duration.of(60, ChronoUnit.MINUTES)
                ))
                .build();

        tokenCacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class,String.class,build)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                        Duration.of(30, ChronoUnit.MINUTES)
                ))
                .build();

        refreshTokenCacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class,String.class,build)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                        Duration.of(3, ChronoUnit.DAYS)
                ))
                .build();
    }

    /**
     * 创建缓存管理器
     * 指定本地路径缓存位置
     * 创建defaultCache和token缓存
     * 初始化
     */
    private void initCacheManager(){
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(appProperties.getWorkPath()+"/temp"))
                .withCache("defaultCache",cacheConfiguration)
                .withCache("token",tokenCacheConfiguration)
                .withCache("verifyCode", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class,String.class,build)
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                                Duration.of(30, ChronoUnit.MINUTES)
                        ))
                        .build())
                .withCache("refreshTokenCache",refreshTokenCacheConfiguration)
                .build(true);
    }

    /**
     * 获取token缓存
     */
    @Bean("tokenCache")
    public Cache<String, String> tokenCache(){
        if (cacheManager == null){
            initCacheManager();
        }
        return cacheManager.getCache("token",String.class,String.class);
    }

    @Bean("verifyCode")
    public Cache<String,String> verifyCode(){
        if (cacheManager == null){
            initCacheManager();
        }
        return cacheManager.getCache("verifyCode",String.class,String.class);
    }

    @Bean("refreshTokenCache")
    public Cache<String,String> refreshTokenCache(){
        if (cacheManager == null){
            initCacheManager();
        }
        return cacheManager.getCache("refreshTokenCache",String.class,String.class);
    }
}
