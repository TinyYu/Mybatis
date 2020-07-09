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
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("mybatis_config.xml");
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
     * 查询全部、查询指定id
     */
    @Test
    public void listProductTest(){
        List<Product> products = productDao.listProduct();
        for (Product product : products){
            System.out.println(product);
        }

        Product product = productDao.getProduct(1);
        System.out.println(product);
    }

    /**
     * 添加
     */
    @Test
    public void addProduct(){
        Product product = new Product();
        product.setName("product a");
        product.setPrice((float) 77.76);
        product.setCid(1);
        productDao.addProduct(product);
        sqlSession.commit();
    }

    /**
     * 删除
     */
    @Test
    public void deleteProductTest(){
        productDao.deleteProduct(7);
        sqlSession.commit();
    }

    /**
     * 修改
     */
    @Test
    public void updateProductTest(){
        Product product = new Product();
        product.setName("product a");
        product.setPrice((float) 77.76);
        product.setCid(1);
        product.setId(1);
        productDao.updateProduct(product);
        sqlSession.commit();
    }
}
