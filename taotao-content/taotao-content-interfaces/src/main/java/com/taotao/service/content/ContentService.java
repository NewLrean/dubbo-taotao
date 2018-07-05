package com.taotao.service.content;

import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/30.
 */
public interface ContentService {
    /**
     * 根据商品类型查询内容
     * @param CategoryId  类型id
     * @param page 当前页数
     * @param rows 显示条数
     * @return  放回所有数据
     */
    EazyUIDataGridResult queryContentsBycid(long CategoryId,int page,int rows);

    /**
     * 添加 一个内用对象
     * @param tbContent 内容本身
     * @return 返回是否成功
     */
    TaotaoResult insertOneContent(TbContent tbContent);

    /**
     * 批量删除
     * @param longs 所有id的集合
     * @return 返回是否成功
     */
    TaotaoResult deleteContentList(List<Long> longs);

    /**
     * 更新单个内容
     * @param tbContent 内容对象
     * @return 返回成功
     */
    TaotaoResult updateOneContent(TbContent tbContent);

    List<TbContent> selectContentsBycid(long CategoryId);
}
