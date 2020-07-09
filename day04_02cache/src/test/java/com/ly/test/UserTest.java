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


public class UserTest {

    private  InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After // 用于在测试方法执行之后执行
    public void over() throws IOException {
        sqlSession.close();
        in.close();
    }

    /**
     * 测试一级缓存
     *      SqlSession
     */
    @Test
    public void testFirstLevelCache() {
        User user1 = userDao.findById(41);
        System.out.println(user1);

//        // s关闭sqlSession对象,缓存消失
//        sqlSession.close();
//        // 再次获取sqlSession对象
//        sqlSession = factory.openSession();

        sqlSession.clearCache(); //此方法也可以清空缓存
        userDao = sqlSession.getMapper(UserDao.class);

        User user2 = userDao.findById(41);
        System.out.println(user2);

        System.out.println(user1 == user2);
    }

    /**
     * 测试缓存的同步
     */
    @Test
    public void testClearCache() {
        // 1. 根据id查询用户
        User user1 = userDao.findById(41);
        System.out.println(user1);

        // 一级缓存时SqlSession范围的缓存，当调用SqlSession的修改，添加，删除，commit(),close()等方法时，就会清空一级缓存。
        // 2.跟新用户信息
        user1.setUsername("update user clear cache");
        user1.setAddress("四川内江");
        userDao.updateUser(user1);

        User user2 = userDao.findById(41);
        System.out.println(user2);

        System.out.println(user1 == user2);
    }
}
