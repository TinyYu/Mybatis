package com.ly.dao;

import com.ly.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    List<Product> listProduct();

    /**
     * if判断
     * @param name
     * @return
     */
//  name理应作为key来获取值 @param注解指定name的key
    List<Product> listProduct(@Param("name") String name);


    /**
     * 多条件判断
     * @return
     */
    List<Product> listProducts();
    List<Product> listProducts(@Param("name") String name,@Param("price") float price);

    /**
     * update下多条件判断
     * @param product
     */
    void updateProduct(Product product);

    /**
     * if else
     * when otherwise
     * @param name
     * @param price
     * @return
     */
    List<Product> listProductElse(@Param("name") String name,@Param("price") float price);

    /**
     * for
     * 查询多个指定id的数据
     * @param productId
     * @return
     */
    List<Product> listProductFor(@Param("productId")List<Integer> productId);
}
