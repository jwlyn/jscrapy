package org.jscrapy.contrib.scheduler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.scheduler.Scheduler;

import java.util.List;

//import gaillard.mongo.Queue;

/**
 * Mongodb 实现的队列
 * https://github.com/gaillard/mongo-queue-java
 * Created by cxu on 2014/11/21.
 */
public class MongoScheduler extends Scheduler {

    //private Queue queue;
    private DBCollection collection = null;

    public MongoScheduler(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
        String dbHost = jscrapyConfig.getSchedulerMongoHost();
        int dbPort = jscrapyConfig.getSchedulerMongoPort();
        String dbName = jscrapyConfig.getSchedulerMongoDbName();
        String tableName = jscrapyConfig.getSchedulerMongoTableName();
        Mongo mongo = new MongoClient(dbHost, dbPort);
        DB db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);
        //this.queue = new Queue(this.collection);
    }

    @Override
    public int push(List<Request> requests) {
        for (Request req : requests) {
            //TODO
        }

        return requests.size();
    }

    @Override
    public List<Request> poll(int n) {
        return null;
    }

    @Override
    public int remove(List<Request> requests) {
        return 0;
    }

}
