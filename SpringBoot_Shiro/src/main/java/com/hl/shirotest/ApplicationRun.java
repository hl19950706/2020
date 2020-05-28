package com.hl.shirotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ClassName com.hl.shirotest.ApplicationRun
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/16 5:25 下午
 * @Version 1.0
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ApplicationRun {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}
