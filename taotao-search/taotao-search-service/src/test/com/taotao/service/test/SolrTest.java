package com.taotao.service.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
public class SolrTest {


    @Test
    public void showsolr() {
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.153:8080/solr/mycore");
        SolrInputDocument inputFields=new SolrInputDocument();
        inputFields.addField("id","001");
        inputFields.addField("item_title","电话");
        try {
            solrServer.add(inputFields);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
