package com.ribbon.sample.exception;

@SuppressWarnings("serial")
public class ConfigureException extends RuntimeException {

    public ConfigureException() {
        super();
    }

    public ConfigureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConfigureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigureException(String message) {
        super(message);
    }

    public ConfigureException(Throwable cause) {
        super(cause);
    }

}
