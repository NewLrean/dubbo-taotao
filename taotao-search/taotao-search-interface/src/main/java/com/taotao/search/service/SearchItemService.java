package com.taotao.search.service;

import com.taotao.common.pojo.SolrResult;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
public interface SearchItemService {
    SolrResult queryitemList(String queryString,Integer pageNum,Integer rows);


}
