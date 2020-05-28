package com.hanlei.config.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName ValueConfig
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/26 5:06 下午
 * @Version 1.0
 */
@Component
public class ValueConfig {

    @Value("${test.name}")
    private String name;

    @Value("${test.value}")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ValueConfig(){
        System.out.println(value);
        System.out.println(name);
    }
}
