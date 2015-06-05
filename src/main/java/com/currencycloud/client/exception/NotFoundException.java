package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

/** Thrown when a 404 HTTP response is returned. */
public class NotFoundException extends ApiException {
    protected NotFoundException(ResponseException e) {
        super(e);
    }
}
