# 商品管理系统

要求：

1. 使用 SpringBoot + Mybatis 完成
2. 完成以下功能：
    - 登录
    - 商品管理
        - 商品列表，条件查询，分页
        - 添加商品、修改商品信息、下架商品
        - 商品包含以下属性：商品名称，商品图片，进货价，售价，库存，状态(int  01)
    - 下架商品管理【选做】
        - 下架商品列表，条件查询，分页
        - 上架、删除
    - 用户管理
        - 用户列表，条件查询，分页
        - 添加用户、重置用户密码、禁用用户、删除用户
        - 用户的属性： 用户名、密码、真实姓名、手机号、邮箱、头像
    - 日志列表【选做】
        - 显示操作日志，条件查询，分页
        - 日志记录格式：某人在某时某刻操作了某数据
    - 修改用户信息
    - 修改密码
    - 修改头像
    - 退出

## 表结构

**商品表**

|    字段     |    类型     |           说明            |
| :---------: | :---------: | :-----------------------: |
|     id      |     int     |         商品编号          |
| goods_name  | varchar(30) |         商品名称          |
|    cost     |     int     |      成本，单位：分       |
|    price    |     int     |      售价，单位：分       |
|    nums     |     int     |           数量            |
|    state    |     int     | 状态  0表示下架 1表示上架 |
| create_date |  datetime   |         创建时间          |
| update_date |  datetime   |         更新时间          |

**用户表**

|    字段     |    类型     |           说明            |
| :---------: | :---------: | :-----------------------: |
|     id      | varchar(32) |     用户编号，自定义      |
|  username   | varchar(20) |          用户名           |
|  password   | varchar(32) |      密码(需要加密)       |
|    name     | varchar(20) |         真实姓名          |
|    phone    | varchar(11) |          手机号           |
|    email    | varchar(30) |           邮箱            |
|   avatar    | varchar(30) |           头像            |
|    state    |     int     | 状态  0表示禁用 1表示正常 |
| create_date |  datetime   |         创建时间          |
| update_date |  datetime   |         更新时间          |

# SpringBoot 整合 Mybatis

1. 创建 SpringBoot 工程，搭建 web 环境

   pom.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.7.17</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
       <groupId>com.kfm</groupId>
       <artifactId>boot-mybatis</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>boot-mybatis</name>
       <description>boot-mybatis</description>
       <properties>
           <maven.compiler.source>17</maven.compiler.source>
           <maven.compiler.target>17</maven.compiler.target>
           <java.version>17</java.version>
       </properties>
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-thymeleaf</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
   
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           <dependency>
               <groupId>com.mysql</groupId>
               <artifactId>mysql-connector-j</artifactId>
               <scope>runtime</scope>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
                   <configuration>
                       <excludes>
                           <exclude>
                               <groupId>org.projectlombok</groupId>
                               <artifactId>lombok</artifactId>
                           </exclude>
                       </excludes>
                   </configuration>
               </plugin>
           </plugins>
       </build>
   
   </project>
   
   ```

2. 导入 mybatis 依赖

   ```xml
   <dependency>
       <groupId>org.mybatis.spring.boot</groupId>
       <artifactId>mybatis-spring-boot-starter</artifactId>
       <version>3.0.1</version>
   </dependency>
   ```

3.  写 DAO 接口和映射文件 （代码略）

4. 配置

    1. 配置数据源

    2. MyBatis 配置

       ```xml
       # 数据源
       spring:
         datasource:
           driver-class-name: com.mysql.cj.jdbc.Driver
           url: jdbc:mysql://localhost:3306/web?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
           username: root
           password:
           type: com.alibaba.druid.pool.DruidDataSource
           druid:
             initial-size: 5
             min-idle: 5
             max-active: 20
             max-wait: 60000
       
       # MyBatis 配置
       mybatis:
         # mapper 映射文件的路径
         mapperLocations: classpath:mapper/*.xml
         # 实体类所在的包，类型别名
         type-aliases-package: com.kfm.bootmybatis.model
         configuration:
           # 开启驼峰命名
           map-underscore-to-camel-case: true
       ```

5.  扫描 DAO(mapper) 接口

1. 第一种方式: 在Mapper接口上使用 `@Mapper` 注解
2. 第二种方式: 在 SpringBoot 的启动类上使用 `@MapperScan(mapepr 所在的包)`




## 使用分页插件

1. 导入依赖



   ```xml
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper-spring-boot-starter</artifactId>
       <version>1.4.7</version>
   </dependency>
   ```

由于 此依赖中包含了`mybatis-spring-boot-starter`依赖所以可以省略之前添加的`mybatis-spring-boot-starter`

