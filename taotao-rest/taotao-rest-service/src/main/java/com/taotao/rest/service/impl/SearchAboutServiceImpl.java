package com.taotao.rest.service.impl;

import com.taotao.mapper.rest.TbItemDescMapper;
import com.taotao.mapper.rest.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.redis.JedisClient;
import com.taotao.rest.service.SearchAboutService;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by 蒋志鹏 on 2018/7/5.
 */

@Service
public class SearchAboutServiceImpl implements SearchAboutService{

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemMapper tbItemMapper;


    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TbItem selectByPrimaryKey(long id) {
        // 先查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果缓存中没有命中，那么查询数据库
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        // 添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":BASE", JsonUtils.objectToJson(tbItem));
            // 设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":BASE", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tbItem;
    }

    @Override
    public String getItemDescById(long id) {

        // 先查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                if(tbItemDesc!=null) {
                    String s = JsonUtils.objectToJson(tbItemDesc);
                    return s;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果缓存中没有命中，那么查询数据库
        TbItemDesc tbItemDesc = tbItemDescMapper.selectItemDescById(id);
        // 添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            // 设置过期时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":DESC", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(tbItemDesc!=null) {
            String s = JsonUtils.objectToJson(tbItemDesc);
            return s;
        }
        return null;
    }
}
