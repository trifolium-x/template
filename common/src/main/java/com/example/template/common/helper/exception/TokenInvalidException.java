package com.example.template.common.helper.exception;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class TokenInvalidException extends AppException {

    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
