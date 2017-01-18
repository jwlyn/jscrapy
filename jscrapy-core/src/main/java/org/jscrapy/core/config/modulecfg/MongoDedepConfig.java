package org.jscrapy.core.config.modulecfg;

/**
 * Created by cxu on 2017/1/16.
 */
public class MongoDedepConfig  extends TaskComponentConfig {
    private String host;
    private int port;
    private String dbName;

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

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
