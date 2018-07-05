package com.taotao.service.impl.content;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.content.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            euTreeNode.setState(tbContentCategory.getParent()?"closed":"open");
            euTreeNodes.add(euTreeNode);
        }
        return euTreeNodes;
    }


    @Override
    public TaotaoResult addContentCategory(long parentId,String name) {
        TbContentCategory tbContentCategory=new TbContentCategory();
        tbContentCategory.setName(name);
        tbContentCategory.setParent(false);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        Date date=new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        TbContentCategory tbContentCategory1 = tbContentCategoryMapper.selectTbContentCategoryById(parentId);
        tbContentCategory.setParentId(tbContentCategory1.getId());
        boolean flag=false;
        if(tbContentCategory1!=null){
            flag = tbContentCategoryMapper.insertTbContentCategory(tbContentCategory);
        }
        if(flag){
            if(tbContentCategory1.getParent()==false){
                tbContentCategoryMapper.updateTbContentCategoryisParent(tbContentCategory1.getId(),1,date);
            }
        }

        return TaotaoResult.bulid(tbContentCategory);
    }

    @Override
    public TaotaoResult updateTbContentCategoryName(long id,String name) {

        TbContentCategory tbContentCategory=new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());

        boolean b = tbContentCategoryMapper.updateTbContentCategoryName(tbContentCategory);
        TaotaoResult taotaoResult=null;

        if(b){
            taotaoResult=new TaotaoResult();
            taotaoResult.setStatus(200);
            taotaoResult.setData(tbContentCategory);
        }
        return taotaoResult;
    }

    @Override
    public TaotaoResult delTbContentCategory(long id) {
        TbContentCategory tbContentCategory=new TbContentCategory();
        Date date=new Date();
        tbContentCategory.setUpdated(date);
        tbContentCategory.setId(id);

        TbContentCategory tbContentCategory1 = tbContentCategoryMapper.selectTbContentCategoryById(id);
        boolean b = tbContentCategoryMapper.deleteTbContentCategory(tbContentCategory);

        if(b) {
            List<TbContentCategory> categoryList = tbContentCategoryMapper.findCategoryList(tbContentCategory1.getParentId());
            if(categoryList==null||categoryList.size()==0){
                 tbContentCategoryMapper.updateTbContentCategoryisParent(tbContentCategory1.getParentId(), 0, date);
            }
        }
        return TaotaoResult.bulid();
    }
}
