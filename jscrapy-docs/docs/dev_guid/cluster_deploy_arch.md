# 集群部署模式详细

Jscrapy在集群状态下需要组件如下：

| *组件*            | *作用*            |
| --------------- | --------------- |
| postgreSql      | 元数据管理           |
| postgreSql      | URL队列           |
| ~~redis~~/kafka | admin与service交互 |
| mongoDb         | URL去重服务         |
| mongoDb         | 数据存储            |
| mongoDb         | 网页缓存            |
|                 |                 |

因此集群模式下你需要部署的三方组件是：

- postgreSql
- ~~redis~~或者kafka
- mongoDb



> 不用redis做消息的原因是考虑到以后用kafka做url队列，用同一套组件完成。

