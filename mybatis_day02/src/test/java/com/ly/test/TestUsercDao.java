package com.ly.test;

import com.ly.dao.UsecDAO;
import com.ly.pojo.Userc;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestUsercDao {

    private SqlSession sqlSession;
    private UsecDAO usecDAO;

    @Before
    public void init(){
        sqlSession = new MybatisUtils().getSqlsession();
        usecDAO = sqlSession.getMapper(UsecDAO.class);
    }
    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void listUserc(){
        List<Userc> usercs = usecDAO.getListUserc();
        for (Userc userc : usercs){
            System.out.println(userc.toString());
        }
    }

}
