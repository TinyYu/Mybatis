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
import java.util.Date;
import java.util.List;

public class UserTest {

    private  InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After // 用于在测试方法执行之后执行
    public void over() throws IOException {
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有、用户总数量
     */
    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        for (User user : users){
            System.out.println("------- 每个用户的信息 -------");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }

    /**
     * 测试根据id查询用户
     */
    @Test
    public void testGetUser(){
        User user = userDao.getUser(54);
        System.out.println(user);

        User user2 = userDao.getUser(54);
        System.out.println(user2);

        System.out.println(user == user2);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testFindUserByName() {
        List<User> users = userDao.findUserByName("%张%");
        for (User user : users){
            System.out.println("------- 每个用户的信息 -------");
            System.out.println(user);
        }
    }
}
