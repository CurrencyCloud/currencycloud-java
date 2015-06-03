package com.currencycloud.client.exception;

import com.currencycloud.client.Utils;

public class UnexpectedException extends CurrencyCloudException {

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public String getInnerError() {
        Throwable cause = super.getCause();
        return cause == null ? null : Utils.getRootCause(cause).toString();
    }
}
