package com.example.template.services.common.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Getter
    @Value("${app.token.timeout:3200}")
    private Long timeout;

    @Getter
    @Value("${app.cache.key-prefix:''}")
    private String cacheKeyPrefix;
}