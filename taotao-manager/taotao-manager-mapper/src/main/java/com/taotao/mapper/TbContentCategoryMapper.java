package com.taotao.mapper;


import com.taotao.pojo.TbContentCategory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TbContentCategoryMapper {
        List<TbContentCategory> findCategoryList(long parentId);

        boolean insertTbContentCategory(TbContentCategory tbContentCategory);

        TbContentCategory selectTbContentCategoryById(long parentId);

        /**
         * 这里是调整文件是作为文件夹还是普通文件表示
         * @param id  自身id
         * @param isParent  是否是文件夹  1：是  0：否
         * @param date 修改日期
         * @return
         */
        boolean updateTbContentCategoryisParent(@Param("id") long id,@Param("isParent")Integer isParent, @Param("date")Date date);

        boolean updateTbContentCategoryName(TbContentCategory tbContentCategory);

        boolean deleteTbContentCategory(TbContentCategory tbContentCategory);

}