package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

public class TooManyRequestsException extends ApiException {
    protected TooManyRequestsException(ResponseException e) {
        super(e);
    }
}
