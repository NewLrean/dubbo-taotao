package com.taotao.service.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/9.
 */
public class TopicTest {


    @Test
    public void topicComuser() throws IOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:mybatis/spring-mybatis.xml");
        System.in.read();
    }
}
