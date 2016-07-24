package org.jscrapy.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Created by cxu on 2015/10/2.
 */

@SpringBootApplication
public class Application {
    public static void main(String[]args) throws IOException, InterruptedException {
        SpringApplication.run(Application.class, args);//启动
    }
}
