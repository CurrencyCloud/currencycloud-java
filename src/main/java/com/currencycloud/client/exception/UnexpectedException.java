package com.currencycloud.client.exception;

public class UnexpectedException extends CurrencyCloudException {
    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
