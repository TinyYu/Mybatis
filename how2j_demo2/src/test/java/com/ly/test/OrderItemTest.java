package com.ly.test;

import com.ly.dao.OrderItemDao;
import com.ly.domain.Order;
import com.ly.domain.OrderItem;
import com.ly.domain.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class OrderItemTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private OrderItemDao orderItemDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = factory.openSession();
        orderItemDao = sqlSession.getMapper(OrderItemDao.class);
    }

    @After
    public void over() throws IOException {
        sqlSession.close();
        inputStream.close();
    }

    /**
     * 添加数据
     */
    @Test
    public void addOrderItemTest(){
        Order order = sqlSession.selectOne("getOrder",1);

        Product product = sqlSession.selectOne("listProductId",6);

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(100);
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        orderItemDao.addOrderItem(orderItem);
        sqlSession.commit();
    }

    /**
     * 删除数据
     */
    @Test
    public void deleteOrderItem(){
        Order order = sqlSession.selectOne("getOrder",1);

        Product product = sqlSession.selectOne("listProductId",null);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItemDao.deleteOrderItem(orderItem);
        sqlSession.commit();
    }
}
