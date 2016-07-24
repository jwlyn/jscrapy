package org.jscrapy.common.http.fetcher;

import org.jscrapy.common.http.FetchRequest;
import org.jscrapy.common.http.FetchResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;

/**
 * Created by cxu on 2015/11/28.
 */
public interface HttpFetcher {
    FetchResponse download(FetchRequest fetchRequest) throws SocketTimeoutException, URISyntaxException, UnsupportedEncodingException, IOException;
}
