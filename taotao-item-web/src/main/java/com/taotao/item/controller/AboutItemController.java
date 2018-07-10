package com.taotao.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.TbItem;
import com.taotao.rest.service.SearchAboutService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 蒋志鹏 on 2018/7/5.
 */

@Controller
public class AboutItemController {

    @Reference
    SearchAboutService searchAboutService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        System.out.println(itemId);
        TbItem item = searchAboutService.selectByPrimaryKey(itemId);
        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping(value="/item/desc/{itemId}", produces= MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId) {
        String string = searchAboutService.getItemDescById(itemId);
        return string;
    }

}
