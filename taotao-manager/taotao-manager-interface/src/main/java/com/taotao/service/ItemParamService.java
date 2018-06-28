package com.taotao.service;

import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;

import java.util.ArrayList;

/**
 * Created by 蒋志鹏 on 2018/6/23.
 */
public interface ItemParamService {

    EazyUIDataGridResult selectAllItemParam(Integer pageNum, Integer pageSize);

    TbItemParamItem seItemParamItemById(Long id);

    TbItemParam queryItemCatParamById(Long id);

    TaotaoResult saveItemParamCat(Long id,String paramData);

    TaotaoResult deleteParams(ArrayList<Long> list);

    TbItemParam selectParamById(Long paramid);
}
