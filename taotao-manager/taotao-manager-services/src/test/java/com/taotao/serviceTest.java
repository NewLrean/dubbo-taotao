package com.taotao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/6/22.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:mybatis/spring-mybatis.xml")
public class serviceTest {



    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"mybatis/spring-mybatis.xml"});

        context.start();
        System.out.println("----------------服务已启动，按任意键结束···········--------------------");
        System.in.read(); // press any key to exit
    }
       // ClassPathXmlApplicationContext context2 = new ClassPathXmlApplicationContext(new String[]{"mybatis/spring-mybatis.xml"});



}
