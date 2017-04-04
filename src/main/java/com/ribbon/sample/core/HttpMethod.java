package com.ribbon.sample.core;

public enum HttpMethod {

    GET("GET"), POST("POST");

    private HttpMethod(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return this.val;
    }

    private String val;

}