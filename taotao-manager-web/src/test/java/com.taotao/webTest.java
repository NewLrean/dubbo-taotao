package com.taotao;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:MATE-INF/spring/dubbo-consumer.xml"} )
public class webTest {


    @Reference
    UserService userService;


    @Test
    public void show() throws IOException {
       //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"MATE-INF/spring/dubbo-consumer.xml"});
        //UserService userService = (UserService) context.getBean("userService");
        System.out.println("*************");

        User usersById = userService.findUsersById(7);

        System.out.println(usersById);
        System.in.read();
    }
}
