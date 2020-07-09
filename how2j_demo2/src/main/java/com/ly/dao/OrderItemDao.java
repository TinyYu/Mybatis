package com.ly.dao;

import com.ly.domain.OrderItem;

public interface OrderItemDao {

    /**
     * 添加数据
     * @param order_item
     */
    void addOrderItem(OrderItem order_item);

    /**
     * 删除数据
     * @param order_item
     */
    void deleteOrderItem(OrderItem order_item);
}
