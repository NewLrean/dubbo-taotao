package com.taotao.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taotao.mapper.TbItemParamItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.TbItemService;
import com.taotao.utils.IdGenrtor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TbItemServiceImpl implements TbItemService {

	
	@Autowired
	private TbItemMapper tbitemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		TbItem tbItem = tbitemMapper.selectByPrimaryKey(id);
		if(tbItem!=null){
			return tbItem;
		}
		return null;
	}


	@Override
	public EazyUIDataGridResult selectAllItem(int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize); 
		List<TbItem> items = tbitemMapper.selectAllItem();
		PageInfo<TbItem> pageInfo=new PageInfo<>(items);
		EazyUIDataGridResult gridResult=new EazyUIDataGridResult();
		gridResult.setTotal(pageInfo.getTotal());
		gridResult.setRows(pageInfo.getList());
		return gridResult;
	}

	/**
	 * 该方法已在事务里声明了，所以该方法一面的方法是一体的，有一个报错便会回滚，所以这里不用写事务
	 * 在这里只需要抛出异常就可以了
	 */
	@SuppressWarnings("finally")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public TaotaoResult saveitem(TbItem tbItem,TbItemDesc itemDesc,String itemParams) {
		
		Long itemID = IdGenrtor.getItemID();
		tbItem.setId(itemID);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date date=new Date();

		tbItem.setStatus((byte) 1);
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		itemDesc.setItemid(itemID);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemParamItemMapper.saveParamByItemId(itemID,itemParams,date,date);
		
		tbitemMapper.saveitem(tbItem);
		
		boolean saveTbItemDesc = itemDescMapper.saveTbItemDesc(itemDesc);


		if (!saveTbItemDesc) {

try {
				
				throw new Exception();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				return new TaotaoResult(500, "失败");
			}
			
		}
		
		return TaotaoResult.bulid();
		
	}


	@Override
	public TaotaoResult deleteItems(ArrayList<Long> list) {
		Map map=new HashMap<>();
		 boolean deleteItems = tbitemMapper.deleteItems(list);
		 if(!deleteItems){
			 return new TaotaoResult(500, "删除失败");
		 }
		 
		 return TaotaoResult.bulid();
	}


	@Override
	public TaotaoResult reshelfItems(ArrayList<Long> list) {
		// TODO Auto-generated method stub
		Map map=new HashMap<>();
		 boolean deleteItems = tbitemMapper.reshelfItems(list);
		 if(!deleteItems){
			 return new TaotaoResult(500, "删除失败");
		 }
		 
		 return TaotaoResult.bulid();
	}


	@Override
	public TaotaoResult theshelvesItems(ArrayList<Long> list) {
		// TODO Auto-generated method stub
		Map map=new HashMap<>();
		 boolean deleteItems = tbitemMapper.theshelvesItems(list);
		 if(!deleteItems){
			 return new TaotaoResult(500, "删除失败");
		 }
		 
		 return TaotaoResult.bulid();
	}

	@Override
	public TbItemDesc selectItemDescById(Long id) {
		TbItemDesc tbItemDesc = itemDescMapper.selectItemDescById(id);
		return tbItemDesc;
	}



	@Override
	public TaotaoResult updateItem(TbItem item, TbItemDesc itemDesc,String itemParams,Long itemParamId) {
		Date date=new Date();
		item.setUpdated(date);
		tbitemMapper.updateitem(item);
		itemDesc.setItemid(item.getId());
		itemDesc.setUpdated(date);
		itemDescMapper.updateTbItemDesc(itemDesc);
		boolean b = itemParamItemMapper.updateItemParamById(itemParams, itemParamId);
		if(!b){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				return new TaotaoResult(500, "失败");
			}
		}
		return TaotaoResult.bulid();
	}

}
