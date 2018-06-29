package com.taotao.service.content;

import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/29.
 */
public interface ContentCategoryService {
    List<EUTreeNode> findCategories(long parentId);
}
