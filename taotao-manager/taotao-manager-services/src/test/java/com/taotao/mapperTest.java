package com.taotao;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.pojo.User;
import com.taotao.service.ItemCatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:mybatis/spring-mybatis.xml"} )
public class mapperTest {


    @Autowired
    UserService userService;


    @Autowired
    ItemCatService itemCatService;

    @Test
    public void show(){
        User user = userService.findUsersById(7);
        System.out.println(user);
    }

    @Test
    public void findCat(){
        List<EUTreeNode> catList = itemCatService.getCatList(1l);
        System.out.println(catList);
    }
}
