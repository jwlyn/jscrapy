package com.oxf1.myspider.scheduler.impl;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.config.ConfigKeys;
import com.oxf1.myspider.request.Request;
import com.oxf1.myspider.scheduler.Scheduler;
//import gaillard.mongo.Queue;
import java.util.List;

/**
 * Mongodb 实现的队列
 * https://github.com/gaillard/mongo-queue-java
 * Created by cxu on 2014/11/21.
 */
public class MongoScheduler extends Scheduler{

    //private Queue queue;
    private DBCollection collection = null;

    public MongoScheduler(TaskConfig taskConfig) {
        super(taskConfig);
        String dbHost = taskConfig.loadString(ConfigKeys.MONGODB_HOST);
        int dbPort = taskConfig.loadInt(ConfigKeys.MONGODB_PORT);
        String dbName = taskConfig.loadString(ConfigKeys.MONGODB_DEDUP_DB_NAME);
        String tableName = taskConfig.getTaskName();
        Mongo mongo = new MongoClient(dbHost, dbPort);
        DB db = mongo.getDB(dbName);
        this.collection = db.getCollection(tableName);
        //this.queue = new Queue(this.collection);
    }

    @Override
    public int push(List<Request> requests) {
        for(Request req : requests){
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

    @Override
    public void close() {

    }
}
