package com.ly.dao;

import com.ly.pojo.Userc;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsercDaoTest {

    private SqlSession sqlSession;
    private UsercDao usercDao;

    /**
     * 在测试程序之前执行
     */
    @Before
    public void init(){
        // 1. 获取sqlSession对象
        sqlSession = MybatisUtils.getSqlSession();
        // 执行Sql
        usercDao = sqlSession.getMapper(UsercDao.class);
    }

    /**
     * 在测试程序之后执行
     */
    @After
    public void over(){
        // 关闭sqlSession
        sqlSession.close();
    }

    /**
     * 测试查询全部
     */
    @Test
    public void test(){
        List<Userc> usercs = usercDao.getUserList();
        for (Userc userc : usercs){
            System.out.println(userc.toString());
        }
    }

    /**
     * 测试根据id查询
     */
    @Test
    public void test1(){
        Userc userc = usercDao.getUsercById(1);
        System.out.println(userc);
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testInset(){
        Userc userc = new Userc();
        userc.setId(4);
        userc.setName("ccc");
        userc.setPwd("123");
        usercDao.insertUserc(userc);
        // 提交事务
        sqlSession.commit();
    }

    /**
     * 测试修改数据
     */
    @Test
    public void testUpdate(){
        Userc userc = new Userc();
        userc.setId(4);
        userc.setName("eee");
        userc.setPwd("123");
        usercDao.updateUserc(userc);
        sqlSession.commit();
    }

    /**
     * 测试删除数据
     */
    @Test
    public void testDelete(){
        usercDao.deleteUserc(4);
        sqlSession.commit();
    }

    /**
     * 测试使用map作为参数，保存数据
     */
    @Test
    public void testInsert2(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userName","eee");
        map.put("userPwd","123");
        map.put("userId",5);
        usercDao.insertUserc2(map);
        sqlSession.commit();
    }

    /**
     * 模糊查询
     */
    @Test
    public void TestUsercBy(){
        List<Userc> usercs = usercDao.getUsercBy("%e%");
        for (Userc userc : usercs){
            System.out.println(userc.toString());
        }
    }
}
