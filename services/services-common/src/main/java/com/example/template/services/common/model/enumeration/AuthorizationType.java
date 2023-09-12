package com.example.template.services.common.model.enumeration;

/**
 * @title: AuthorizationType
 * @author: trifolium
 * @date: 2023/1/6
 * @modified :
 */
public enum AuthorizationType {

    INTERFACE("1"), PAGE("2");

    private final String code;

    AuthorizationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
