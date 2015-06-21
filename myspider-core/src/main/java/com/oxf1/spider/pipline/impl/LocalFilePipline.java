package com.oxf1.spider.pipline.impl;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.impl.EhcacheConfigOperator;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.pipline.Pipline;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by cxu on 2015/6/21.
 */
public class LocalFilePipline extends Pipline {

   /*并发写入数据到同一个文件，每个taskId对应一个数据结果的queue*/
    private static LocalFileDataConsumer DATA_CONSUMER = new LocalFileDataConsumer();

    /**
     *
     * @param taskid
     * @throws IOException
     */
    public LocalFilePipline(TaskId taskid) {
        super(taskid);
    }

    @Override
    public void save(DataItem dataItem) {
        /*
         * 保存数据到一个队里里，使用消费线程串行写入文件，
         * 这样做可以解除写文件加锁导致抓取任务阻塞的问题。
         */
        DATA_CONSUMER.add(dataItem, getTaskid());//数据给消费者
    }

    @Override
    public void close(){
        DATA_CONSUMER.stop(this.getTaskid());
    }
}

class LocalFileDataConsumer{
    private Map<TaskId, BlockingQueue<DataItem>> dataQueue;
    private Map<TaskId, Future> consumerTask;
    ExecutorService threadPool = Executors.newCachedThreadPool();

    public LocalFileDataConsumer(){
        dataQueue = new ConcurrentHashMap<TaskId, BlockingQueue<DataItem>>();
        consumerTask = new ConcurrentHashMap<TaskId, Future>();
    }

    /**
     * 放入数据到队列里，等待线程写入文件
     * @param data
     * @param taskid
     * @return
     */
    public boolean add(DataItem data, TaskId taskid){
        BlockingQueue<DataItem> queue = dataQueue.get(taskid);
        if(queue==null){
            synchronized (LocalFileDataConsumer.class){
                if(queue==null){
                    /*初始化taskid对应的数据队列；启动taskid对应的消费线程*/
                    dataQueue.put(taskid, new LinkedBlockingDeque<DataItem>());
                    this.startConsumThread(taskid);//启动消费线程
                }
            }
        }
        queue = dataQueue.get(taskid);
        return queue.add(data);
    }

    /**
     * 停止任务
     * @param taskId
     */
    public void stop(TaskId taskId){
        Future f = this.consumerTask.get(taskId);
        if(f!=null && f.isCancelled()==false){
            f.cancel(true);
            consumerTask.remove(taskId);
        }
        else{
            //TODO 记录系统异常
        }
    }

    /**
     * 启动吸入数据的线程
     * @param taskId
     */
    private void startConsumThread(final TaskId taskId){
        final Runnable task = new Runnable() {
            public void run() {
                String dataFilePath = EhcacheConfigOperator.instance().loadString(taskId, ConfigKeys.LOCAL_FILE_PIPLINE_DATA_SAVE_PATH);//完整的目录+文件名字。解析之后的数据保存的位置
                String baseDir = FilenameUtils.getFullPath(dataFilePath);
                try {
                    FileUtils.forceMkdir(new File(baseDir));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while(true)
                {
                    try {
                        DataItem di = (DataItem)dataQueue.get(taskId).take();
                        if(di!=null) {
                            //append 方式追加写入
                            FileUtils.writeLines(new File(dataFilePath), di.getData(), true);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Future future = threadPool.submit(task);
        consumerTask.put(taskId, future);
    }
}