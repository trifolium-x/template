package co.tunan.template.application.configuration;

import co.tunan.template.services.ms.common.configuration.BaseWebMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * @title: AppWebMvcConfig
 * @author: trifolium
 * @date: 2023/9/12
 * @modified :
 */
@Configuration
@ControllerAdvice
public class AppWebMvcConfig extends BaseWebMvcConfig {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
