package com.taotao.search.service.impl;

import com.taotao.common.pojo.SolrResult;
import com.taotao.search.dao.SearchItemDao;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */

@Service
public class SearchItemServiceImpl implements SearchItemService {


    @Autowired
    private SearchItemDao searchItemDao;

    @Override
    public SolrResult queryitemList(String queryString, Integer pageNum,Integer rows) {
        SolrQuery query=new SolrQuery();

        if(queryString==null||"".equals(queryString)){
            query.setQuery("*");
        }else {
            query.setQuery(queryString);
        }

        query.set("df","key_word_item");
        if(pageNum==null){
            pageNum=1;
        }
        query.setStart((pageNum-1)*rows);
        query.setRows(rows);

        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.addHighlightField("item_sellpoint");
        query.setHighlightSimplePre("<span style='color:red;font-size:15px;git status'>");
        query.setHighlightSimplePost("</span>");

        SolrResult findsolritem = searchItemDao.findsolritem(query);
        int pages= (int) (findsolritem.getTotal()%rows==0?findsolritem.getTotal()/rows:findsolritem.getTotal()%rows+1);
        findsolritem.setPages(pages);
        return findsolritem;
    }
}
