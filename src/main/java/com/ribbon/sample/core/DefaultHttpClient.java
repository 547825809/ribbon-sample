package com.ribbon.sample.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.ribbon.sample.exception.HttpInvokeException;

import feign.Client;
import feign.Request;
import feign.Response;

public class DefaultHttpClient implements HttpClient {

    private static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");

    private Charset charset = DEFAULT_CHARSET;

    private int connectTimeout;

    private int readTimeout;

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    @Override
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public int getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public int getReadTimeout() {
        return readTimeout;
    }

    private byte[] toBytes(Map<String, Object> argsMap) {
        if (argsMap == null || argsMap.size() == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Entry<String, Object> entry : argsMap.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString().getBytes(charset);
    }

    private RpcResult executeInternal(String endPoint, HttpMethod method, Map<String, Collection<String>> headers, byte[] body) {
        Request.Options options = new Request.Options(this.connectTimeout, this.readTimeout);
        Client.Default client = new Client.Default(null, null);
        String url = endPoint;
        Request request = Request.create(method.toString(), url, headers, body, charset);
        try {
            Response response = client.execute(request, options);
            return new RpcResult(response.status(), request.headers(), response.body().asInputStream());
        } catch (IOException e) {
            throw new HttpInvokeException(e);
        }
    }

    @Override
    public RpcResult execute(String endPoint, HttpMethod method, Map<String, Collection<String>> headers, Map<String, Object> args) {
        return executeInternal(endPoint, method, headers, toBytes(args));
    }


    @Override
    public RpcResult executePOST(String endPoint, Map<String, Collection<String>> headers, Map<String, Object> args) {
        return execute(endPoint, HttpMethod.POST, headers, args);
    }

    @Override
    public RpcResult executeGET(String endPoint, Map<String, Collection<String>> headers, Map<String, Object> args) {
        return execute(endPoint, HttpMethod.GET, headers, args);
    }

    @Override
    public RpcResult executeJSON(String endPoint, Map<String, Collection<String>> headers, String payload) {
        if (payload == null) {
            throw new IllegalArgumentException("body is null");
        }
        return executeInternal(endPoint, HttpMethod.POST, headers, payload.getBytes(charset));

    }

}
