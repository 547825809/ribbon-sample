package com.ribbon.sample.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.netflix.client.ClientRequest;

public class LBRequest extends ClientRequest {

    private String endPoint;

    private HttpMethod method;

    private Map<String, Object> args;

    private Map<String, Collection<String>> headers;

    public LBRequest(String endPoint, HttpMethod method, Map<String, Collection<String>> headers, Map<String, Object> args) {
        this.endPoint = endPoint;
        this.method = method;
        this.args= args;
        this.headers = headers;
        try {
            if (StringUtils.isBlank(endPoint)) {
                super.setUri(new URI("/"));
            } else {
                super.setUri(new URI(endPoint));                
            }
            
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("invalid uri");
        } 
    }

    public Map<String, Object> getArgs() {
        if (args == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(args);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }


}
