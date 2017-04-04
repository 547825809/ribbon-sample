package com.ribbon.sample.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.ribbon.sample.exception.HttpInvokeException;

public class RpcResult {

    private Map<String, Collection<String>> headers;

    private int status;
    
    private byte[] payload;

    public RpcResult(int status, Map<String, Collection<String>> headers, InputStream inputStream) {
        this.status = status;
        this.headers = headers;
        setPayload(inputStream);      
    }
    
    private void setPayload(InputStream inputStream) {
        if (inputStream != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                int c = -1;
                while ((c = inputStream.read()) != -1) {
                    bos.write(c);
                }           
            } catch (IOException e) {
                throw new HttpInvokeException(e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
            this.payload = bos.toByteArray();
        }        
    }
    
    public byte[] getPayload() {
        return payload;
    }    

    public int getStatus() {
        return status;
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }


}
