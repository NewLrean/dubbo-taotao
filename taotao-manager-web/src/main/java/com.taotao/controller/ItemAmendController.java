package com.taotao.controller;

import java.util.ArrayList;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;

@Controller
@RequestMapping("/rest/item")
public class ItemAmendController {

	
	@Reference
	TbItemService itemService;

	@Reference
	ItemParamService itemParamService;

	
	@RequestMapping("/delete/{ids}")
	@ResponseBody
	private TaotaoResult deleteItem(@PathVariable String ids){
		ArrayList<Long> list=new ArrayList<Long>();
		String[] split = ids.split(",");
		for (String string : split) {
			list.add(Long.parseLong(string));
		}
		
		TaotaoResult deleteItems = itemService.deleteItems(list);
		System.out.println(ids);
		return deleteItems;
		
	}
	
	
	@RequestMapping("/reshelf/{ids}")
	@ResponseBody
	private TaotaoResult shelvesItem(@PathVariable String ids){
		ArrayList<Long> list=new ArrayList<Long>();
		String[] split = ids.split(",");
		for (String string : split) {
			list.add(Long.parseLong(string));
		}
		
		TaotaoResult reshelfItems = itemService.reshelfItems(list);
		System.out.println(ids);
		return reshelfItems;
		
	}
	@RequestMapping("/instock/{ids}")
	@ResponseBody
	private TaotaoResult soldoutItem(@PathVariable String ids){
		ArrayList<Long> list=new ArrayList<Long>();
		String[] split = ids.split(",");
		for (String string : split) {
			list.add(Long.parseLong(string));
		}
		
		TaotaoResult theshelvesItems = itemService.theshelvesItems(list);
		System.out.println(ids);
		return theshelvesItems;
		
	}


	@RequestMapping("/query/item/desc/{id}")
	@ResponseBody
	private TaotaoResult queryItemDesc(@PathVariable Long id){
		TbItemDesc tbItemDesc = itemService.selectItemDescById(id);
		TaotaoResult taotaoResult=new TaotaoResult();
		if(tbItemDesc!=null){
			taotaoResult.setStatus(200);
			taotaoResult.setData(tbItemDesc);
		}
		return taotaoResult;
	}



	@RequestMapping("/update")
	@ResponseBody
	private TaotaoResult updateItem(TbItem tbItem,TbItemDesc tbItemDesc,String itemParams,Long itemParamId){

		System.out.println(itemParams);
		System.out.println(itemParamId);
		TaotaoResult taotaoResult = itemService.updateItem(tbItem, tbItemDesc,itemParams,itemParamId);
		return taotaoResult;
	}

	@RequestMapping("/param/item/query/{id}")
	@ResponseBody
	private TaotaoResult queryOneParam(@PathVariable Long id){

		TbItemParamItem tbItemParamItem = itemParamService.seItemParamItemById(id);
		TaotaoResult taotaoResult=null;
		if(tbItemParamItem!=null){
			taotaoResult=new TaotaoResult();
			taotaoResult.setStatus(200);
			taotaoResult.setData(tbItemParamItem);
		}
		return taotaoResult;
	}
}
