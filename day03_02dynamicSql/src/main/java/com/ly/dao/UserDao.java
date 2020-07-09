package com.ly.dao;

import com.ly.domain.QueryVo;
import com.ly.domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 查询所有用户
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
     * 根据名称模糊查询用户信息
     * @param username
     * @return
     */
    List<User> findByName(String username);

    /**
     * 根据QueryVo查询中的条件查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);

    /**
     * 根据传入参数条件查询
     * @param user 查询的条件: 有可能有用户名、有可能有性别、有可能有地址、还有可能是都有
     * @return
     */
    List<User> findUserByCondition(User user);
}
