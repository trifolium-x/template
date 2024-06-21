package com.example.template.services.common.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @title: 应用程序配置类,建议所有的配置属性加入到该类统一管理。
 * @author: trifolium.wang
 * @date: 2024/6/21
 */
@Configuration
public class AppConfig {

    @Getter
    @Value("${app.token.timeout:3200}")
    private Long timeout;

    @Getter
    @Value("${app.cache.key-prefix:''}")
    private String cacheKeyPrefix;
}