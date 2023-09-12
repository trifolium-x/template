package co.tunan.template.services.ms.common.configuration;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import co.tunan.template.services.ms.common.annotion.TokenValidator;
import co.tunan.template.services.ms.common.model.enumeration.Const;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @title: BaseSpringDocConfig
 * @author: trifolium
 * @date: 2023/9/12
 * @modified :
 */
public class BaseSpringDocConfig {

    @Bean
    public OperationCustomizer operationCustomizer() {

        return (operation, handlerMethod) -> {
            // 处理TokenValidator自定义注解
            TokenValidator tokenValidator = handlerMethod.getMethodAnnotation(TokenValidator.class);
            if (tokenValidator == null) {
                tokenValidator = handlerMethod.getBeanType().getAnnotation(TokenValidator.class);
            }
            if (tokenValidator != null) {
                if (tokenValidator.isCheckLogin()) {
                    SecurityRequirement securityRequirement = new SecurityRequirement();
                    securityRequirement.addList(Const.HTTP_HEADER_AUTH_KEY);
                    operation.addSecurityItem(securityRequirement);
                    String des = operation.getDescription();
                    operation.setDescription((des == null ? "" : (des + "</br>")) + "权限: " +
                            CollectionUtil.join(ListUtil.toList(tokenValidator.authorities()), ",") + "</br>" +
                            " isAnd: " + tokenValidator.isAnd());
                }
            }

            return operation;
        };
    }
}
