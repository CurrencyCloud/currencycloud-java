package com.currencycloud.client.exception;

public class UnexpectedException extends CurrencyCloudException {

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getInnerError() {
        Throwable cause = super.getCause();
        return cause == null ? null : cause.toString();
    }
}
