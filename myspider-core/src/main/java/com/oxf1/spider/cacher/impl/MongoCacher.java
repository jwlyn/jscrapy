package com.oxf1.spider.cacher.impl;

import com.mongodb.*;
import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;

/**
 * Created by cxu on 2015/7/12.
 */
public class MongoCacher  extends Cacher {
    private Mongo mongo = null;
    protected DB db = null;
    protected DBCollection collection = null;
    private static final String DB_PRIMARY_KEY = "id";
    private static final String DB_CACHE_FIELD_NAME = "page";

    public MongoCacher(TaskConfig taskConfig) {
        super(taskConfig);
        String dbHost = taskConfig.loadString(ConfigKeys.MONGODB_HOST);
        int dbPort = taskConfig.loadInt(ConfigKeys.MONGODB_PORT);
        String dbName = taskConfig.loadString(ConfigKeys.MONGODB_CACHER_DB_NAME);
        String tableName = taskConfig.getTaskName();
        this.mongo = new MongoClient(dbHost, dbPort);
        this.db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);
    }

    @Override
    public Page loadPage(Request request) {
        BasicDBObject query = new BasicDBObject();
        query.append(DB_PRIMARY_KEY, request.fp());
        BasicDBObject obj = (BasicDBObject) collection.findOne(query);
        if(obj!=null){
            return new Page(obj.getString(DB_CACHE_FIELD_NAME));
        }

        return null;
    }

    @Override
    public void cachePage(Page page) {
        DBObject pageDoc = new BasicDBObject();
        pageDoc.put(DB_PRIMARY_KEY, page.getRequest().fp());
        pageDoc.put(DB_CACHE_FIELD_NAME, page.getRawText());
        this.collection.insert(pageDoc);
    }

    @Override
    public void close() {
        this.collection.drop();
    }
}
