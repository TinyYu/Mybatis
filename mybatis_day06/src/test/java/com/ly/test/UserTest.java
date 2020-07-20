package com.ly.test;

import com.ly.dao.UserDAO;
import com.ly.pojo.User;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {
    private SqlSession sqlSession;
    private UserDAO userDAO;
    @Before
    public void init(){
        sqlSession = MybatisUtils.getSqlSession();
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
            System.out.println(user.toString());
        }
    }
}
