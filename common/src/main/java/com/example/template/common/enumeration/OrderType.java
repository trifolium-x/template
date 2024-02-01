package com.example.template.common.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by trifolium on 2021/8/23.
 *
 * @modified :
 */
public enum OrderType {

    /**
     * 倒序
     */
    DESC("desc"),
    /**
     * 顺序
     */
    ASC("asc");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
