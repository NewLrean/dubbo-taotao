package com.taotao.service.test;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
public class SolrTest {



    @Test
    public void showsolr() {
       /* SolrServer solrServer = new HttpSolrServer("http://192.168.25.153:8080/solr/mycore");
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
        }*/

    }


    @Test
    public void imports() throws IOException, SolrServerException {
        //SolrClient solrClient=new HttpSolrClient.Builder("http://192.168.25.153:8080/solr/mycore2").build();
        CloudSolrClient solrClient = new CloudSolrClient.Builder().withZkHost("192.168.25.151:2181,192.168.25.151:2182,192.168.25.151:2183").build();
    solrClient.setDefaultCollection("collection2");
        SolrInputDocument inputFields=new SolrInputDocument();
        inputFields.addField("id","001");
        solrClient.add(inputFields);
        solrClient.commit();
    }
}
