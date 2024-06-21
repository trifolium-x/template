package com.example.template.services.common.configuration;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.example.template.services.common.annotion.AuthValidator;
import com.example.template.services.common.model.enumeration.Const;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @title: SpringDoc组件等配置的基类
 * @author: trifolium
 * @date: 2023/9/12
 * @modified :
 */
public class BaseSpringDocConfig {

    @Bean
    public OperationCustomizer operationCustomizer() {

        return (operation, handlerMethod) -> {
            // 处理AuthValidator自定义注解
            AuthValidator authValidator = handlerMethod.getMethodAnnotation(AuthValidator.class);
            if (authValidator == null) {
                authValidator = handlerMethod.getBeanType().getAnnotation(AuthValidator.class);
            }
            if (authValidator != null) {
                if (authValidator.isCheckLogin()) {
                    SecurityRequirement securityRequirement = new SecurityRequirement();
                    securityRequirement.addList(Const.HTTP_HEADER_AUTH_KEY);
                    operation.addSecurityItem(securityRequirement);
                    String des = operation.getDescription();
                    operation.setDescription((des == null ? "" : (des + "</br>")) + "权限: " +
                            CollectionUtil.join(ListUtil.toList(authValidator.authorities()), ",") + "</br>" +
                            " isAnd: " + authValidator.isAnd());
                }
            }

            return operation;
        };
    }
}
