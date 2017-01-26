package org.jscrapy.core.cacher.impl;

import com.mongodb.*;
import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cxu on 2015/7/12.
 */
public class MongoCacher extends Cacher {
    final static Logger logger = LoggerFactory.getLogger(MongoCacher.class);
    private static final String DB_PRIMARY_KEY = "id";
    private static final String DB_CACHE_FIELD_NAME = "page";
    protected DB db = null;
    protected DBCollection collection = null;
    private Mongo mongo = null;

    public MongoCacher(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
        String dbHost = jscrapyConfig.getMongoCacheHost();
        int dbPort = jscrapyConfig.getMongoCachePort();
        String dbName = jscrapyConfig.getMongoCacheDbName();
        String tableName = jscrapyConfig.getMongoCacheTableName();
        this.mongo = new MongoClient(dbHost, dbPort);
        this.db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);
    }

    @Override
    public Page loadPage(HttpRequest request) {
        BasicDBObject query = new BasicDBObject();
        query.append(DB_PRIMARY_KEY, request.fp());
        BasicDBObject obj = (BasicDBObject) collection.findOne(query);
        if (obj != null) {
            Page pg = new Page(obj.getString(DB_CACHE_FIELD_NAME));
            pg.setIsFromCache(true);
            return pg;
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
}
