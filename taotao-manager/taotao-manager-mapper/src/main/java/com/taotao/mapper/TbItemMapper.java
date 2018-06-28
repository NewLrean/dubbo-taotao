package com.taotao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface TbItemMapper {
    TbItem selectByPrimaryKey(Long id);
    
    List<TbItem> selectAllItem();
    
    boolean saveitem(TbItem item);
    
    boolean deleteItems(@Param("list") ArrayList<Long> list);
    
    boolean reshelfItems(@Param("list") ArrayList<Long> list);
    
    boolean theshelvesItems(@Param("list") ArrayList<Long> list);

    boolean updateitem(TbItem item);
}