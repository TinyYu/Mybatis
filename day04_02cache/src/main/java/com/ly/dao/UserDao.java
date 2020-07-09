package com.ly.dao;

import com.ly.domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 查询所有用户,同时获取用户下所有账户的信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);
}
