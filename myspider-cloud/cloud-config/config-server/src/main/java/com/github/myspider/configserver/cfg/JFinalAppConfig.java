package com.github.myspider.configserver.cfg;

import com.github.myspider.configserver.controller.AdminController;
import com.github.myspider.configserver.controller.ConfigController;
import com.jfinal.config.*;
import com.jfinal.render.ViewType;

/**
 * Created by cxu on 2015/2/7.
 */
public class JFinalAppConfig extends JFinalConfig
{

    @Override
    public void configConstant(Constants constants)
    {
        constants.setDevMode(true);
        constants.setViewType(ViewType.FREE_MARKER);
    }

    @Override
    public void configRoute(Routes routes)
    {
        String tplPath = "/WEB-INF/tpl/";
        routes.add("/index", ConfigController.class, tplPath);//对外输出配置的value值
        routes.add("/admin", AdminController.class, tplPath);//管理
    }

    @Override
    public void configPlugin(Plugins plugins)
    {

    }

    @Override
    public void configInterceptor(Interceptors interceptors)
    {

    }

    @Override
    public void configHandler(Handlers handlers)
    {

    }
}
