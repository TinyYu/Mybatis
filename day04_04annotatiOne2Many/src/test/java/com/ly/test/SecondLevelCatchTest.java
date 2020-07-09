package com.ly.test;

import com.ly.dao.UserDao;
import com.ly.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class SecondLevelCatchTest {
    private InputStream in;
    private SqlSession sqlSession;
    private SqlSessionFactory factory;
    private UserDao userDao;

    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After // 用于在测试方法执行之后执行
    public void over() throws IOException {
        sqlSession.close();
        in.close();
    }

    @Test
    public void testGetUser(){
        User user = userDao.getUser(54);
        System.out.println(user);

        sqlSession.close(); // 释放一级缓存

        SqlSession session1 = factory.openSession();// 再次打开session
        userDao = session1.getMapper(UserDao.class);
        User user1 = userDao.getUser(54);
        System.out.println(user1);

        sqlSession.close(); // 释放一级缓存
    }
}
