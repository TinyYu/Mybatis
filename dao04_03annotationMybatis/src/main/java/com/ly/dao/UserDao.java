package com.ly.dao;

import com.ly.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 在mybatis中，CRUD一共有四个注解
 * @Select
 */
public interface UserDao {
    /**
     * 查询所有
     * @return
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user(username,address,sex,birthday) values(#{username},#{address},#{sex},#{birthday})")
    void saveUser(User user);

    /**
     * 删除用户
     * @param id
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);

    /**
     * 修改用户信息
     * @param user
     */
    @Update("update user set username = #{username},address = #{address},sex = #{sex},birthday = #{birthday} where id = #{id}")
    void updateUser(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getUser(Integer id);

    /**
     * 根据用户名模糊查询
     * @param username
     * @return
     */
    @Select("select * from user where username like #{username}")
    List<User> findUserByName(String username);

    /**
     * 查询用户总数量
     * @return
     */
    @Select("select count(*) from user")
    int findToatalUser();
}
