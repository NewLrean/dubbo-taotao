package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * Created by 蒋志鹏 on 2018/7/19.
 */
public interface OrderService {

    TaotaoResult createOrder(OrderInfo orderInfo);
}
