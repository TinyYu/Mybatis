package com.ly.dao.impl;

import com.ly.dao.UserDao;
import com.ly.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private SqlSessionFactory factory;

    public  UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    /**
     * 查询所有
     * @return
     */
    public List<User> findAll() {
        // 1.根据factory获取SqlSession对象
        SqlSession session = factory.openSession();
        // 2.调用SqlSession中的方法，实现查询列表
        List<User> users = session.selectList("com.ly.dao.UserDao.findAll");// 参数就是能获取配置信息的key
        // 3.释放资源
        session.close();
        return users;
    }

    public void saveUser(User user) {
        // 1.根据factory获取SqlSession对象
        SqlSession session = factory.openSession();
        // 2.调用SqlSession中的方法，实现查询列表
        session.selectList("com.ly.dao.UserDao.saveUser",user);// 参数就是能获取配置信息的key
        // 3.提交事务
        session.commit();
        // 5.释放资源
        session.close();
    }

    public void updateUser(User user) {

    }

    public void deleteUser(Integer id) {

    }

    public User findById(Integer id) {
        return null;
    }

    public List<User> findByName(String username) {
        return null;
    }

    public int findTotal() {
        return 0;
    }
}
