package com.taotao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	/**
	 * 商品删除
	 * @param ids 删除的商品id集合字符串
	 * @return 返回json数据
	 */
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

	/**
	 * 商品上架
	 * @param ids 上架的商品id集合字符串
	 * @return 返回json数据
	 */
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

	/**
	 * 商品下架
	 * @param ids 下架的商品id集合字符串
	 * @return 返回json数据
	 */
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

	/**
	 * 查询商品描述
	 * @param id  商品id
	 * @return 返回json数据
	 */
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


	/**
	 * 更行商品信息
	 * @param tbItem 商品信息
	 * @param tbItemDesc  商品描述
	 * @param itemParams 商品规格参数
	 * @param itemParamId  商品规格参数对应的商品id
	 * @return  返回json数据
	 */
	@RequestMapping("/update")
	@ResponseBody
	private TaotaoResult updateItem(TbItem tbItem,TbItemDesc tbItemDesc,String itemParams,Long itemParamId){

		System.out.println(itemParams);
		System.out.println(itemParamId);
		TaotaoResult taotaoResult = itemService.updateItem(tbItem, tbItemDesc,itemParams,itemParamId);
		return taotaoResult;
	}

	/**
	 * 查询商品 规格参数
	 * @param id  商品id
	 * @return  返回json数据
	 */
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


	/**
	 *
	 * 动态显示商品的规格参数
	 * @param id  商品id
	 * @param model  页面显示
	 * @return 返回的页面
	 */
	@RequestMapping("/user/item/param/query/{id}")
	private String queryOneitemParam(@PathVariable Long id,Model model){

		TbItemParamItem tbItemParamItem = itemParamService.seItemParamItemById(id);
		String paramdata = tbItemParamItem.getParamdata();
		List<Map> maps = JsonUtils.jsonToList(paramdata, Map.class);
		StringBuffer buffer=new StringBuffer();
		buffer.append("<div>");
		for(Map map:maps){
			buffer.append("<h3>"+map.get("group")+"</h3>");
			buffer.append("<dl>");
			List<Map> params = (List<Map>) map.get("params");
			for(Map map1:params){
				buffer.append("<dt>"+map1.get("k")+"</dt><dd>"+map1.get("v")+"</dd>");
			}
			buffer.append("</dl>");
		}
		buffer.append("/div");
		model.addAttribute("itemParamItem",buffer);
			return  "user/useritemparam";
	}
}
