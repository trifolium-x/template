package com.example.template.service.template.test.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @title: TestCode
 * @author: trifolium.wang
 * @date: 2023/10/17
 * @modified :
 */
public class TestCode {

    @Test
    public void test(){
        System.out.println(convertCamelCaseToUnderscore("userNameOrPWD"));
    }

    private static String convertCamelCaseToUnderscore(String camelCase) {
        Pattern pattern = Pattern.compile("([A-Z][a-z\\d]*)");
        Matcher matcher = pattern.matcher(camelCase);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "_" + matcher.group(1).toLowerCase());
        }
        matcher.appendTail(buffer);
        return buffer.toString().toLowerCase();
    }
}
