package com.ly.dao;

import com.ly.domain.Category;

import java.util.List;
import java.util.Map;

/**
 * Category持久层接口
 */
public interface CategoryDao {

    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<Category> listCategoryByName(String name);

    /**
     * 多条件查询
     * @param map
     * @return
     */
    List<Category> listCategoryByIdAndName(Map<String,Object> map);

    /**
     * 一对多
     * @return
     */
    List<Category> listCategory();
}
