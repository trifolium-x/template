package co.tunan.template.application.configuration;

import co.tunan.template.services.ms.common.configuration.BaseSpringDocConfig;
import co.tunan.template.services.ms.common.model.enumeration.Const;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @title: Created by trifolium on 2021/8/23.
 * @author: trifolium
 * @date: 2019/3/12 17:41
 * @modified :
 */
@Configuration
@Profile({"dev", "test"})
public class SpringDocConfig extends BaseSpringDocConfig {

    @Bean
    public OpenAPI myOpenAPI(@Value("${application-description}")
                             String appDescription,
                             @Value("${application-version}")
                             String appVersion) {
        return new OpenAPI().info(new Info()
                .title("template接口文档")
                .description(appDescription)
                .version(appVersion)
                .contact(new Contact()
                        .name("trifolium.wang")
                        .email("trifolium.wang@foxmail.com"))
        ).components(new Components().addSecuritySchemes(Const.HTTP_HEADER_AUTH_KEY, new SecurityScheme()
                .name("Token")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
    }


}
