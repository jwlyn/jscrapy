package com.github.myspider.configserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**http://docs.spring.io/spring-boot/docs/1.2.1.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-auto-configuration
 * Created by cxu on 2015/2/7.
 */
@Controller
@RequestMapping("/admin")
public class AdminController
{
    @RequestMapping("/index")
    public String index(ModelMap map)
    {
        map.put("dear", "cxu");
        return "test";
    }
}
