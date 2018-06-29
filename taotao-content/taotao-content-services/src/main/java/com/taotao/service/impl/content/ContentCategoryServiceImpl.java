package com.taotao.service.impl.content;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.content.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/29.
 */

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EUTreeNode> findCategories(long parentId) {
        List<TbContentCategory> categoryList = tbContentCategoryMapper.findCategoryList(parentId);
        List<EUTreeNode> euTreeNodes=new ArrayList<EUTreeNode>();
        for (TbContentCategory tbContentCategory:categoryList){
            EUTreeNode euTreeNode=new EUTreeNode();
            euTreeNode.setId(tbContentCategory.getId());
            euTreeNode.setText(tbContentCategory.getName());
            euTreeNode.setState(tbContentCategory.getIsparent()?"closed":"open");
            euTreeNodes.add(euTreeNode);
        }
        return euTreeNodes;
    }
}
