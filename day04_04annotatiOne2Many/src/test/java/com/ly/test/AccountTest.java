package com.ly.test;

import com.ly.dao.AccountDao;
import com.ly.dao.UserDao;
import com.ly.domain.Account;
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

public class AccountTest {

    private  InputStream in;
    private SqlSession sqlSession;
    private AccountDao accountDao;

    @Before // 用于在测试方法执行之前执行
    public void init() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        accountDao = sqlSession.getMapper(AccountDao.class);
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
        List<Account> accounts = accountDao.findAll();
        for (Account account : accounts){
            System.out.println("------- 每个账户的信息 -------");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
}
