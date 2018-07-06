package com.taotao.mapper.rest;

import com.taotao.pojo.TbItem;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface TbItemMapper {
    TbItem selectByPrimaryKey(Long id);

}