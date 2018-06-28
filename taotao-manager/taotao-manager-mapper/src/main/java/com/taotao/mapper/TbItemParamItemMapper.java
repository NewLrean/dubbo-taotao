package com.taotao.mapper;



import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TbItemParamItemMapper {
    List<TbItemParam> selectAllItemParam();

    TbItemParamItem seItemParamItemById(Long id);

    boolean updateItemParamById(@Param("itemParams") String itemParams, @Param("itemParamId") Long itemParamId);

    boolean saveParamByItemId(@Param("itemId") Long itemId, @Param("paramData") String paramData,
                              @Param("created") Date created, @Param("updated") Date updated);
}