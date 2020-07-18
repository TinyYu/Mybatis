package com.ly.dao;

import com.ly.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDAO {
    @Select("select * from user")
    List<User> getListUser();


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from user where id = #{userId}")
    User getUserById(@Param("userId") Integer id);


    /**
     * 保存数据
     * @param user
     */
    @Insert("insert into user(username,birthday,sex,address) values (#{username},#{birthday},#{sex},#{address})")
    void insertUser(User user);


    /**
     * 更新数据
     * @param user
     */
    @Update("update user set username = #{username},birthday = #{birthday},sex = #{sex},address = #{address} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from user where id = #{id}")
    /**
     * 删除数据id
     * @param id
     */
    void deleteUser(Integer id);
}
