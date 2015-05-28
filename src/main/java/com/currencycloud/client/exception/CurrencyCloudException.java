package com.currencycloud.client.exception;

public abstract class CurrencyCloudException extends RuntimeException {

    protected CurrencyCloudException(Throwable cause) {
        super(cause);
    }

    protected CurrencyCloudException(String message) {
        super(message);
    }

}
