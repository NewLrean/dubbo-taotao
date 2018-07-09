package com.taotao.search.mapper;

import com.taotao.common.pojo.SolrItems;
import com.taotao.pojo.TbItem;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/4.
 */
public interface SolritemMapper {
    List<SolrItems> findAllItem();

    SolrItems findItemById(long itemId);
}
