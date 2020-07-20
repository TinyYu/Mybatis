package com.ly.test;

import com.ly.dao.BlogDAO;
import com.ly.pojo.Blog;
import com.ly.utils.IDUtils;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BlogTest {
    private SqlSession sqlSession;
    private BlogDAO blogDAO;
    @Before
    public void init(){
        sqlSession = MybatisUtils.getSqlSession();
        blogDAO = sqlSession.getMapper(BlogDAO.class);
    }
    @After
    public void over(){
        sqlSession.close();
    }

    @Test
    public void queryBlogIF(){
        HashMap map = new HashMap();

        map.put("title","Mybatis如此简单");
        map.put("author","狂神说");
        List<Blog> blogs = blogDAO.queryBlogIF(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();

        SqlSession sqlSession1 = MybatisUtils.getSqlSession();
        BlogDAO blogDAO2 = sqlSession1.getMapper(BlogDAO.class);
        map.put("title","Mybatis如此简单");
        map.put("author","狂神说");
        List<Blog> blogs1 = blogDAO2.queryBlogIF(map);
        for (Blog blog : blogs1) {
            System.out.println(blog);
        }
        System.out.println(blogs == blogs1);
        sqlSession1.close();
    }

    @Test
    public void queryBlogChoose(){
        HashMap map = new HashMap();

        map.put("title","Mybatis如此简单");
        map.put("author","狂神说");

        List<Blog> blogs = blogDAO.queryBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void queryBlogForeach(){
        HashMap map = new HashMap();

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        map.put("ids",strings);
        List<Blog> blogs = blogDAO.queryBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }
}
