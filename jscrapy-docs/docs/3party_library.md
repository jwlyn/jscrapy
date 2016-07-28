## URL管理组件
|           |   单机 | 集群   | 内存   | 磁盘   | URL优先级    | 不丢数据/错误恢复 | 缺点                   |
| --------- | ---: | :--- | ---- | ---- | --------- | --------- | -------------------- |
| mapDb     |    Y |      | Y    | Y    | FIFO/LIFO |           |                      |
| h2        |    Y |      |      | Y    | 数字优先级     | Y         |                      |
| bigQueue  |    Y |      |      | Y    | FIFO      | Y         |                      |
| mongoDb   |    Y | Y    |      | Y    | 数字优先级     | Y         | findAndModify每次只能取一条 |
| kafka     |    Y | Y    |      | Y    | FIFO      | Y         |                      |
| pg        |    Y | Y    |      | Y    | 数字优先级     | Y         |                      |
| redis     |    Y | Y    | Y    |      | FIFO      |           | redis崩溃时所有任务不能恢复     |
| JDK queue |    Y |      | Y    |      | FIFO/LIFO |           | 程序退出，不能断点继续          |
|           |      |      |      |      |           |           |                      |

### 核心URL管理组件

1. bigQueue：单机方式，小数量URL队列。
2. h2：单机方式，小数量URL，要求URL严格不丢失。
3. mongoDb：集群方式，大URL量级，严格要求数据不丢失。

## URL去重组件

|         | 单机   | 集群   | 内存   | 磁盘   | 错误恢复 |
| ------- | ---- | ---- | ---- | ---- | ---- |
| mapDb   | Y    |      |      | Y    |      |
| JDK Set | Y    |      | Y    |      |      |
| redis   | Y    | Y    | Y    |      |      |
| mongoDb | Y    | Y    |      | Y    | Y    |

### 核心URL去重组件

1. mapDb：单机，去重。
2. mongoDb：集群方式，去重。