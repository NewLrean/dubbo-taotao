package com.taotao.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 蒋志鹏 on 2018/7/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:mybatis/spring-mybatis.xml"} )
public class ClusterTest {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void show() {
        Set<HostAndPort> nodes = new HashSet<>();

        nodes.add(new HostAndPort("192.168.25.152",7001));
        nodes.add(new HostAndPort("192.168.25.152",7002));
        nodes.add(new HostAndPort("192.168.25.152",7003));
        nodes.add(new HostAndPort("192.168.25.152",7004));
        nodes.add(new HostAndPort("192.168.25.152",7005));
        nodes.add(new HostAndPort("192.168.25.152",7006));


        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("key","123");
        System.out.println(jedisCluster.get("key"));
    }

    @Test
    public void test(){
        String s = jedisCluster.get("key");
        System.out.println(s);
    }
}
