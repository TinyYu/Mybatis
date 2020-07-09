package com.ly.test;

import com.ly.dao.ProductDao;
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
import java.util.List;

public class ProductTest {

    private InputStream inputStream;
    private SqlSession sqlSession;
    private ProductDao productDao;

    @Before
    public void inti() throws IOException {
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

    /**
     * 多对一
     */
    @Test
    public void listProductTest(){
        List<Product> products = productDao.listProduct();
        for (Product product : products){
            System.out.println(product);
        }
    }

    @Test
    public void updateProductCidTest(){
        Product product = new Product();
        product.setId(5);
        product.setCid(1);
        productDao.updateProductCid(product);
        sqlSession.commit();
    }

    @Test
    public void listProductIdTest(){
        Product products = productDao.listProductId(6);
        System.out.println(products);
    }
}
