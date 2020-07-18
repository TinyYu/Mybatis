package com.ly.dao;

import com.ly.pojo.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentDAO {

    /**
     * 查询所有学生信息以及老师的信息
     * @return
     */
    List<Student> getListStudent();
}
