package com.example.template.common.helper.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
@Slf4j
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
