package com.ly.dao;

import com.ly.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    /**
     * 查询全部
     * @return
     */
    List<User> getListUser();


    /**
     * 分页查询
     * @param map
     * @return
     */
    List<User> getListUserByLimit(Map<String,Integer> map);

    /**
     * 分页查询 RowBounds方式
     * @return
     */
    List<User> getListUserByRowBounds();
}
