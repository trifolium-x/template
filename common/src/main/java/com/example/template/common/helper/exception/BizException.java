package com.example.template.common.helper.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @title: 业务异常中，对业务异常的详细描述异常
 * @author: trifolium
 * @date: 2019/3/11 19:48
 */
@Slf4j
@Getter
public class BizException extends InvokeException {

    // 业务名称
    private String bizName;

    // 业务码
    private String code;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BizException(String bizName, String code, String message) {
        super(message);
        this.bizName = bizName;
        this.code = code;
    }

}
