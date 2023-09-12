package com.example.template.common.helper.exception;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class InvokeException extends AppException {
    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
