package org.jscrapy.ext.dedup;

import com.mongodb.*;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.config.modulecfg.MongoDedepConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;

/**
 * Created by cxu on 2015/7/12.
 */
public class MongoDedup extends DeDup {
    private static final String DB_PRIMARY_KEY = "_id";
    private static final String DB_CACHE_FIELD_NAME = "is_page_exit";
    protected DB db = null;
    protected DBCollection collection = null;
    private Mongo mongo = null;

    public MongoDedup(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
    }

    public MongoDedup() {

    }

    /**
     *
     * @param jscrapyConfig
     */
    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        super.setJscrapyConfig(jscrapyConfig);
        MongoDedepConfig mongoDedupConfig = null;//(MongoDedepConfig)jscrapyConfig.get(ComponentName.DEDUP_MONGO);
        String dbHost = mongoDedupConfig.getHost();
        int dbPort = mongoDedupConfig.getPort();
        String dbName = mongoDedupConfig.getDbName();
        String tableName = jscrapyConfig.getTaskFp();

        this.mongo = new MongoClient(dbHost, dbPort);
        this.db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);
    }

    @Override
    protected boolean isDup(Request request) {
        String id = request.uniqId();
        BasicDBObject query = new BasicDBObject();
        query.append(DB_PRIMARY_KEY, id);
        BasicDBObject obj = (BasicDBObject) collection.findOne(query);
        if (obj == null) {
            DBObject pageDoc = new BasicDBObject();
            pageDoc.put(DB_PRIMARY_KEY, id);
            pageDoc.put(DB_CACHE_FIELD_NAME, 1);
            this.collection.insert(pageDoc);
        }
        return obj != null;
    }
}
