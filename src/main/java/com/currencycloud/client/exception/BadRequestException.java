package com.currencycloud.client.exception;

import com.currencycloud.client.model.ResponseException;

public class BadRequestException extends ApiException {
    protected BadRequestException(ResponseException e) {
        super(e);
    }
}
