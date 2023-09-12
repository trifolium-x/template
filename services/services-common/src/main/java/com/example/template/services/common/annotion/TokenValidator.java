package com.example.template.services.common.annotion;

import java.lang.annotation.*;

/**
 * @title: 权限过滤注解
 * @author: trifolium
 * @date: 2019/3/12 17:41
 * @modified :
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TokenValidator {

    /**
     * 权限数组
     */
    String[] authorities() default {};

    /**
     * 权限是否与
     */
    boolean isAnd() default false;

    /**
     * 是否检查权限
     */
    boolean isCheckPermission() default true;

    /**
     * 是否检查登录
     */
    boolean isCheckLogin() default true;

}
