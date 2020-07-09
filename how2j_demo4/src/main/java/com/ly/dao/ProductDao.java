package com.ly.dao;

import com.ly.domain.Product;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 使用注解方式
 */
public interface ProductDao {
    /**
     * 查询全部
     * @return
     */
    @Select("select * from product_")
    List<Product> listProduct();

    /**
     * 查询指定条件
     * @param id
     * @return
     */
    @Select("select * from product_ where id = #{id}")
    Product getProduct(int id);

    /**
     * 添加
     * @param product
     */
    @Insert("insert into product_ values(null,#{name},#{price},#{cid})")
    void addProduct(Product product);

    /**
     * 删除
     * @param id
     */
    @Delete("delete from product_ where id = #{id}")
    void deleteProduct(int id);

    /**
     * 修改
     * @param product
     */
    @Update("update product_ set name = #{name},price = #{price} where id = #{id}")
    void updateProduct(Product product);
}
