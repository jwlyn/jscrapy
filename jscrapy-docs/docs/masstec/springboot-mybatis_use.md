# mybatis-spring-boot 使用记录

[mybatis-spring-boot](https://github.com/mybatis/spring-boot-starter)大多数介绍了
在单一的pom工程中使用例子。本节着重记录在多pom工程中使用中猜到的坑：

- dal层在其他模块调用
- 多数据源配置，H2, postgreSql

详细步骤记录如下:

1. 定义 XXMapper和XXDo java类。
2. 在classpath里定义 xxmapper.xml
3. 写一个mybatis-config.xml写上2中定义的xxmapper.xml的位置
4. 应用层：在application.properties中定义 `mybatis.config-locations=mybatis-config.xml`; datasource
的相关配置项。
5. 应用层：写一个spring-boot的 java config类， 在类上使用`@MapperScan('package.to.mapper')`

上述的第5步骤实在有点让我迷惑，找了半天才发现这样才能使用。原因不明，既然都可以自动配置了，为什么
spring-boot不能多做一点帮助自动扫描到mapper?

其次我的应用是动态建表的，如果在mapper xml中的sql参数里使用 `#{}`的形式那么如果遇到有
table做为参数的就直接报了sql语法错误，如果要动态操作表要改用 `${}`。需要注意的是对于`#{}`形式
的参数mybatis会自动进行sql注入检查，但是 `${}`形式的参数只会原样进行字符串替换。

