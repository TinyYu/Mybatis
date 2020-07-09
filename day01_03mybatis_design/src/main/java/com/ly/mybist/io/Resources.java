package com.ly.mybist.io;

import java.io.InputStream;

/**
 * 使用类加载器读取文件的类
 */
public class Resources {

    /**
     * 根据传入的参数，获取一个字节输入流
     * @param filePath
     * @return
     */
    public static InputStream getResourceAsStream(String filePath){

        /**
         * Resources.class 获取当前类的字节码
         * getClassLoader() 根据字节码获取类加载器
         * getResourceAsStream(filePath) 根据类加载器读取配置
         */
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
