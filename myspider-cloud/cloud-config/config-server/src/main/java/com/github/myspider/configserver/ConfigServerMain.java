package com.github.myspider.configserver;

/**
 * Created by cxu on 2015/2/7.
 */

import com.jfinal.core.JFinalFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ConfigServerMain extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(ConfigServerMain.class);
    }

    @Bean
    public FilterRegistrationBean serverEndpointExporter()
    {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JFinalFilter jfinalConfigFilter = new JFinalFilter();

        Map<String, String> initParms = new HashMap<String, String>();
        initParms.put("configClass", "com.github.myspider.configserver.cfg.JFinalAppConfig");
        registrationBean.setInitParameters(initParms);
        List urlPattern = new ArrayList<String>();
        urlPattern.add("/*");
        registrationBean.setUrlPatterns(urlPattern);

        registrationBean.setFilter(jfinalConfigFilter);
        registrationBean.setOrder(2);

        return registrationBean;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(ConfigServerMain.class, args);
    }
}
