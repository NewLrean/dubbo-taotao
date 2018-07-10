package com.taotao.search.listener;

import com.taotao.search.service.SolrItemsService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/9.
 */
public class MyMessageListener implements MessageListener{
    @Autowired
    private SolrItemsService solrItemsService;

    @Autowired
    private SolrClient solrClient;
    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;
            //取消息内容
            String text = textMessage.getText();
            System.out.println(text);
            String[] split = text.split(",");
            if("add".equals(split[0])) {
                solrItemsService.findItemById(Long.valueOf(split[1]));
            }else if("del".equals(split[0])){
                //执行删除索引
                for (int i=1;i<split.length;i++){
                    try {
                        solrClient.deleteById(split[i]);
                        solrClient.commit();
                    } catch (SolrServerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else if("reshelf".equals(split[0])){
                //物品上架，增加物品索引
                for (int i=1;i<split.length;i++){
                    solrItemsService.findItemById(Long.valueOf(split[i]));
                }
            }else if("theshelves".equals(split[0])){
                //物品下架，更新索引

            }else if("upd".equals(split[0])){
                //物品更新，更新索引
                solrItemsService.findItemById(Long.valueOf(split[1]));
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
