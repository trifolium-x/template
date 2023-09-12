package com.example.template.common.helper.exception;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class NoAuthException extends AppException {

    public NoAuthException(String message) {
        super(message);
    }

    public NoAuthException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
