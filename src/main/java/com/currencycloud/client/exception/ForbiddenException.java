package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

public class ForbiddenException extends ApiException {
    protected ForbiddenException(ResponseException e) {
        super(e);
    }
}
