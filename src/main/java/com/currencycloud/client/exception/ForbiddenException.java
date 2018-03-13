package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

/** Thrown when a 403 HTTP response is returned. */
public class ForbiddenException extends ApiException {
    protected ForbiddenException(ResponseException e) {
        super(e);
    }
}
