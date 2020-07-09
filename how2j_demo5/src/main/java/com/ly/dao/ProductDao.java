package com.ly.dao;

import com.ly.doamin.Product;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao {

    /**
     * 指定cid的数据
     * @param cid
     * @return
     */
    @Select("select * from product_ where cid = #{cid}")
    List<Product> listByCategory(int cid);

    // 多对一
    @Select("select * from product_")
    @Results({
            @Result(property = "category",column="cid",one = @One(select = "com.ly.dao.CategoryDao.getCategory"))
    })
    List<Product> list();
}
