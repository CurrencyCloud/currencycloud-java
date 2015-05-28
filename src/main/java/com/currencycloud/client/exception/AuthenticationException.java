package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

public class AuthenticationException extends ApiException {
    protected AuthenticationException(ResponseException e) {
        super(e);
    }
}
