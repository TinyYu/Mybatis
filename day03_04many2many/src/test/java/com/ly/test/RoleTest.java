package com.ly.test;

import com.ly.dao.RoleDao;
import com.ly.dao.UserDao;
import com.ly.domain.Role;
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
import java.util.List;

public class RoleTest {

    private  InputStream in;
    private SqlSession sqlSession;
    private RoleDao roleDao;

    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        roleDao = sqlSession.getMapper(RoleDao.class);
    }

    @After // 用于在测试方法执行之后执行
    public void over() throws IOException {
        sqlSession.close();
        in.close();
    }
    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        List<Role> roles = roleDao.findAll();
        for (Role role : roles) {
            System.out.println("------- 每个用户的信息 -------");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }
}
