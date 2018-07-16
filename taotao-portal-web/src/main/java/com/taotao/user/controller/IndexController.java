package com.taotao.user.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.content.pojo.CatjsonResult;
import com.taotao.content.pojo.ItemjsonCat;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbItemCat;
import com.taotao.service.content.ContentService;
import com.taotao.service.content.IndexCatService;
import com.taotao.user.pojo.AD1Node;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/2.
 */

@Controller
public class IndexController {
    @Value("${AD1NODE_WIDTH}")
    private Integer AD1NODE_WIDTH;
    @Value("${AD1NODE_HEIGHT}")
    private Integer AD1NODE_HEIGHT;
    @Value("${AD1NODE_WIDTHB}")
    private Integer AD1NODE_WIDTHB;
    @Value("${AD1NODE_HEIGHTB}")
    private Integer AD1NODE_HEIGHTB;

    @Reference
    private ContentService contentService;

    @Reference
    private IndexCatService indexCatService;

    @RequestMapping("/index")
    private String showIndex(Model model){
        List<TbContent> tbContents = contentService.selectContentsBycid(89);
        List<AD1Node> ad1Nodes=new ArrayList<>();

        for (TbContent tbContent : tbContents){
            AD1Node ad1Node=new AD1Node();
            ad1Node.setSrcB(tbContent.getPic2());
            ad1Node.setHeight(AD1NODE_HEIGHT);
            ad1Node.setAlt(tbContent.getTitleDesc());
            ad1Node.setSrc(tbContent.getPic());
            ad1Node.setWidth(AD1NODE_WIDTH);
            ad1Node.setWidthB(AD1NODE_WIDTHB);
            ad1Node.setHref(tbContent.getUrl());
            ad1Node.setHeightB(AD1NODE_HEIGHTB);
            ad1Nodes.add(ad1Node);
        }
        String json = JsonUtils.objectToJson(ad1Nodes);
        model.addAttribute("ad1",json);
        return "index";
    }


    @RequestMapping(value = "/rest/itemcat/all")
    @ResponseBody
    private String queryCatAll(String callback){

        CatjsonResult catjsonResult = indexCatService.getCatjsonResult();
        if(StringUtils.isNotEmpty(callback)){
            return callback+"("+JsonUtils.objectToJson(catjsonResult)+");";
        }
        return JsonUtils.objectToJson(catjsonResult);
    }



}
