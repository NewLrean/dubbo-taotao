package com.taotao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface TbItemService {
	TbItem selectByPrimaryKey(Long id);
	
	EazyUIDataGridResult selectAllItem(int pageNum, int pageSize);
	
	TaotaoResult saveitem(TbItem item, TbItemDesc itemDesc,String itemParams);
	
	TaotaoResult deleteItems(ArrayList<Long> list);
	
	TaotaoResult reshelfItems(ArrayList<Long> list);
	
	TaotaoResult theshelvesItems(ArrayList<Long> list);

	TbItemDesc selectItemDescById(Long id);

	TaotaoResult updateItem(TbItem item, TbItemDesc itemDesc,String itemParams,Long itemParamId);
}
