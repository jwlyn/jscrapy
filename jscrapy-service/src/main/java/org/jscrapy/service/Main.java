package org.jscrapy.service;

import org.jscrapy.core.dal.h2.UrlQueueDo;
import org.jscrapy.core.dal.h2.UrlQueueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by cxu on 2016/8/1.
 */
@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    UrlQueueMapper urlQueueMapper;

    public static void main(String[]args){
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        //this.urlQueueMapper.createTable("test_table1");

        UrlQueueDo obj = this.urlQueueMapper.selectUrlByStatus("test_url", "I");

        System.out.println(obj.getId());
    }
}
