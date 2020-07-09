package com.ly.dao;

import com.ly.doamin.Category;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {

    // 一对多
//    @Results 通过@Result和@Many中调用ProductMapper.listByCategory()方法相结合，来获取一对多关系
    @Select("select * from category_")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "products",javaType = List.class,column = "id",many = @Many(select = "com.ly.dao.ProductDao.listByCategory"))
    })
    List<Category> list();


    // 多对一
    // 返回指定的Category
    @Select("select * from category_ where id = #{id}")
    Category getCategory(int id);
}
