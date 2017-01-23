package org.jscrapy.contrib.scheduler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;

//import gaillard.mongo.Queue;

/**
 * Mongodb 实现的队列
 * https://github.com/gaillard/mongo-queue-java
 * Created by cxu on 2014/11/21.
 */
public class MongoScheduler extends ConfigDriver {

    //private Queue queue;
    private DBCollection collection = null;

    public MongoScheduler(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
        String dbHost = jscrapyConfig.getSchedulerMongoHost();
        int dbPort = jscrapyConfig.getSchedulerMongoPort();
        String dbName = jscrapyConfig.getSchedulerMongoDbName();
        String tableName = jscrapyConfig.getSchedulerMongoTableName();
        Mongo mongo = new MongoClient(dbHost, dbPort);
        DB db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);
        //this.queue = new Queue(this.collection);
    }



}
