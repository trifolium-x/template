package com.example.template.common.helper.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @title: 业务系统的自定义异常基类，建议所有自定义异常继承该类
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
@Slf4j
public class AppBaseException extends RuntimeException {

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
