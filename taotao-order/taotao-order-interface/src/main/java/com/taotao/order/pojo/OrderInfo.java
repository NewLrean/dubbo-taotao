package com.taotao.order.pojo;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/20.
 */
public class OrderInfo extends TbOrder implements Serializable{

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;


    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderItems=" + orderItems +
                ", orderShipping=" + orderShipping +
                '}';
    }
}
