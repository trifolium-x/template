package co.tunan.template.common.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 基础Enum
 */
public interface BaseEnum {

    @JsonValue
    String getName();
}
