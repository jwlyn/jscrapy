package org.jscrapy.core.dedup.impl;

import com.mongodb.*;
import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.config.cfgkey.ConfigKeys;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;

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

        String dbHost = taskConfig.loadString(ConfigKeys.DEDUP_MONGO_HOST);
        int dbPort = taskConfig.loadInt(ConfigKeys.DEDUP_MONGO_PORT);
        String dbName = taskConfig.loadString(ConfigKeys.RT_EXT_DEDUP_MONGODB_DB_NAME);
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
    public void close() {
        this.collection.drop();
    }
}
