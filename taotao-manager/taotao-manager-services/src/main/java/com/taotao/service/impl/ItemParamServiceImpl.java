package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/23.
 */

@Service
public class ItemParamServiceImpl implements ItemParamService{



    @Autowired
    TbItemParamItemMapper itemParamItemMapper;


    @Autowired
    TbItemParamMapper tbItemParamMapper;
    @Override
    public EazyUIDataGridResult selectAllItemParam(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbItemParam> itemparams = itemParamItemMapper.selectAllItemParam();
        for (TbItemParam tbItemParam:itemparams){
            tbItemParam.setItemCatName(tbItemParam.getTbItemCat().getName());
        }
        System.out.println(itemparams);
        PageInfo<TbItemParam> pageInfo=new PageInfo<>(itemparams);
        EazyUIDataGridResult gridResult=new EazyUIDataGridResult();
        gridResult.setTotal(pageInfo.getTotal());
        gridResult.setRows(itemparams);
        return gridResult;
    }

    @Override
    public TbItemParamItem seItemParamItemById(Long id) {
        TbItemParamItem tbItemParamItem = itemParamItemMapper.seItemParamItemById(id);

        if(tbItemParamItem!=null){
            return tbItemParamItem;
        }
        return null;
    }

    @Override
    public TbItemParam queryItemCatParamById(Long id) {
        TbItemParam tbItemParam = tbItemParamMapper.queryItemCatParamById(id);
        if(tbItemParam!=null){
            return  tbItemParam;
        }
        return null;
    }

    @Override
    public TaotaoResult saveItemParamCat(Long id, String paramData) {
        Date date=new Date();
        TaotaoResult taotaoResult=null;
        if(paramData!=null&&paramData!="[]") {
            boolean b = tbItemParamMapper.saveItemParamCat(id, paramData, date, date);
            if (b) {
                taotaoResult = new TaotaoResult();
                taotaoResult.setStatus(200);
            }
        }
        return taotaoResult;
    }

    @Override
    public TaotaoResult deleteParams(ArrayList<Long> list) {
        TaotaoResult taotaoResult = new TaotaoResult();

        boolean b = tbItemParamMapper.deleteParams(list);
        if(b){

            taotaoResult.setStatus(200);
        }else{
            taotaoResult.setStatus(500);
        }
        return taotaoResult;
    }

    @Override
    public TbItemParam selectParamById(Long paramid) {
        TbItemParam tbItemParam = tbItemParamMapper.selectParamById(paramid);
        return tbItemParam;
    }


}
