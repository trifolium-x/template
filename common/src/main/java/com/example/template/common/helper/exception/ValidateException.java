package com.example.template.common.helper.exception;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class ValidateException extends AppException {
    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
