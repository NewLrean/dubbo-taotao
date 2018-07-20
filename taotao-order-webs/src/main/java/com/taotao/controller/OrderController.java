package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/19.
 */

@Controller
@RequestMapping("/order")
public class OrderController {
    @Value("${TT_CART}")
    private String TT_CART;

    @Reference
    private OrderService orderService;



    @RequestMapping("/order-cart")
    private String getCartsToOrder(HttpServletRequest request,HttpServletResponse response){
        List<TbItem> list = getCookies(request);
        request.setAttribute("cartList",list);
        return "order-cart";
    }

    @RequestMapping("/create")
    private String createOrder(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response){
        TaotaoResult result = orderService.createOrder(orderInfo);
if(result.getStatus()==200) {
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie:cookies){
        if(TT_CART.equals(cookie.getName())){
            cookie.setValue(null);
            cookie.setMaxAge(0);// 立即销毁cookie
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
    request.setAttribute("orderId", result.getData().toString());
    request.setAttribute("payment", orderInfo.getPayment());
    DateTime dateTime = new DateTime(3);
    request.setAttribute("date", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
}else {
    try {
        throw new Exception();
    } catch (Exception e) {
        e.printStackTrace();
    }
    request.setAttribute("orderId",result.getMsg());
}
        return "success";
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
