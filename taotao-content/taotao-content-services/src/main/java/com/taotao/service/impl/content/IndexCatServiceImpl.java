package com.taotao.service.impl.content;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.taotao.content.pojo.CatjsonResult;
import com.taotao.content.pojo.ItemjsonCat;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.redis.JedisClient;
import com.taotao.service.content.IndexCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/16.
 */

@Service
public class IndexCatServiceImpl implements IndexCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public List<TbItemCat> queryCats(Long parentId) {
        List<TbItemCat> itemCatList = tbItemCatMapper.getItemCatList(parentId);
        return itemCatList;
    }

    @Override
    public CatjsonResult getCatjsonResult() {
        try {
            String json = jedisClient.get("catjsonResult_list");
            if(StringUtils.isNotEmpty(json)) {
                CatjsonResult catjsonResult = JsonUtils.jsonToPojo(json, CatjsonResult.class);
                return catjsonResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        CatjsonResult catjsonResult = new CatjsonResult();
        catjsonResult.setData(getAllCats(0l));
        try {
            jedisClient.set("catjsonResult_list",JsonUtils.objectToJson(catjsonResult));
        }catch (Exception e){
            e.printStackTrace();
        }
        return catjsonResult;
    }

    public List<ItemjsonCat> getAllCats(Long parentId) {
        List<TbItemCat> tbItemCats = queryCats(parentId);
        List data = new ArrayList<>();
        int count = 0;
        for (TbItemCat tbItemCat : tbItemCats) {
            ItemjsonCat itemjsonCat = new ItemjsonCat();
            if (tbItemCat.getIsparent()) {

                itemjsonCat.setUrl("/products/" + tbItemCat.getId());
                if (parentId == 0) {

                    itemjsonCat.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    itemjsonCat.setName(tbItemCat.getName());
                }
                itemjsonCat.setItem(getAllCats(tbItemCat.getId()));
                data.add(itemjsonCat);
            } else {
                data.add("/products/" + tbItemCat.getId() + "|" + tbItemCat.getName());
            }
            count++;
            if (parentId == 0 && count >= 14) {
                break;
            }
        }
        return data;
    }
}
