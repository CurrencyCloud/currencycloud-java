package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

/** Thrown when a 401 HTTP response is returned. */
public class AuthenticationException extends ApiException {
    protected AuthenticationException(ResponseException e) {
        super(e);
    }
}
