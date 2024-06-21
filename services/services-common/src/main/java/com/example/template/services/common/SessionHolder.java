package com.example.template.services.common;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @title: 会话上下文，用于获取request，session等。
 * @author: trifolium
 * @date: 2021/9/1
 * @modified :
 */
public class SessionHolder {
    private SessionHolder() {
    }

    public static HttpSession getSession() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return null;
        }
        HttpServletRequest request = sra.getRequest();
        return request.getSession(true);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return null;
        }
        return sra.getRequest();
    }

    public static String getRemoteIp() {
        String unknown = "unknown";
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return null;
        }
        List<String> head = Arrays.asList("X-Real-IP", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");
        for (String h : head) {
            String ip = sra.getRequest().getHeader(h);
            if (!StrUtil.isBlank(ip) && !unknown.equalsIgnoreCase(ip)) {
                int index = ip.indexOf(',');
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
        }

        return sra.getRequest().getRemoteAddr();
    }

}
