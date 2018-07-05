package com.taotao.search.service.impl;

import com.taotao.common.pojo.SolrItems;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SolritemMapper;
import com.taotao.search.service.SolrItemsService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
@Service
public class SolrItemsServiceImpl implements SolrItemsService{

    @Autowired
    private SolritemMapper solritemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllItem() {
        List<SolrItems> allItem = solritemMapper.findAllItem();
        try {

            for (SolrItems solrItems : allItem) {
                SolrInputDocument inputFields = new SolrInputDocument();
                inputFields.addField("id", solrItems.getId().toString());
                inputFields.addField("item_title", solrItems.getTitle());
                inputFields.addField("item_sellpoint", solrItems.getSellPoint());
                inputFields.addField("item_categoryName", solrItems.getName());
                inputFields.addField("item_price", solrItems.getPrice());
                inputFields.addField("item_image", solrItems.getImage());
                inputFields.addField("item_itemDesc", solrItems.getItemDesc());
                solrServer.add(inputFields);
            }
        solrServer.commit();
            return TaotaoResult.bulid();
        }catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TaotaoResult.bulid(500,"执行错误!");
    }
}
