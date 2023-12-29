package com.example.template.service.template;

import com.example.template.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.example.template", proxyBeanMethods = false)
@MapperScan(basePackages = "com.example.template.repo.mapper")
public class TemplateApplication {

    public static void main(String[] args) {

        log.info("应用启动标记--date:{}", DateUtil.getDateStr());
        SpringApplication.run(TemplateApplication.class, args);
    }

}
