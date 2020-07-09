package com.ly.mybist.sqlsession;

import com.ly.mybist.cfg.Configuration;
import com.ly.mybist.sqlsession.defaults.DefaultsSqlSessionFactory;
import com.ly.mybist.utils.XMLConfigBuilder;

import java.io.InputStream;

/**
 * 用于创建一个SqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {

    /**
     * 根据参数的字节输入流来构建一个SqlSessionFactory工厂
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream){
        Configuration cfg = XMLConfigBuilder.loadConfiguration(inputStream);
        return new DefaultsSqlSessionFactory(cfg);
    }
}
