package com.ly.test;

import com.ly.dao.CategoryDao;
import com.ly.dao.ProductDao;
import com.ly.doamin.Category;
import com.ly.doamin.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    public void over() throws IOException {
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void listCategory(){
        List<Category> categorys = categoryDao.list();
        for (Category category : categorys){
            System.out.println(category);
            List<Product> products = category.getProducts();
            for (Product product : products){
                System.out.println(product);
            }
        }
    }

}
