package com.taotao.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/18.
 */

@Controller
@RequestMapping("/cart")
public class CartController {

    @Value("${TT_CART}")
    private String TT_CART;
    @Value("${CART_TIME}")
    private int CART_TIME;

    @Reference
    private TbItemService tbItemService;

    @RequestMapping("/add/{itemId}")
    private String addToCart(@PathVariable Long itemId, int num, HttpServletRequest request, HttpServletResponse response){
        List<TbItem> list = getCookies(request);
        boolean flag=false;

            for (TbItem tbItem : list) {
                if (tbItem.getId() == itemId.longValue()) {
                    tbItem.setNum(tbItem.getNum() + num);
                    flag=true;
                    break;
                }
            }


        if (!flag){
            TbItem tbItem = tbItemService.selectByPrimaryKey(itemId);
            tbItem.setNum(num);
            list.add(tbItem);
        }

        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_TIME,true);
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    private String toBuyCart(HttpServletRequest request){
        List<TbItem> list = getCookies(request);
        request.setAttribute("cartList",list);
        return "cart";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    private TaotaoResult updateCart(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
        List<TbItem> list = getCookies(request);
        for(int i=0;i<list.size();i++){
            TbItem tbItem = list.get(i);
            if(tbItem.getId()==itemId.longValue()){
                tbItem.setNum(num);
                break;
            }
        }

        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_TIME,true);
        return TaotaoResult.bulid();
    }

    @RequestMapping("/delete/{itemId}")
    private String deleteCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        List<TbItem> list = getCookies(request);
        for(int i=0;i<list.size();i++){
            TbItem tbItem = list.get(i);
            if(tbItem.getId()==itemId.longValue()){
                list.remove(tbItem);
                break;
            }
        }
        request.setAttribute("cartList",list);
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(list),CART_TIME,true);
        return "cart";
    }

    public List<TbItem> getCookies(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, TT_CART,true);
        if(StringUtils.isNotEmpty(json)) {
            List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
            return list;
        }else {
            return new ArrayList<TbItem>();
        }

    }
}
