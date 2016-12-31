# 基于RDBMS的URL队列表

|      | id      | url    | url_status  | retry_times | url_type  | site_id      | gmt_created  | gmt_access    | error_code | error_msg |
| ---- | ----    | ------ | ----------  | ----------- | --------  | -------      | -----------  | ------------  | ---------- | --------- |
| H2   |IDENTITY | TEXT   | char(10)    | TINYINT     | char(10)  | IDENTITY     | TIMESTAMP    | TIMESTAMP     | char(10)   | text      |
| PG   | int     | json   | char(10)    | int(2)      | char(10)  | int          | TIMESTAMP    | TIMESTAMP     | char(10)   | text      |


## H2
```sql
CREATE TABLE IF NOT EXISTS XXX
(
    id IDENTITY PRIMARY KEY, -- 唯一兼 
    url TEXT,  -- url
    url_status CHAR(10), -- 种子状态[N:新进入, O:出队列, E:出错]
    retry_times TINYINT,  -- 重试次数
    url_type CHAR(10), -- 种子类型[S:种子(Seed), L:列表(List), D: 详情页(Detail)]
    site_id INT,  -- 站点ID
    gmt_created TIMESTAMP, -- url插入时间
    gmt_access TIMESTAMP, -- 被访问时间
    error_code CHAR(20), -- 错误编码
    error_msg  TEXT  -- 错误详细信息
);
```

## Pg
```sql
CREATE TABLE IF NOT EXISTS XXX
(
    id IDENTITY PRIMARY KEY,
    url TEXT,
    retry_times SMALLINT
);
```