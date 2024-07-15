package com.example.template.common.helper.exception;

import lombok.Getter;

import java.util.List;

/**
 * @title:
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
@Getter
public class ValidateException extends AppBaseException {

    private List<String> filed;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, List<String> filed) {
        super(message);
        this.filed = filed;
    }

    public ValidateException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
