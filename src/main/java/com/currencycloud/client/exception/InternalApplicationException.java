package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

/** Thrown when a 500 HTTP response is returned. */
public class InternalApplicationException extends ApiException {
    protected InternalApplicationException(ResponseException e) {
        super(e);
    }
}
