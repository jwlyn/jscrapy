package com.oxf1.myspider.http;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cxu on 2015/9/29.
 */
public class SpiderProxy {
    private final String user;
    private final String password;
    private final String host;
    private final int port;

    public SpiderProxy(String user, String password, String host, int port) {
        this.user = StringUtils.isBlank(user) ? "" : user;
        this.password = StringUtils.isBlank(password) ? "" : password;
        this.host = host;
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return user + ":" + password + "@" + host + ":" + port;
    }
}
