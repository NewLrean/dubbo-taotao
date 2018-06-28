package com.taotao.mapper;


import com.taotao.pojo.TbItemParam;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;

public interface TbItemParamMapper {
    TbItemParam queryItemCatParamById(Long id);

    boolean saveItemParamCat(@Param("itemCatId") Long itemCatId, @Param("paramData") String paramData,
                             @Param("created") Date created, @Param("updated") Date updated);

    boolean deleteParams(@Param("list") ArrayList<Long> list);
}