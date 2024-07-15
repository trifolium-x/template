package com.example.template.common.helper.exception;

/**
 * @title: 业务调用异常，常用来对未知错误进行抛射
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
public class InvokeException extends AppBaseException {
    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
