package com.taotao.mapper;

import com.taotao.pojo.TbItemDesc;

public interface TbItemDescMapper {
	boolean saveTbItemDesc(TbItemDesc itemDesc);

	TbItemDesc selectItemDescById(Long id);

	boolean updateTbItemDesc(TbItemDesc itemDesc);
}