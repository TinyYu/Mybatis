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


public class SecondLevelCacheTest {

    private  InputStream in;
    private SqlSessionFactory factory;


    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
    }

    @After // 用于在测试方法执行之后执行
    public void over() throws IOException {
        in.close();
    }

    /**
     * 测试二级缓存
     *      SqlSessionFactory
     */
    @Test
    public void testFirstLevelCache() {
        SqlSession sqlSession1 = factory.openSession();
        UserDao userDao1 = sqlSession1.getMapper(UserDao.class);
        User user1 = userDao1.findById(41);
        System.out.println(user1);
        sqlSession1.close();  // 一级缓存消失

        SqlSession sqlSession2 = factory.openSession();
        UserDao userDao2 = sqlSession2.getMapper(UserDao.class);
        User user2 = userDao2.findById(41);
        System.out.println(user2);
        sqlSession2.close(); // 一级缓存消失

        System.out.println(user1 == user2);

    }
}
