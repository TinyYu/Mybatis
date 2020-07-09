package com.ly.test;

import com.ly.dao.CategoryDao;
import com.ly.domain.Category;
import com.ly.domain.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryTest {

    private InputStream inputStream;
    private SqlSession sqlSession;
    private CategoryDao categoryDao;
    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = factory.openSession();
        categoryDao = sqlSession.getMapper(CategoryDao.class);
    }
    @After
    public void destory() throws IOException {
        sqlSession.close();
        inputStream.close();
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void listCategoryByNameTest(){
        List<Category> categoryList = categoryDao.listCategoryByName("%2%");
        for (Category category : categoryList){
            System.out.println(category);
        }
    }

    /**
     * 多条件查询
     */
    @Test
    public void listCategoryByIdAndName(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",2);
        map.put("name","%c%");
        List<Category> categoryList = categoryDao.listCategoryByIdAndName(map);
        for (Category category : categoryList){
            System.out.println(category);
        }
    }

    /**
     * 一对多
     */
    @Test
    public void listCategory(){
        List<Category> categoryList = categoryDao.listCategory();
        for (Category category : categoryList){
            System.out.println(category);
        }
    }
}
