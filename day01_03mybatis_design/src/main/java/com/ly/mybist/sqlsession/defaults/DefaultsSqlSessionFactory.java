package com.ly.mybist.sqlsession.defaults;

import com.ly.mybist.cfg.Configuration;
import com.ly.mybist.sqlsession.SqlSession;
import com.ly.mybist.sqlsession.SqlSessionFactory;

/**
 * SqlSessionFactory接口的实现类
 */
public class DefaultsSqlSessionFactory implements SqlSessionFactory {

    private Configuration cfg;
    public DefaultsSqlSessionFactory(Configuration cfg){
        this.cfg = cfg;
    }
    /**
     * 用于创建一个新的操作数据库对象
     * @return
     */
    public SqlSession openSession() {
        return new DefaultSqlSession(cfg);
    }
}
