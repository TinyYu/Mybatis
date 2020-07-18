package com.ly.test;

import com.ly.dao.UserDAO;
import com.ly.pojo.User;
import com.ly.utils.UserUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class UserTest {
    private SqlSession sqlSession;
    private UserDAO userDAO;
    @Before
    public void init(){
        sqlSession = UserUtils.getSqlSession();
        userDAO = sqlSession.getMapper(UserDAO.class);
    }
    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void getListUser(){
        List<User> listUser = userDAO.getListUser();
        for (User user : listUser) {
            System.out.println(user);
        }
    }

    @Test
    public void getUserById(){
        User user = userDAO.getUserById(54);
        System.out.println(user);
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setUsername("小王");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("四川");
        user.setId(54);
        userDAO.updateUser(user);
        sqlSession.commit();
    }

    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("小王");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("四川");
        userDAO.insertUser(user);
        sqlSession.commit();
    }

    @Test
    public void deleteUser(){
        userDAO.deleteUser(56);
        sqlSession.commit();
    }
}
