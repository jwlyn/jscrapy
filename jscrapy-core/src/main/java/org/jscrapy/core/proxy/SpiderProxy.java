package org.jscrapy.core.proxy;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cxu on 2015/9/29.
 */
public class SpiderProxy {

    public enum ProxyType{
        SOCKS, SOCKS5, HTTP
    }

    private final Enum proxyType;
    private final String userName;
    private final String password;
    private final String host;
    private final int port;

    public SpiderProxy(ProxyType proxyType, String userName, String password, String host, int port) {
        this.proxyType = proxyType;
        this.userName = StringUtils.isBlank(userName) ? "" : userName;
        this.password = StringUtils.isBlank(password) ? "" : password;
        this.host = host;
        this.port = port;
    }

    public String getUserName() {
        return userName;
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
        return userName + ":" + password + "@" + host + ":" + port;
    }
}
