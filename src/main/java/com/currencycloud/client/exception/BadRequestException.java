package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

/** Thrown when a 400 HTTP response is returned. */
public class BadRequestException extends ApiException {
    protected BadRequestException(ResponseException e) {
        super(e);
    }
}
