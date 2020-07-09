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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * if判断
     */
    @Test
    public void listProductTest(){
        List<Product> products1 = productDao.listProduct();
        System.out.println("查询全部");
        for (Product product : products1){
            System.out.println(product);
        }

        System.out.println("模糊查询");
        List<Product> products = productDao.listProduct("%a%");
        for (Product product : products){
            System.out.println(product);
        }
    }

    /**
     * where多条件判断
     */
    @Test
    public void listProductsTest(){
        List<Product> products = productDao.listProducts("a",0);
        for (Product product : products){
            System.out.println(product);
        }
    }

    /**
     * set多条件(update)
     */
    @Test
    public void updateProduct(){
        Product product = new Product();
        product.setName("product e");
        product.setPrice((float) 77.77);
        product.setId(1);
        productDao.updateProduct(product);
        sqlSession.commit();
    }

    /**
     * if else
     * when otherwise
     */
    @Test
    public void listProductElseTest(){
        List<Product> products = productDao.listProductElse(null,0);
        for (Product product : products){
            System.out.println(product);
        }
    }

    /**
     * for
     * 通常用于in语法
     *  in常用于where表达式中，其作用是查询某个范围内的数据。
     */
    @Test
    public void listProductForTest(){
        List<Integer> integers = new ArrayList<Integer>();
        integers.add(1);
        integers.add(3);
        integers.add(4);
        List<Product> products = productDao.listProductFor(integers);
        for (Product product : products){
            System.out.println(product);
        }
    }
}
