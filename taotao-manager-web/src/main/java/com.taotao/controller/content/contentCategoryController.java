package com.taotao.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.content.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/29.
 */

@Controller
public class contentCategoryController {

    @Reference
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    private List<EUTreeNode> queryContentCategories(@RequestParam(name = "id",defaultValue = "0") long parentId){
        List<EUTreeNode> categories = contentCategoryService.findCategories(parentId);
        return categories;
    }
}
