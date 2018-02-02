package org.jscrapy.ext.modulecfg;

import org.jscrapy.core.config.modulecfg.TaskComponentConfig;

/**
 * Created by cxu on 2017/1/18.
 */
public class RedisConfig extends TaskComponentConfig {
    private String host;
    private int port = 27017;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
