package com.ly.dao;

import com.ly.domain.Order;

import java.util.List;

public interface OrderDao {
    /**
     * 查询全部
     * @return
     */
    List<Order> listOrder();

    /**
     * 指定条件查询
     * @param id
     * @return
     */
    Order getOrder(int id);

    /**
     * 删除订单，同时删除订单项
     * @param id
     */
    void deleteOrder(int id);
}
