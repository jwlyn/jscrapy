package com.oxf1.spider.dudup.impl;

import com.mongodb.*;
import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.dudup.DeDup;
import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2015/7/12.
 */
public class MongoDedup extends DeDup {
    private static final String DB_PRIMARY_KEY = "_id";
    private static final String DB_CACHE_FIELD_NAME = "page";
    private Mongo mongo = null;
    protected DB db = null;
    protected DBCollection collection = null;

    public MongoDedup(TaskConfig taskConfig) {
        super(taskConfig);

        String dbHost = taskConfig.loadString(taskConfig, ConfigKeys.MONGODB_HOST);
        int dbPort = taskConfig.loadInt(taskConfig, ConfigKeys.MONGODB_PORT);
        String dbName = taskConfig.loadString(taskConfig, ConfigKeys.MONGODB_DEDUP_DB_NAME);
        String tableName = taskConfig.getTaskName();
        this.mongo = new MongoClient(dbHost, dbPort);
        this.db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);

    }

    @Override
    protected boolean isDup(Request request) {
        String id = request.fp();
        BasicDBObject query = new BasicDBObject();
        query.append(DB_PRIMARY_KEY, id);
        BasicDBObject obj = (BasicDBObject) collection.findOne(query);
        if(obj==null){
            DBObject pageDoc = new BasicDBObject();
            pageDoc.put(DB_PRIMARY_KEY, id);
            pageDoc.put(DB_CACHE_FIELD_NAME, 1);
            this.collection.insert(pageDoc);
        }
        return obj!=null;
    }

    @Override
    public void clean() {
        this.collection.drop();
    }
}
