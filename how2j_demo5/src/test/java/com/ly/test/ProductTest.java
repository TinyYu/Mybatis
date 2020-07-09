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

public class ProductTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private ProductDao productDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = factory.openSession();
        productDao = sqlSession.getMapper(ProductDao.class);
    }

    @After
    public void over() throws IOException {
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void list(){
        List<Product> products = productDao.list();
        for (Product product : products){
            System.out.println(product + " : " + product.getCategory());
        }
    }

}
