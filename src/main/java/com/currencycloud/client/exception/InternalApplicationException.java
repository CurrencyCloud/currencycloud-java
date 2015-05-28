package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

public class InternalApplicationException extends ApiException {
    protected InternalApplicationException(ResponseException e) {
        super(e);
    }
}
