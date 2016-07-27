## 爬虫组件

|             | 作用                    | 子类   |
| ----------- | --------------------- | ---- |
| Spider      | 控制爬虫的各个组件协调，是逻辑控制的核心。 |      |
| Downloader  | 用于下载网页、图片、文件等。        |      |
| Processor   | 调用脚本，完成解析。产出URL和数据内容。 |      |
| Pipeline    | 存储数据到一个或者多个地方。        |      |
| UrlConsumer | 把URL从队列中取出。           |      |
| UrlProducer | 把新产生的URL放入到队列里。       |      |
| Cacher      | 网页缓存                  |      |
| Deduper     | URL去重                 |      |
|             |                       |      |

