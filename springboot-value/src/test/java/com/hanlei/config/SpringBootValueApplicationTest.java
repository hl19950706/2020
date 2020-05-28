package com.hanlei.config;

import com.hanlei.config.config.ValueConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName SpringBootValueApplicationTest
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/26 5:11 下午
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootValueApplicationTest {

    @Autowired
    private ValueConfig valueConfig;

    @Test
    public void test1(){
        System.out.println(valueConfig.getName());
        System.out.println(valueConfig.getValue());
    }

    @Test
    public void test2(){
        String[] strings = new String[5];
        strings[0] = null;
        strings[1] = null;
        strings[2] = null;
        strings[3] = null;
        strings[4] = null;
        System.out.println(strings[0]);
    }
}
