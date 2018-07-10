package com.taotao.rest.listener;


import com.taotao.redis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by 蒋志鹏 on 2018/7/9.
 */
public class UpdateRedisListener implements MessageListener{


    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Autowired
    private JedisClient jedisClient;
    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;
            //取消息内容
            String text = textMessage.getText();
            System.out.println(text);
            String[] split = text.split(",");
            for (int i=1;i<split.length;i++){
                jedisClient.del(REDIS_ITEM_KEY+":"+split[i]+":BASE");
                jedisClient.del(REDIS_ITEM_KEY+":"+split[i]+":DESC");
            }


        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
