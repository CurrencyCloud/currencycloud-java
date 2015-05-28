package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

public class NotFoundException extends ApiException {
    protected NotFoundException(ResponseException e) {
        super(e);
    }
}
