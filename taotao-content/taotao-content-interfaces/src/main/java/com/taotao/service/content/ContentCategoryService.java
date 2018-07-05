package com.taotao.service.content;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/29.
 */
public interface ContentCategoryService {
    List<EUTreeNode> findCategories(long parentId);


    /**
     * 添加内容节点
     * @param parentId 内容节点父类id
     * @param name 节点名字
     * @return TaotaoResult   json数据
     */
    TaotaoResult addContentCategory(long parentId,String name);

    TaotaoResult updateTbContentCategoryName(long id,String name);

    /**
     * 删除节点
     * @param parentId  父节点id
     * @param id  自身id
     * @return  返回消息
     * 注：删除时需要判断如果父类文件没有子节点是将父类节点变为文本文件
     */
    TaotaoResult delTbContentCategory(long id);
}
