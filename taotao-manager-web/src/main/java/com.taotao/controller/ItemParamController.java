package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/23.
 */


@Controller

@RequestMapping("/item/param")
public class ItemParamController {


    @Reference
    ItemParamService  itemParamService;

    @RequestMapping("/list")
    @ResponseBody
    private EazyUIDataGridResult getItemList(Integer page, Integer rows){

        EazyUIDataGridResult result = itemParamService.selectAllItemParam(page, rows);
        return result;

    }

    @RequestMapping("/query/itemcatid/{id}")
    @ResponseBody
    private TaotaoResult queryItemCatId(@PathVariable Long id){
        TbItemParam tbItemParam = itemParamService.queryItemCatParamById(id);
        TaotaoResult taotaoResult=new TaotaoResult();
        if(tbItemParam!=null){

            taotaoResult.setStatus(200);
            taotaoResult.setData(tbItemParam);

        }else {
            taotaoResult.setStatus(500);
        }
        return taotaoResult;
    }

    @RequestMapping("/delete/{ids}")
    @ResponseBody
    private TaotaoResult deleteParam(@PathVariable String ids){
        ArrayList<Long> list=new ArrayList<Long>();
        String[] split = ids.split(",");
        for (String string : split) {
            list.add(Long.parseLong(string));
        }

        TaotaoResult taotaoResult = itemParamService.deleteParams(list);
        return taotaoResult;

    }

    @RequestMapping("/save/{id}")
    @ResponseBody
    private TaotaoResult addCatParam(@PathVariable Long id,String paramData){
        TaotaoResult taotaoResult = itemParamService.saveItemParamCat(id, paramData);
        return taotaoResult;
    }


}
