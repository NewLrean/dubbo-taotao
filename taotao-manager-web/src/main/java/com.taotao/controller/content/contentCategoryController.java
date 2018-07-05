package com.taotao.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.content.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/29.
 */

@Controller
@RequestMapping("/content/category")
public class contentCategoryController {

    @Reference
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    private List<EUTreeNode> queryContentCategories(@RequestParam(name = "id",defaultValue = "0") long parentId){
        System.out.println(parentId);
        List<EUTreeNode> categories = contentCategoryService.findCategories(parentId);
        return categories;
    }

    @RequestMapping("/create")
    @ResponseBody
    private TaotaoResult insertContentCate(Long parentId,String name){
        System.out.println(parentId+">>>>>>>>>>>"+name);
        TaotaoResult taotaoResult = contentCategoryService.addContentCategory(parentId, name);
        return taotaoResult;
    }

    @RequestMapping("/delete")
    @ResponseBody
    private TaotaoResult deleContentCate(Long id){
        System.out.println(">>>>>>>>>>"+id);

        TaotaoResult taotaoResult = contentCategoryService.delTbContentCategory(id);
        return taotaoResult;
    }


    @RequestMapping("/update")
    @ResponseBody
    private TaotaoResult updateContentCate(long id,String name){
        System.out.println(id);
        TaotaoResult taotaoResult = contentCategoryService.updateTbContentCategoryName(id, name);
        return taotaoResult;
    }

}
