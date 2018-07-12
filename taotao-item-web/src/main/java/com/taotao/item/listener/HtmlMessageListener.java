package com.taotao.item.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.SearchAboutService;
import com.taotao.service.ItemParamService;
import com.taotao.service.TbItemService;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蒋志鹏 on 2018/7/12.
 */
public class HtmlMessageListener implements MessageListener{

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Reference
    private ItemParamService itemParamService;

    @Reference
    private TbItemService tbItemService;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage textMessage=(TextMessage)message;
            try {
                String text = textMessage.getText();
                String[] split = text.split(",");
                TbItemParamItem tbItemParamItem = itemParamService.seItemParamItemById(Long.valueOf(split[1]));
                TbItemDesc tbItemDesc = tbItemService.selectItemDescById(Long.valueOf(split[1]));
                TbItem tbItem = tbItemService.selectByPrimaryKey(Long.valueOf(split[1]));
                Map map=new HashMap();
                map.put("itemParam",tbItemParamItem);
                map.put("itemDesc",tbItemDesc);
                map.put("item",tbItem);
                map.put("query","");
                Configuration configuration = freeMarkerConfigurer.getConfiguration();
                Template template = configuration.getTemplate("item.ftl");
                Writer writer=new FileWriter(new File(REST_BASE_URL + split[1] +".html"));
                template.process(map,writer);
                writer.close();
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (MalformedTemplateNameException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (TemplateNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        }
        

    }
}
