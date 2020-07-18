package com.ly.dao;

import com.ly.pojo.Teacher;

import java.util.List;

public interface TeacherDAO {
    List<Teacher> getListTeacher();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Teacher getTeacherById(Integer id);
}
