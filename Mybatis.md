[toc]

# Mybatis

## 1.Mybatis入门 -- MAVEN工程

### 1.1 导入依赖

~~~xml
<!-- mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.5</version>
</dependency>

<!-- mysql -->
<dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>5.1.6</version>
</dependency>
~~~

### 1.2 创建mybatis-config.xm核心文件 -- resources->mybatis-config.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!-- configuration核心配置文件 -->
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;userUnicode=true&amp;characterEncoding=UTF-8"/>
                <property name="username" value="admin"/>
                <property name="password" value="1999"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
~~~

### 1.3 创建mybatis工具类 -- com->ly->utils->MybatisUtils.java

~~~java
package com.ly.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static{
        try {
            // 获取sqlSessionFactory对象
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
~~~

### 1.4 创建一个实体类 (成员名和mysql表中列名相同) -- com->ly->pojo->Userc

~~~java
package com.ly.dao;

public class Userc {
    private Integer id;
    private String name;
    private String pwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
~~~

### 1.5创建持久层接口 -- com.ly.dao.UsercDao

UsercDao

~~~java
package com.ly.dao;

import com.ly.pojo.Userc;

import java.util.List;

public interface UsercDao {
    List<Userc> getUserList();
}
~~~

### 1.6创建Dao配置文件 -- resources->com->ly->dao->UserMapper.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 绑定对应的Dao/Mapper接口 -->
<mapper namespace="com.ly.dao.UsercDao">
    <!-- 查询语句 -->
   <select id="getUserList" resultType="com.ly.pojo.Userc">
       select * from userc
   </select>
</mapper>
~~~

### 1.7测试

注意点 :

​	org.apache.ibatis.binding.BindingException: Type interface com.ly.dao.UsercDao is not known to the MapperRegistry.

​	解决 : 核心文件中注册mapper

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">

        <!-- configuration核心配置文件 -->
<configuration>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=UTF-8"/>
                <property name="username" value="admin"/>
                <property name="password" value="1999"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 每一个Mapper.xml都需要在mybatis核心配置文件中注册 -->
    <mappers>
        <mapper resource="com/ly/dao/UserMapper.xml"></mapper>
    </mappers>

</configuration>
~~~

测试类 -- test->com->ly->test->UsercDaoTest.java

~~~java
package com.ly.dao;

import com.ly.pojo.Userc;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UsercDaoTest {
    @Test
    public void test(){
        // 1. 获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        // 执行Sql
        UsercDao usercDao = sqlSession.getMapper(UsercDao.class);
        List<Userc> usercs = usercDao.getUserList();
        for (Userc userc : usercs){
            System.out.println(userc.toString());
        }

        // 关闭sqlSession
        sqlSession.close();
    }
}
~~~

## 2.CRUD

### 2.1 持久层接口中添加方法

~~~java
package com.ly.dao;

import com.ly.pojo.Userc;

import java.util.List;

public interface UsercDao {
    /**
     * 查询所有
     * @return
     */
    List<Userc> getUserList();


    /**
     * 根据id查询
     * @param id
     * @return
     */
    Userc getUsercById(Integer id);

    /**
     * 保存数据
     * @param userc
     */
    void insertUserc(Userc userc);

    /**
     * 更新数据
     * @param userc
     */
    void updateUserc(Userc userc);

    /**
     * 删除数据
     * @param id
     */
    void deleteUserc(Integer id);
}
~~~

### 2.2 编写SQL语句

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 绑定对应的Dao/Mapper接口 -->
<mapper namespace="com.ly.dao.UsercDao">
    <!-- 查询语句 -->
    <select id="getUserList" resultType="com.ly.pojo.Userc">
       select * from userc
    </select>

    <!-- 根据id查询
        resultType ：返回类型
        parameterType : 传入参数类型
    -->
    <select id="getUsercById" resultType="com.ly.pojo.Userc" parameterType="Integer">
        select * from userc where id = #{id}
    </select>

    <!-- 保存数据 -->
    <insert id="insertUserc" parameterType="com.ly.pojo.Userc">
        insert into userc(id,name,pwd) values (#{id},#{name},#{pwd})
    </insert>

    <!-- 修改数据 -->
    <update id="updateUserc" parameterType="com.ly.pojo.Userc">
        update userc set name = #{name},pwd = #{pwd} where id = #{id}
    </update>

    <!-- 删除数据 -->
    <delete id="deleteUserc" parameterType="Integer">
        delete from userc where id = #{id}
    </delete>
</mapper>
~~~

### 2.3 编写测试

~~~java
package com.ly.dao;

import com.ly.pojo.Userc;
import com.ly.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UsercDaoTest {

    private SqlSession sqlSession;
    private UsercDao usercDao;

    /**
     * 在测试程序之前执行
     */
    @Before
    public void init(){
        // 1. 获取sqlSession对象
        sqlSession = MybatisUtils.getSqlSession();
        // 执行Sql
        usercDao = sqlSession.getMapper(UsercDao.class);
    }

    /**
     * 在测试程序之后执行
     */
    @After
    public void over(){
        // 关闭sqlSession
        sqlSession.close();
    }

    /**
     * 测试查询全部
     */
    @Test
    public void test(){
        List<Userc> usercs = usercDao.getUserList();
        for (Userc userc : usercs){
            System.out.println(userc.toString());
        }
    }

    /**
     * 测试根据id查询
     */
    @Test
    public void test1(){
        Userc userc = usercDao.getUsercById(1);
        System.out.println(userc);
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testInset(){
        Userc userc = new Userc();
        userc.setId(4);
        userc.setName("ccc");
        userc.setPwd("123");
        usercDao.insertUserc(userc);
        // 提交事务
        sqlSession.commit();
    }

    /**
     * 测试修改数据
     */
    @Test
    public void testUpdate(){
        Userc userc = new Userc();
        userc.setId(4);
        userc.setName("eee");
        userc.setPwd("123");
        usercDao.updateUserc(userc);
        sqlSession.commit();
    }

    /**
     * 测试删除数据
     */
    @Test
    public void testDelete(){
        usercDao.deleteUserc(4);
        sqlSession.commit();
    }
}
~~~

### 2.4 使用Map作为参数

#### 说明

​	如果实体类 / 数据库中的表，字段或者参数过多，应当考虑使用Map

#### 2.4.1 在DAO接口中添加方法

~~~java
	/**
     * 使用Map作为参数
     * @param map
     * @return
     */
    void insertUserc2(Map<String,Object> map);
~~~

#### 2.4.2 配置DAO的xml文件

~~~xml
<!-- 使用Map作为参数
        传递Map里面的key
     -->
    <insert id="insertUserc2" parameterType="map">
        insert into userc(id, name, pwd) values (#{userId},#{userName},#{userPwd})
    </insert>

~~~

#### 2.4.3 测试

~~~java
/**
     * 测试使用map作为参数，保存数据
     */
    @Test
    public void testInsert2(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userName","eee");
        map.put("userPwd","123");
        map.put("userId",5);
        usercDao.insertUserc2(map);
        sqlSession.commit();
    }
~~~

#### 总结

Map传递参数，直接在sql中取出key

对象传递参数，直接在sql中取出对象的属性

只有一个基本类型参数的情况下，可以直接在sql中取到

多个参数使用Map，或者注解

#### 扩展模糊查询

1. 在Java代码中使用通配符

   ~~~java
    List<Userc> usercs = usercDao.getUsercBy("%e%")
   ~~~

   

2. 在sql拼接中使用通配符

   ~~~xml
    select * from userc where name like "%"#{name}"%"
   ~~~

   

## 3.配置解析

### 3.1 核心配置文件

+ mybatis-config.xml

  ~~~xm
  configuration（配置）          
  - properties（属性）
  - settings（设置）
  - typeAliases（类型别名）
  - typeHandlers（类型处理器）
  - objectFactory（对象工厂）
  - plugins（插件）
  - environments（环境配置）
    - environment（环境变量）                  
    - transactionManager（事务管理器）
    - dataSource（数据源）
  - databaseIdProvider（数据库厂商标识）
  - mappers（映射器）
  ~~~

### 3.2 环境配置 -- configuration

MyBatis可以配置成适应多种环境

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!-- 多种环境配置 -->
    <environments default="mysql"> <!-- 通过id选择使用的环境 -->
        <environment id="mysql">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=UTF-8"/>
                <property name="username" value="admin"/>
                <property name="password" value="1999"/>
            </dataSource>
        </environment>
        
         <environment id="mysql2">
            <transactionManager type="JDBC"/><!-- Mybatis默认的事务管理器JDBC -->
            <dataSource type="POOLED"><!-- mybatis默认连接池:POOLED -->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=UTF-8"/>
                <property name="username" value="admin"/>
                <property name="password" value="1999"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>
    </mappers>
</configuration>
~~~

**注意**：但是SqlSessionFactory实例只能选择一种环境。也就是environments中的default属性只能选择一个id

### 3.3 properties（属性）

使用外部的Java属性文件，配置properties

+ 创建properties文件 -- resources->db.properties

  ~~~properties
  dirver=com.mysql.jdbc.Driver
  url=jdbc:mysql://localhost:3306/mybatis?characterEncoding=UTF-8
  username=admin
  password=1999
  ~~~

+ 在核心配置文件中映射properties文件

  ~~~xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <properties resource="db.properties"></properties><!-- 引入properties文件(必须在) -->
      <environments default="mysql">
          <environment id="mysql">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="${dirver}"/>
                  <property name="url" value="${url}"/>
                  <property name="username" value="${username}"/>
                  <property name="password" value="${password}"/>
              </dataSource>
          </environment>
      </environments>
  
      <mappers>
          <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>
      </mappers>
  </configuration>
  ~~~

  + 可以直接引入外部配置文件

  + 可以在其中增加属性设置

    ~~~xml
    <properties resource="db.properties">
    	<property name="username" value="admin"/>
        <property name="password" value="1999"
    </properties>
    ~~~

  + 如果同时使用外部文件、增加属性两种配置，优先使用外部配置文件

### 3.4  typeAliases（类型别名）

+ 降低冗余的全限定类名书写

  + 第一种方式

    当实体类少时，建议使用，可以自定义别名

  ~~~xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <properties resource="db.properties"></properties>
  
      <!-- 给实体类起别名 -->
      <typeAliases>
          <typeAlias type="com.ly.pojo.Userc" alias="Userc"/>
      </typeAliases>
  
      <environments default="mysql">
          <environment id="mysql">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="${dirver}"/>
                  <property name="url" value="${url}"/>
                  <property name="username" value="${username}"/>
                  <property name="password" value="${password}"/>
              </dataSource>
          </environment>
      </environments>
  
      <mappers>
          <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>
      </mappers>
  </configuration>
  
  <!-- 在UsercDAO.xml中使用配置的实体类别名 -->
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.ly.dao.UsecDAO">
      <select id="getListUserc" resultType="Userc"><!-- 使用别名 -->
          select * from userc
      </select>
  </mapper>
  ~~~

  + 通过扫描包的方式

    当实体类多的时候，建议使用，但不能自定以别名。

    如果需要自定以别名需要在实体类上使用注解@Alias()

    ~~~java
    @Alias("user")
    public class Userc 
    ~~~

    

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
    
        <properties resource="db.properties"></properties>
    
        <!-- 给实体类起别名 -->
        <typeAliases>
    <!--        <typeAlias type="com.ly.pojo.Userc" alias="Userc"/>-->
            <!-- 扫描包 -->
            <package name="com.ly.pojo"/>
        </typeAliases>
    
        <environments default="mysql">
            <environment id="mysql">
                <transactionManager type="JDBC"/>
                <dataSource type="POOLED">
                    <property name="driver" value="${dirver}"/>
                    <property name="url" value="${url}"/>
                    <property name="username" value="${username}"/>
                    <property name="password" value="${password}"/>
                </dataSource>
            </environment>
        </environments>
    
        <mappers>
            <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>
        </mappers>
    </configuration>
    
    <!-- 在UsercDAO.xml中使用配置的实体类别名 -->
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.ly.dao.UsecDAO">
        <select id="getListUserc" resultType="userc"><!-- 使用别名,默认别名就是实体类的类名首字母小写 -->
            select * from userc
        </select>
    </mapper>
    ~~~

### 3.5 settings（设置）

| logImpl            | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        |
| ------------------ | ------------------------------------------------------------ |
| lazyLoadingEnabled | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。                特定关联关系中可通过设置 `fetchType`                属性来覆盖该项的开关状态。 |
| cacheEnabled       | 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。     |

### 3.6 其他配置

+ typeHandlers（类型处理器）
+ objectFactory（对象工厂）
+ plugins（插件）
  * mybatis-generator-core
  * mybatis-plus
  * 通用mapper

### 3.7 mappers（映射器）

MapperRegistry:注册绑定Mapper文件

方式1：(推荐使用)

~~~xml
<!--  -->  
<mappers>
        <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>
    </mappers>
~~~

方式2:使用class文件绑定注册

~~~xml
 <mappers>
<!--        <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>-->
        <mapper class="com.ly.dao.UsercDao"></mapper>
    </mappers>
~~~

注意点:

+ 接口和他的Mapper配置文件必须同名
+ 接口和他的Mapper配置文件必须在同一个包下

方式3：使用扫描

~~~xml
 <mappers>
<!--        <mapper resource="com/ly/dao/UsercDAO.xml"></mapper>-->
        <package name="com.ly.dao"/>
    </mappers>
~~~

注意点:

+ 接口和他的Mapper配置文件必须同名
+ 接口和他的Mapper配置文件必须在同一个包下

### 3.8 生命周期和作用域

生命周期和作用域是至关重要的，错误的使用会导致非常严重的并发问题

![mybatis](C:\Users\22978\Pictures\mybatis.png)

**SqlSessionFactoryBuilder**：

+ 一旦创建SqlSessionFactoryBuilder，就不在需要它了
+ 局部变量

**SqlSessionFactory**：

+ 可以想象为：数据库连接池
+ SqlSessionFactory一旦被创建就应该在应用的运行期间一直存在，**没有任何理由丢弃它或者重新创建另一个实例**
+ SqlSessionFactory的最佳作用域是应用领域
+ 最简单的就是使用单例模式或者**静态单例模式**

**SqlSession**：

+ 连接到连接池的一个请求

+ SqlSession的实例不是线程安全的，不能被共享，最佳作用域是请求或者方法作用域

+ 用完之后需要关闭，否则资源被占用

  ![image-20200718033321215](C:\Users\22978\AppData\Roaming\Typora\typora-user-images\image-20200718033321215.png)

+ 这里面的每一个Mapper就相当于一个业务



## 4.解决属性名和字段名不一致

### 4.1 在sql中取别名

~~~xml
<!-- 别名和属性名相同 -->
<select id="getListUser" resultType="user">
        select id,username,sex as userSex,birthday as userBirthday,address as userAddress from user
    </select>
~~~

### 4.2 使用resultMap

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ly.dao.UserDAO">

    <!-- 结果集映射 -->
    <resultMap id="userMap" type="user">
        <!-- column: 数据库列名 property: 实体类属性名-->
        <result column="id" property="id"/>
        <result column="username" property="userName"/>
        <result column="sex" property="userSex"/>
        <result column="birthday" property="userBirthday"/>
        <result column="address" property="userAddress"/>
    </resultMap>

    <select id="getListUser" resultMap="userMap">
        select * from user
    </select>
</mapper>
~~~

## 5.日志

### 5.1 日志工厂

+ logImpl
  + SLF4J
  + LOG4J
  + LOG4J2
  + JDK_LOGGING
  + COMMONS_LOGGING 
  + STDOUT_LOGGING 
  + NO_LOGGING

~~~xml
<!-- 在核心配置文件中配置日志工厂 -->
<settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
~~~

### 5.2 LOG4J

1. 导入LOG4J包

   ~~~xml
     <dependency>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
               <version>1.2.12</version>
           </dependency>
   ~~~

2. 创建配置文件log4j.properties

   ~~~properties
   ### 配置根 ###
   log4j.rootLogger = DEBUG,console,file
   
   ### 设置输出sql的级别，其中logger后面的内容全部为jar包中所包含的包名 ###
   log4j.logger.org.mybatis=DEBUG
   log4j.logger.java.sql=DEBUG
   log4j.logger.java.sql.Statement=DEBUG
   log4j.logger.java.sql.PreparedStatement=DEBUG
   log4j.logger.java.sql.ResultSet=DEBUG
   
   ### 配置输出到控制台 ###
   log4j.appender.console = org.apache.log4j.ConsoleAppender
   log4j.appender.console.Target = System.out
   log4j.appender.console.Threshold=DEBUG
   log4j.appender.console.layout = org.apache.log4j.PatternLayout
   log4j.appender.console.layout.ConversionPattern =  [%c]-%m%n
   
   ### 配置输出到文件 ###
   log4j.appender.file = org.apache.log4j.RollingFileAppender
   log4j.appender.file.File = ./logs/log.log
   log4j.appender.file.MaxFileSize = 10mb
   log4j.appender.file.Threshold = DEBUG
   log4j.appender.file.layout = org.apache.log4j.PatternLayout
   log4j.appender.file.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
   ~~~

3. 配置log4j为日志的实现

   ~~~xml
    <settings>
           <setting name="logImpl" value="LOG4J"/>
       </settings>
   ~~~

   简单使用

   + 创建Logger对象,参数为当前类的class

     ~~~java
     static Logger logger = Logger.getLogger(UserTest.class);
     ~~~

   + 日志级别

     ~~~java
     logger.info("info:进入了testLog4j");
     logger.debug("debug:进入了testLog4j");
     logger.error("error:进入了testLog4j");
     ~~~

## 6.分页

### 6.1 使用Limit分页

~~~sql
select * from user limit startIndex,pageSize;
select * from user limit 3; [0,n] // 从0开始查询3条数据
~~~

 **Mybatis实现分页**

1. 接口

   ```java
   List<User> getListUserByLimit(Map<String,Integer> map);
   ```

2. Mapper

   ```xml
   <select id="getListUserByLimit" parameterType="map" resultMap="userMap">
       select * from user limit #{startIndex},#{pageSize}
   </select>
   ```

3. 测试

   ```java
   @Test
   public void getListUserByLimit(){
       HashMap<String, Integer> map = new HashMap<String, Integer>();
       map.put("startIndex",0);
       map.put("pageSize",3);
       List<User> userList = userDAO.getListUserByLimit(map);
       for (User user : userList) {
           System.out.println(user);
       }
   }
   ```

### 6.2 RowBounds实现分页

​	不使用SQL实现分页	

 1. 接口

    ```java
    List<User> getListUserByRowBounds();
    ```

 2. Mapper

    ```xml
    <select id="getListUserByRowBounds" resultMap="userMap">
        select * from user
    </select>
    ```

 3. 测试

    ```java
    @Test
    public void getListUserByRowBounds(){
        RowBounds rowBounds = new RowBounds(0, 2);
        List<User> userList = sqlSession.selectList("com.ly.dao.UserDAO.getListUserByRowBounds", null, rowBounds);
        for (User user : userList) {
            System.out.println(user);
        }
    }
    ```

## 7.注解开发

1. 注解在接口中实现SQL语句

   ```java
   @Select("select * from user")
   List<User> getListUser();
   ```

2. 核心文件绑定接口

   ```xml
   <mappers>
           <!-- 也可以使用扫描包的方式 -->
   <!--        <package name="com.ly.dao"/>-->
           <mapper class="com.ly.dao.UserDAO"></mapper>
       </mappers>
   ```

3. 测试

   ```java
   @Test
   public void getListUser(){
       List<User> listUser = userDAO.getListUser();
       for (User user : listUser) {
           System.out.println(user);
       }
   }
   ```

### 7.1 CRUD

1. 接口

   ```java
   package com.ly.dao;
   
   import com.ly.pojo.User;
   import org.apache.ibatis.annotations.Delete;
   import org.apache.ibatis.annotations.Insert;
   import org.apache.ibatis.annotations.Select;
   import org.apache.ibatis.annotations.Update;
   
   import java.util.List;
   
   public interface UserDAO {
       @Select("select * from user")
       List<User> getListUser();
   
   
       /**
        * 根据id查询
        * @param id
        * @return
        */
       @Select("select * from user where id = #{id}")
       User getUserById(Integer id);
   
   
       /**
        * 保存数据
        * @param user
        */
       @Insert("insert into user(username,birthday,sex,address) values (#{username},#{birthday},#{sex},#{address})")
       void insertUser(User user);
   
   
       /**
        * 更新数据
        * @param user
        */
       @Update("update user set username = #{username},birthday = #{birthday},sex = #{sex},address = #{address} where id = #{id}")
       void updateUser(User user);
   
       @Delete("delete from user where id = #{id}")
       /**
        * 删除数据id
        * @param id
        */
       void deleteUser(Integer id);
   }
   ```

2. 注册绑定配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   
   <configuration>
       <properties resource="db.properties"></properties>
       <settings>
           <setting name="logImpl" value="STDOUT_LOGGING"/>
       </settings>
       <environments default="mysql">
           <environment id="mysql">
               <transactionManager type="JDBC"></transactionManager>
               <dataSource type="POOLED">
                   <property name="driver" value="${dirver}"/>
                   <property name="url" value="${url}"/>
                   <property name="username" value="${username}"/>
                   <property name="password" value="${password}"/>
               </dataSource>
           </environment>
       </environments>
       <mappers>
           <!-- 也可以使用扫描包的方式 -->
   <!--        <package name="com.ly.dao"/>-->
           <mapper class="com.ly.dao.UserDAO"></mapper>
       </mappers>
   </configuration>
   ```

3. 测试

   ```java
   package com.ly.test;
   
   import com.ly.dao.UserDAO;
   import com.ly.pojo.User;
   import com.ly.utils.UserUtils;
   import org.apache.ibatis.session.SqlSession;
   import org.junit.After;
   import org.junit.Before;
   import org.junit.Test;
   
   import java.util.Date;
   import java.util.List;
   
   public class UserTest {
       private SqlSession sqlSession;
       private UserDAO userDAO;
       @Before
       public void init(){
           sqlSession = UserUtils.getSqlSession();
           userDAO = sqlSession.getMapper(UserDAO.class);
       }
       @After
       public void over(){
           sqlSession.close();
       }
   
       @Test
       public void getListUser(){
           List<User> listUser = userDAO.getListUser();
           for (User user : listUser) {
               System.out.println(user);
           }
       }
   
       @Test
       public void getUserById(){
           User user = userDAO.getUserById(54);
           System.out.println(user);
       }
   
       @Test
       public void updateUser(){
           User user = new User();
           user.setUsername("小王");
           user.setBirthday(new Date());
           user.setSex("男");
           user.setAddress("四川");
           user.setId(54);
           userDAO.updateUser(user);
           sqlSession.commit();
       }
   
       @Test
       public void insertUser(){
           User user = new User();
           user.setUsername("小王");
           user.setBirthday(new Date());
           user.setSex("男");
           user.setAddress("四川");
           userDAO.insertUser(user);
           sqlSession.commit();
       }
   
       @Test
       public void deleteUser(){
           userDAO.deleteUser(56);
           sqlSession.commit();
       }
   }
   ```

### 7.2 关于@Param()注解

+ 基本类型的参数或者String类型，需要加上

+ 引用类型不需要

+ 如果只有一个基本类型，可以忽略

+ 在SQL引用中使用@Param()中设定的属性名

  如:

  ```java
  /**
   * 根据id查询
   * @param id
   * @return
   */
  @Select("select * from user where id = #{userId}")
  User getUserById(@Param("userId") Integer id);
  ```

## 8.Lombok

使用步骤

1. 在IDEA中安装Lombok插件

2. 在项目中导入Lombok jar包

   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.10</version>
   </dependency>
   ```

3. Lombok中的注解,在实体类上添加注解

   ~~~java
   @Data: 无参构造、get、set、toString、hashcode、equals
   @AllArgsConstructor: 有参构造
   @NoArgsConstructor: 无参构造
   ~~~

## 9.多对一处理

![多对一](F:\exploit\Trchnology\ssm\mybatis\多对一.jpg)

+ 多个学生对应一个老师
+ 对于学生而言，关联..  多个学生，关联一个老师【多对一】
+ 对于老师而言, 集合，一个老师有很多个学生【一对多】

### 9.1 环境搭建

1. 创建关联的两个表

   ~~~sql
   CREATE TABLE teacher(
   	id int(10) not null,
   	name varchar(30) DEFAULT null,
   	primary key(id)
   )engine=innodb default charset=utf8
   insert into teacher(id,name) values (1,'刘老师')
   
   create table student(
   	id int(10) not null,
   	name varchar(30) default null,
   	tid int(10) default null,
   	primary key(id),
   	key fktid (tid),
   	constraint fktid FOREIGN key (tid) REFERENCES teacher (id)
   )engine=innodb default charset=utf8
   INSERT into student(id,name,tid) values (1,'小明',1),(2,'小王',1),(3,'小红',1)
   ~~~

2. 创建实体类

   student

   ```java
   package com.ly.pojo;
   
   public class Student {
       private Integer id;
       private String name;
   
       // 学生需要关联一个老师
       private Teacher teacher;
   
       public Integer getId() {
           return id;
       }
   
       public void setId(Integer id) {
           this.id = id;
       }
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public Teacher getTeacher() {
           return teacher;
       }
   
       public void setTeacher(Teacher teacher) {
           this.teacher = teacher;
       }
   
       @Override
       public String toString() {
           return "Student{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   ", teacher=" + teacher +
                   '}';
       }
   }
   ```

   teacher

   ```java
   package com.ly.pojo;
   
   import java.util.List;
   
   public class Teacher {
       private Integer id;
       private String name;
   
       // 一个老师有多个学生
       private List<Student> students;
   
       public Integer getId() {
           return id;
       }
   
       public void setId(Integer id) {
           this.id = id;
       }
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public List<Student> getStudents() {
           return students;
       }
   
       public void setStudents(List<Student> students) {
           this.students = students;
       }
   
       @Override
       public String toString() {
           return "Teacher{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   ", students=" + students +
                   '}';
       }
   }
   ```

3. DAO接口

   ```java
   // Student
   package com.ly.dao;
   
   import com.ly.pojo.Student;
   import org.apache.ibatis.annotations.Select;
   
   import java.util.List;
   
   public interface StudentDAO {
   
       List<Student> getListStudent();
   }
   
   // teacher
   package com.ly.dao;
   
   import com.ly.pojo.Teacher;
   
   import java.util.List;
   
   public interface TeacherDAO {
       List<Teacher> getListTeacher();
   }
   
   ```

4. db.properties外部配置文件(和上面的配置文件相同)、核心配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <properties resource="db.properties"/>
       
       <typeAliases>
           <package name="com.ly.pojo"/>
       </typeAliases>
   
       <environments default="mybatis">
           <environment id="mybatis">
               <transactionManager type="JDBC"></transactionManager>
               <dataSource type="POOLED">
                   <property name="driver" value="${dirver}"/>
                   <property name="url" value="${url}"/>
                   <property name="username" value="${username}"/>
                   <property name="password" value="${password}"/>
               </dataSource>
           </environment>
       </environments>
   
       <mappers>
           <package name="com.ly.dao"/>
       </mappers>
   </configuration>
   ```

5. Mapper配置文件

   ```xml
   <!-- student -->
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.ly.dao.StudentDAO">
       <select id="getListStudent" resultType="student">
           select * from student
       </select>
   </mapper>
   
   <!-- teacher -->
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.ly.dao.TeacherDAO">
       <select id="getListTeacher" resultType="teacher">
           select * from teacher
       </select>
   </mapper>
   ```

6. 工具类

   ```java
   package com.ly.utils;
   
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   
   import java.io.IOException;
   import java.io.InputStream;
   
   public class MybatisUtils {
       private static SqlSessionFactory sqlSessionFactory;
       private static InputStream inputStream;
       static {
           try {
               inputStream = Resources.getResourceAsStream("mybatis-config.xml");
               sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   
       public static SqlSession getSqlSession(){
           return sqlSessionFactory.openSession();
       }
   }
   ```

7. 测试

   ```java
   package com.ly.test;
   
   import com.ly.dao.StudentDAO;
   import com.ly.pojo.Student;
   import com.ly.utils.MybatisUtils;
   import org.apache.ibatis.session.SqlSession;
   import org.junit.After;
   import org.junit.Before;
   import org.junit.Test;
   
   import java.util.List;
   
   public class TestStudent {
       private SqlSession sqlSession;
       private StudentDAO studentDAO;
       @Before
       public void init(){
           sqlSession = MybatisUtils.getSqlSession();
           studentDAO = sqlSession.getMapper(StudentDAO.class);
       }
       @After
       public void over(){
           sqlSession.close();
       }
   
       @Test
       public void getListStudent(){
           List<Student> listStudent = studentDAO.getListStudent();
           for (Student student : listStudent) {
               System.out.println(student.toString());
           }
   
       }
   }
   ```

### 9.2 按照查询嵌套处理

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ly.dao.StudentDAO">

    <!--
        1. 查询所有的学生信息
        2. 根据查询出来的学生的tid，查询对应的老师
    -->
    <select id="getListStudent" resultMap="studentMap">
        select * from student
    </select>
    
    <resultMap id="studentMap" type="student">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <!-- 复杂属性，需要单独处理 对象: association (一般适用于多对一情况下) 集合: collection (一般适用于一对多情况下) -->
        <association property="teacher" column="tid" javaType="teacher" select="getTeacherById"></association>
    </resultMap>
    
    <select id="getTeacherById" resultType="teacher">
        select * from teacher where id = #{tid}
    </select>
</mapper>
```

### 9.3 按照结果嵌套处理

```xml
<!-- 按照结果嵌套处理 -->
<select id="getListStudent" resultMap="studentMap">
    select s.*,t.id tid,t.name tname from student s,teacher t where s.tid = t.id
</select>

<resultMap id="studentMap" type="student">
    <id property="id" column="id"/>
    <result property="name" column="name"/>
    <association property="teacher" javaType="teacher">
        <id property="id" column="tid"/>
        <result property="name" column="tname"/>
    </association>
</resultMap>
```

## 10.一对多处理

### 10.1 环境搭建和 9 相同

### 10.2 按照结果嵌套处理

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ly.dao.TeacherDAO">

    <!-- 查询全部 -->
    <select id="getListTeacher" resultMap="teacherMap">
        select t.*,s.id sid,s.name sname from teacher t,student s where t.id = s.tid
    </select>

    <resultMap id="teacherMap" type="teacher">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <!-- 复杂属性，需要单独处理 对象: association (一般适用于多对一情况下) 集合: collection (一般适用于一对多情况下)
             集合中的泛型信息，使用ofType获取
         -->
        <collection property="students" ofType="student">
            <id property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>

    <!-- 根据id查询 -->
    <select id="getTeacherById" resultMap="teacherMap">
        select t.*,s.id sid,s.name sname from teacher t,student s where t.id = s.tid and t.id = #{id}
    </select>
</mapper>
```

## 11.动态SQL

根据不同的条件生成不同的SQL语句

### 11.1 环境搭建

~~~sql
create table blog(
	id varchar(50) not null comment '博客id',
	title varchar(100) not null comment '博客标题',
	author varchar(30) not null comment '博客作者',
	create_time datetime not null comment '创建时间',
	views int not null comment '浏览量'
)engine=innodb default charset=utf8
~~~

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <properties resource="db.properties"/>

    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>

    <typeAliases>
        <package name="com.ly.pojo"/>
    </typeAliases>

    <environments default="mybatis">
        <environment id="mybatis">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="${dirver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <package name="com.ly.dao"/>
    </mappers>
</configuration>
```

```java
package com.ly.utils;

import org.junit.Test;

import java.util.UUID;

public class IDUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    @Test
    public void test(){
        System.out.println(IDUtils.getId());
    }

}
```

```java
package com.ly.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
```

```java
package com.ly.pojo;

import java.util.Date;

public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime;
    private Integer views;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", views=" + views +
                '}';
    }
}
```

```java
package com.ly.dao;

import com.ly.pojo.Blog;

public interface BlogDAO {
    // 插入数据
    void addBook(Blog blog);
}
```

### 11.2 IF

```java
// 查询博客
List<Blog> queryBlogIF(Map map);
```

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
    select * from blog where 1=1
    <if test="title != null">
        and title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</select>
```

```java
@Test
public void queryBlogIF(){
    HashMap map = new HashMap();

    map.put("title","Mybatis如此简单");
    map.put("author","狂神说");

    List<Blog> blogs = blogDAO.queryBlogIF(map);
    for (Blog blog : blogs) {
        System.out.println(blog);
    }
}
```

### 11.3 choose(when,otherwise)

```xml
<!-- choose相当于java中的switch -->
    <select id="queryBlogChoose" parameterType="map" resultType="blog">
        select * from blog
        <where>
            <choose>
                <when test="title != null">
                    title = #{title}
                </when>
                <when test="author != author">
                   author = #{author}
                </when>
                <otherwise>
                   views = #{views}
                </otherwise>
            </choose>
        </where>
    </select>
```

### 11.4 trim(where,set)

*where* 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，*where* 元素也会将它们去除。

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <if test="title != null">
            and title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </where>
</select>
```

*set* 元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号

```xml
<update id="updateBlog" parameterType="map">
    update blog
    <set>
        <if test="title != null">
            title = #{title},
        </if>
        <if test="author != null">
            author = #{author},
        </if>
    </set>
    where id = #{id}
</update>
```

### 11.5 SQL片段

1. 使用SQL标签抽取公共的部分

   ```xml
   <sql id="if-title-author">
       <if test="title != null">
           and title = #{title}
       </if>
       <if test="author != null">
           and author = #{author}
       </if>
   </sql>
   ```

2. 在需要使用的地方使用include标签引用即可

   ```xml
   <select id="queryBlogIF" parameterType="map" resultType="blog">
       select * from blog
       <where>
           <include refid="if-title-author"/>
       </where>
   </select>
   ```

3. 注意事项:

   1. 最好基于单表来定义SQL片段
   2. 不要存在where,set标签

### 11.6 Foreach

```java
// 查询id为1,2,3记录的博客
List<Blog> queryBlogForeach(Map map);
```

```xml
<!--
    select * from where (id = ? or id = ? or id = ?)
-->
<select id="queryBlogForeach" parameterType="map" resultType="blog">
    select * from blog
    <where>
        <foreach collection="ids" item="id" open="and (" close=")" separator="or">
            id = #{id}
        </foreach>
    </where>
</select>
```

```java
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
```

### 总结

所谓的动态SQL，本质还是SQL语句，只是可以在SQL层面，去执行一个逻辑代码

动态SQL就是在拼接SQL语句,只要确保SQL的正确性，按照SQL的格式，去排列组合

## 12.缓存

### 12.1 简介

1. 什么是缓存
   + 存在内存中的临时数据
   + 将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从硬盘（关系型数据库数据文件）查询，从缓存中查询，提高查询效率，解决高并发系统的性能问题
2. 为什么使用缓存
   + 减少和数据库的交互次数，减少系统开销，提高系统效率
3. 什么样的数据可以使用缓存
   + 经常查询并且不经常改变的数据

### 12.2 MyBatis缓存

+ mybatis包含一个非常强大的查询缓存特性，它可以非常方便地定制和配置缓存。
+ mybatis系统中默认定制了两个缓存: **一级缓存**和**二级缓存**
  + 默认情况下，只有**一级缓存**开启。(SqlSession级别的缓存，也称为本地缓存)
  + 二级缓存需要手动开启和配置，它基于namespace级别
  + 为了提高扩展性，mybatis定义了缓存接口Cache。可以通过Cache接口来自定义二级缓存

### 12.3 一级缓存

+ 一级缓存也叫本地缓存
  + 于数据库同一次会话起键查询到的数据会放在本地缓存中。
  + 以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库

测试步骤

1. 开启日志

   ```xml
   <setting name="logImpl" value="STDOUT_LOGGING"/>
   ```

2. 在同一个SqlSession中查询两次相同的记录

   ```java
   @Test
   public void queryBlogIF(){
       HashMap map = new HashMap();
   
       map.put("title","Mybatis如此简单");
       map.put("author","狂神说");
   
       List<Blog> blogs = blogDAO.queryBlogIF(map);
       for (Blog blog : blogs) {
           System.out.println(blog);
       }
   
       map.put("title","Mybatis如此简单");
       map.put("author","狂神说");
   
       List<Blog> blogs1 = blogDAO.queryBlogIF(map);
       for (Blog blog : blogs1) {
           System.out.println(blog);
       }
   
       System.out.println(blogs == blogs1);
   }
   ```

3. 查看日志

   ![image-20200719161647201](C:\Users\22978\AppData\Roaming\Typora\typora-user-images\image-20200719161647201.png)

#### 12.3.1 缓存失效

1. 查询不同的记录
2. 执行update、insert、delete语句刷新缓存
   + 增删改操作可能会改变原来的数据，所以一定会刷新缓存
3. 查询不同的Mapper.xml
4. 手动清理缓存sqlSession.clearCache()

### 12.4 二级缓存

+ 二级缓存也叫全局缓存
+ 基于namespace级别的缓存，一个命名空间，对应一个二级缓存
+ 工作机制
  + 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中
  + 如果当前会话关闭了，这个会话对应的一级缓存就没了，将一级缓存保存到二级缓存中
  + 新的会话查询信息就可以从二级缓存中获取内容
  + 不同的Mapper查出的数据会放在自己对应的缓存中

测试步骤

1. 开启全局缓存

   ```xml
   <setting name="cachedEnabled" value="true"/>
   ```

2. 在要使用二级缓存的Mapper中开启

   ```xml
   <!--
       在当前Mapper.xml中使用二级缓存
       eviction="FIFO" ： 使用FIFO策略
       flushInterval="60000" : 每隔60秒刷新缓存
       size="512" : 最多512条缓存
       readOnly="true"
    -->
   <cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true"/>
   ```

3. 测试

   ```java
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
      qlSession1.close();
   }
   ```

注意: 实例类需要序列化

#### 小结

+ 只要开启了二级缓存，在同一个Mapper下就有效

+ 所有的数据都会先放在一个一级缓存中

+ 只有当会话提交，或者关闭了，才会将一级缓存提交到二级缓存中

  ![未命名文件](F:\lib\未命名文件.jpg)

