package com.taotao.service.content;

import com.taotao.content.pojo.CatjsonResult;
import com.taotao.pojo.TbItemCat;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/16.
 */
public interface IndexCatService {
    List<TbItemCat> queryCats(Long parentId);

    CatjsonResult getCatjsonResult();
}
