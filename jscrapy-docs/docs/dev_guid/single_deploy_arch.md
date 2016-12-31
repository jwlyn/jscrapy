# 单机部署模式详细

单机模式下的Jscrapy需要的组件如下：

| *组件*                                     | *作用*            |
| ---------------------------------------- | --------------- |
| [H2](http://www.h2database.com)          | 元数据管理           |
| H2                                       | URL队列           |
| bigqueue                                 | admin与service交互 |
| H2([MVStore](http://www.h2database.com/html/mvstore.html)) | Url去重           |
| [orientDb](http://orientdb.com/)         | 数据存储            |
| [orientDb](http://orientdb.com/)         | 网页缓存            |



最简单情况下你只需要启动应用程序， H2，bigqueue，orientdb都是java的嵌入式组件，
在打包好的应用程序里已经存在了。

某些情况下，虽然你部署了单机模式，但是为了容易处理数据还是希望能把数据直接保存
到一个更强大的Db里，这个时候你可以部署一台mongoDb。

> 有一点需要记住，单机还是集群在Jscrapy的设计中只是一个逻辑上的概念，他们共享
> 代码。一个单机的程序可以无缝切换为集群上的一个节点。集群上的一个节点也可以随时
> 退出集群当做单机程序来使用。