package com.taotao.search.dao;

import com.taotao.common.pojo.SolrItems;
import com.taotao.common.pojo.SolrResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */


public interface SearchItemDao {

    SolrResult findsolritem(SolrQuery query);
}
