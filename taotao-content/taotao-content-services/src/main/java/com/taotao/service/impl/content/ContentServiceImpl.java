package com.taotao.service.impl.content;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EazyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.redis.JedisClient;
import com.taotao.service.content.ContentService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/6/30.
 */

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private JedisClient jedisClient;

    @Value("${ADL_INDEX}")
    private String ADL_INDEX;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public EazyUIDataGridResult queryContentsBycid(long CategoryId, int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbContent> tbContents = tbContentMapper.queryContentsByCid(CategoryId);
        PageInfo<TbContent> pageInfo=new PageInfo<>(tbContents);
        EazyUIDataGridResult gridResult=new EazyUIDataGridResult();
        gridResult.setTotal(pageInfo.getTotal());
        gridResult.setRows(tbContents);
        return gridResult;

    }

    @Override
    public TaotaoResult insertOneContent(TbContent tbContent) {
        Date date=new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);

        tbContentMapper.saveOneContent(tbContent);
        jedisClient.hdel(ADL_INDEX,tbContent.getCategoryId()+"");
        return  TaotaoResult.bulid();
    }

    @Override
    public TaotaoResult deleteContentList(List<Long> longs) {
        TbContent tbContent = tbContentMapper.selectOneContent(longs.get(0));

        tbContentMapper.deleteContentList(longs);

        jedisClient.hdel(ADL_INDEX,tbContent.getCategoryId()+"");
        return TaotaoResult.bulid();
    }

    @Override
    public TaotaoResult updateOneContent(TbContent tbContent) {

        tbContent.setUpdated(new Date());
        tbContentMapper.updateContent(tbContent);
        jedisClient.hdel(ADL_INDEX,tbContent.getCategoryId()+"");
        return TaotaoResult.bulid();
    }

    @Override
    public List<TbContent> selectContentsBycid(long CategoryId) {

        //先从缓存中查看是否有缓存数据,有时返回，没时查询数据库再添加缓存
        try {
            String hget = jedisClient.hget(ADL_INDEX, CategoryId + "");
            if (hget!=null){
                List<TbContent> lists = JsonUtils.jsonToList(hget, TbContent.class);
                return lists;
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        List<TbContent> tbContents = tbContentMapper.queryContentsByCid(CategoryId);
        //缓存中没有数据时加入缓存
        try {
            jedisClient.hset(ADL_INDEX,CategoryId+"", JsonUtils.objectToJson(tbContents));
        }catch (Exception e){
            e.printStackTrace();
        }

        return tbContents;
    }
}
