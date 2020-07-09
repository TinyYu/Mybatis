package com.ly.test;

import com.ly.domain.Category;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Category测试类
 */
public class CategoryTest {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 2.然后再根据sqlSessionFactory 得到session
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3.通过session的selectList方法，调用sql语句listCategory
        // listCategory这个就是在配置文件Category.xml中那条sql语句设置的id
        // 执行完毕之后，得到一个Category集合，遍历即可看到数据
        List<Category> cs = sqlSession.selectList("listCategory");
        for (Category c : cs){
            System.out.println(c.getName());
        }

        /**
         * 实现原理
         * 1.程序找mybatis要数据
         * 2.mybatis从数据库中找来数据
         *   2.1.通过mybatis-config.xml定位是那个数据库
         *   2.2.通过CategoryDao.xml执行对应的sql语句
         *   2.3.基于CategoryDao.xml把返回的数据库记录封装到Category对象中
         *   2.4.把多个Category对象装在一个Category集合中
         * 3.返回一个Category集合
         */
    }
}
