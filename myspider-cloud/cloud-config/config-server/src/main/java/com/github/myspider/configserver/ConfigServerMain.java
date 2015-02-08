package com.github.myspider.configserver;

/**
 * Created by cxu on 2015/2/7.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigServerMain
{
    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(ConfigServerMain.class, args);
    }
}
