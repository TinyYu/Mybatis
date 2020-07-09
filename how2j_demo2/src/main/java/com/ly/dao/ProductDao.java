package com.ly.dao;

import com.ly.domain.Product;

import java.util.List;

/**
 * product持久层接口
 */
public interface ProductDao {

    /**
     * 多对一
     * @return
     */
    List<Product> listProduct();

    /**
     * 修改数据
     * @param product
     */
    void updateProductCid(Product product);

    /**
     * 指定id查询
     * @param id
     * @return
     */
   Product listProductId(int id);
}
