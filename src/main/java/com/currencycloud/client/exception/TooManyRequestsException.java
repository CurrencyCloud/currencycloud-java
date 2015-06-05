package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

/** Thrown when a 429 HTTP response is returned. */
public class TooManyRequestsException extends ApiException {
    protected TooManyRequestsException(ResponseException e) {
        super(e);
    }
}
