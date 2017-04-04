package com.ribbon.sample.exception;

@SuppressWarnings("serial")
public class HttpInvokeException extends RuntimeException {

    public HttpInvokeException() {
        super();
    }

    public HttpInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpInvokeException(String message) {
        super(message);
    }

    public HttpInvokeException(Throwable cause) {
        super(cause);
    }

}
