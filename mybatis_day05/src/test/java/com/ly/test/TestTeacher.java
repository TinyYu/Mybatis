package com.ly.test;

import com.ly.dao.StudentDAO;
import com.ly.dao.TeacherDAO;
import com.ly.pojo.Student;
import com.ly.pojo.Teacher;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestTeacher {
    private SqlSession sqlSession;
    private TeacherDAO teacherDAO;
    @Before
    public void init(){
        sqlSession = MybatisUtils.getSqlSession();
        teacherDAO = sqlSession.getMapper(TeacherDAO.class);
    }
    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void getListStudent(){
        List<Teacher> listTeacher = teacherDAO.getListTeacher();
        for (Teacher teacher : listTeacher) {
            System.out.println(teacher.toString());
        }

    }

    @Test
    public void getTeacherById(){
        Teacher teacher = teacherDAO.getTeacherById(1);
        System.out.println(teacher.toString());

    }
}
