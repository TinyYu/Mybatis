package com.ly.dao;

import com.ly.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogDAO {
    // 插入数据
    void addBook(Blog blog);

    // 查询博客
    List<Blog> queryBlogIF(Map map);

    List<Blog> queryBlogChoose(Map map);

    // 更新博客
    void updateBlog(Map map);

    // 查询id为1,2,3记录的博客
    List<Blog> queryBlogForeach(Map map);
}
