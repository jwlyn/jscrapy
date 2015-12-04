package com.oxf1.myspider.common.http.fetcher;

import com.oxf1.myspider.common.http.FetchRequest;
import com.oxf1.myspider.common.http.FetchResponse;

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
