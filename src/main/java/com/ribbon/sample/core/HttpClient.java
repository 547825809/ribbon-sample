package com.ribbon.sample.core;

import java.util.Collection;
import java.util.Map;

public interface HttpClient {

    RpcResult execute(String endPoint, HttpMethod method, Map<String, Collection<String>> headers, Map<String, Object> args);

    RpcResult executePOST(String endPoint, Map<String, Collection<String>> headers, Map<String, Object> args);

    RpcResult executeGET(String endPoint, Map<String, Collection<String>> headers, Map<String, Object> args);

    RpcResult executeJSON(String endPoint, Map<String, Collection<String>> headers, String payload);

    void setConnectTimeout(int connectTimeout);

    void setReadTimeout(int readTimeout);

    int getConnectTimeout();

    int getReadTimeout();

}
