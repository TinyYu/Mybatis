package com.ly.test;

import com.ly.dao.OrderDao;
import com.ly.domain.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OrderTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private OrderDao orderDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = factory.openSession();
        orderDao = sqlSession.getMapper(OrderDao.class);
    }

    @After
    public void over() throws IOException {
        sqlSession.close();
        inputStream.close();
    }

    /**
     * 测试查询全部
     */
    @Test
    public void listOrderTest(){
        List<Order> orders = orderDao.listOrder();
        for (Order order : orders){
            System.out.println(order);
        }
    }

    /**
     * 测试指定条件查询
     */
    @Test
    public void getOrderTest(){
        Order order = orderDao.getOrder(2);
        System.out.println(order);
    }

    @Test
    public void deleteOrderTest(){
        orderDao.deleteOrder(1);
        sqlSession.commit();
    }
}
