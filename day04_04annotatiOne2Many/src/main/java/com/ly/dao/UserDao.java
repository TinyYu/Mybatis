package com.ly.dao;

import com.ly.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 在mybatis中，CRUD一共有四个注解
 * @Select
 */
@CacheNamespace(blocking = true) //开启二级缓存
public interface UserDao {


    /**
     * 查询所有
     * @return
     */
    @Select("select * from user")
    @Results(id = "userMap",value = {
            @Result(id = true,column = "id",property = "userId"),
            @Result(column = "username",property = "userName"),
            @Result(column = "address",property = "userAddress"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "birthday",property = "userBirthday"),
            @Result(property = "accounts",column = "id",many = @Many(select = "com.ly.dao.AccountDao.findAccountByUid",fetchType = FetchType.LAZY))
    })
    List<User> findAll();

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    @ResultMap(value = {"userMap"})
    User getUser(Integer id);

    /**
     * 根据用户名模糊查询
     * @param username
     * @return
     */
    @Select("select * from user where username like #{username}")
    @ResultMap(value = {"userMap"})
    List<User> findUserByName(String username);
}
