package com.ly.test;

import com.ly.dao.UserDAO;
import com.ly.pojo.User;
import com.ly.utils.UserUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
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
        List<User> users = userDAO.getListUser();
        for (User user : users){
            System.out.println(user.toString());
        }
    }


    static Logger logger = Logger.getLogger(UserTest.class);
    @Test
    public void testLog4j(){
        logger.info("info:进入了testLog4j");
        logger.debug("debug:进入了testLog4j");
        logger.error("error:进入了testLog4j");
    }

    @Test
    public void getListUserByLimit(){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("startIndex",0);
        map.put("pageSize",3);
        List<User> userList = userDAO.getListUserByLimit(map);
        for (User user : userList) {
            System.out.println(user);
        }
    }


    @Test
    public void getListUserByRowBounds(){
        RowBounds rowBounds = new RowBounds(0, 2);
        List<User> userList = sqlSession.selectList("com.ly.dao.UserDAO.getListUserByRowBounds", null, rowBounds);
        for (User user : userList) {
            System.out.println(user);
        }
    }
}
