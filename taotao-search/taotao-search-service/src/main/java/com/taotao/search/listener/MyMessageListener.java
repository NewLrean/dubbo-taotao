package com.taotao.search.listener;

import com.taotao.search.service.SolrItemsService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by 蒋志鹏 on 2018/7/9.
 */
public class MyMessageListener implements MessageListener{
    @Autowired
    private SolrItemsService solrItemsService;

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;
            //取消息内容
            String text = textMessage.getText();
            System.out.println(text);
            solrItemsService.findItemById(Long.valueOf(text));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
