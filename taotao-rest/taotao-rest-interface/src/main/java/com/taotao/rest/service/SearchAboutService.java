package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * Created by 蒋志鹏 on 2018/7/5.
 */
public interface SearchAboutService {
    TbItem selectByPrimaryKey(long id);

    String getItemDescById(long id);
}
