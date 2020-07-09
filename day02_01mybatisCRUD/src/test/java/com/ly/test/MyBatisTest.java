package com.ly.test;

import com.ly.dao.UserDao;
import com.ly.domain.QueryVo;
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

public class MyBatisTest {

    private  InputStream in;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @After // 用于在测试方法执行之后执行
    public void destory() throws IOException {
        sqlSession.close();
        in.close();
    }
    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        for (User user : users){
            System.out.println(user);
        }
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("张三");
        user.setAddress("广州");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("保存操作之前:" + user);
        userDao.saveUser(user);
        // 提交事务
        sqlSession.commit();
        System.out.println("保存操作之后:" + user);
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setUsername("张四");
        user.setAddress("广州");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setId(51);

        userDao.updateUser(user);
        sqlSession.commit();
    }

    /**
     * 测试删除操作
     */
    @Test
    public void testDelete(){
        userDao.deleteUser(51);
        sqlSession.commit();
    }

    /**
     * 测试根据id查询用户信息
     */
    @Test
    public void testFindOne(){
        User user = userDao.findById(45);
        System.out.println(user);
    }

    /**
     * 测试根据名称模糊查询用户信息
     */
    @Test
    public void testFindByName(){
        List<User> users = userDao.findByName("%小%");
        for (User user : users){
            System.out.println(user);
        }
    }

    /**
     * 测试查询总用户数
     */
    @Test
    public void testFindTotal(){
        int count = userDao.findTotal();
        System.out.println(count);
    }

    /**
     * 根据QueryVo查询中的条件查询用户
     */
    @Test
    public  void testFindUserByVo(){
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setUsername("%王%");
        queryVo.setUser(user);
        List<User> users = userDao.findUserByVo(queryVo);
        for (User user1 : users){
            System.out.println(user1);
        }
    }
}
