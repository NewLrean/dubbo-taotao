package com.taotao.order.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/19.
 */

@Service
public class OrderServiceImpl implements OrderService{

    @Value("${TT_ORDER_ID}")
    private String TT_ORDER_ID;

    @Value("${TT_ORDER_KEY}")
    private String TT_ORDER_KEY;

    @Value("${TT_ORDER_ITEM_ID}")
    private String TT_ORDER_ITEM_ID;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        String orderid = null;
        try {
            if(!jedisCluster.exists(TT_ORDER_KEY)){
                jedisCluster.set(TT_ORDER_KEY,TT_ORDER_ID);
            }
            orderid = jedisCluster.incr(TT_ORDER_KEY).toString();
            orderInfo.setOrderid(orderid);
            Date date=new Date();
            orderInfo.setStatus(1);
            orderInfo.setPostfee("0");
            orderInfo.setCreatetime(date);
            orderInfo.setUpdatetime(date);
            tbOrderMapper.insertOrder(orderInfo);
            List<TbOrderItem> orderItems = orderInfo.getOrderItems();
            for (TbOrderItem tbOrderItem:orderItems){
                Long incr = jedisCluster.incr(TT_ORDER_ITEM_ID);
                tbOrderItem.setId(incr.toString());
                tbOrderItem.setOrderid(orderid);
                tbOrderItemMapper.insertOrderItem(tbOrderItem);
            }

            TbOrderShipping orderShipping = orderInfo.getOrderShipping();
            orderShipping.setOrderid(orderid);
            orderShipping.setCreated(date);
            orderShipping.setUpdated(date);
            tbOrderShippingMapper.insertOrderShipping(orderShipping);
        } catch (Exception e) {

            e.printStackTrace();
            return TaotaoResult.bulid(500,"当前网络繁忙！");
        }

        return TaotaoResult.bulid(orderid);
    }
}
