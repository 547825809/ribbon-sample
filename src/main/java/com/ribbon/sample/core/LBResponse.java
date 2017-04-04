package com.ribbon.sample.core;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

import com.netflix.client.IResponse;

public class LBResponse implements IResponse {

    private final URI uri;
    private final RpcResult response;

    public LBResponse(URI uri, RpcResult response) {
        this.uri = uri;
        this.response = response;
    }
    
    public int getStatus() {
        return this.response.getStatus();
    }

    @Override
    public byte[] getPayload() {
        return response.getPayload();
    }

    @Override
    public boolean hasPayload() {
        return this.response.getPayload() != null;
    }

    @Override
    public boolean isSuccess() {
        return this.response.getStatus() == 200;
    }
    

    @Override
    public URI getRequestedURI() {
        return this.uri;
    }

    @Override
    public Map<String, Collection<String>> getHeaders() {
        return this.response.getHeaders();
    }


    @Override
    public void close() throws IOException {
    }

}
