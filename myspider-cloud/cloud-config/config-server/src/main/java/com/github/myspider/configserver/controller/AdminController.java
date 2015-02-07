package com.github.myspider.configserver.controller;

import com.jfinal.core.Controller;

/**
 * Created by cxu on 2015/2/7.
 */
public class AdminController extends Controller
{
    public void index()
    {
        setAttr("dear", "cxu");
        render("test.ftl");
    }
}
