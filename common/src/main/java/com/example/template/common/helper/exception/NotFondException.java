package com.example.template.common.helper.exception;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class NotFondException extends AppException {
    public NotFondException(String message) {
        super(message);
    }

    public NotFondException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
