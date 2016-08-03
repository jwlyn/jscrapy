# 基于RDBMS的URL队列表

|      | id   | url    | url_status | retry_times | url_type | rule_id | gmt_created | gmt_modified | error_code | error_msg |
| ---- | ---- | ------ | ---------- | ----------- | -------- | ------- | ----------- | ------------ | ---------- | --------- |
| H2   | int  | string | char(1)    | int(2)      | char(5)  | int     | datetime    | datetime     | char(10)   | text      |
| PG   | int  | json   | char(1)    | int(2)      | char(5)  | int     | datetime    | datetime     | char(10)   | text      |
|      |      |        |            |             |          |         |             |              |            |           |


## H2
```sql
CREATE TABLE IF NOT EXISTS XXX
(
    id IDENTITY PRIMARY KEY,
    url TEXT,
    retry_times SMALLINT
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