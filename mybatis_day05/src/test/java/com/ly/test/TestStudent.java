package com.ly.test;

import com.ly.dao.StudentDAO;
import com.ly.pojo.Student;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestStudent {
    private SqlSession sqlSession;
    private StudentDAO studentDAO;
    @Before
    public void init(){
        sqlSession = MybatisUtils.getSqlSession();
        studentDAO = sqlSession.getMapper(StudentDAO.class);
    }
    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void getListStudent(){
        List<Student> listStudent = studentDAO.getListStudent();
        for (Student student : listStudent) {
            System.out.println(student.toString());
        }

    }
}
