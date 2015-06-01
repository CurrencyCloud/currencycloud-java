package com.currencycloud.client.exception;

import com.currencycloud.client.model.ErrorMessage;
import com.currencycloud.client.model.ResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiException extends CurrencyCloudException {
    private final int httpStatusCode;
    private final String errorCode;
    private final List<ErrorMessage> errorMessages;

    public ApiException(ResponseException e) {
        super(collectMessages(e));
        httpStatusCode = e.getHttpStatusCode();
        errorCode = e.getErrorCode();
        errorMessages = new ArrayList<>();
        for (Map.Entry<String, List<ErrorMessage>> entry : e.getErrorMessages().entrySet()) {
            List<ErrorMessage> emsgs = entry.getValue();
            for (ErrorMessage em : emsgs) {
                errorMessages.add(new ErrorMessage(entry.getKey(), em.getCode(), em.getMessage(), em.getParams()));
            }
        }
    }

    static String collectMessages(ResponseException e) {
        StringBuilder sb = new StringBuilder();
        for (List<ErrorMessage> msgs : e.getErrorMessages().values()) {
            for (ErrorMessage msg : msgs) {
                if (sb.length() > 0) {
                    sb.append("; ");
                }
                sb.append(msg.getMessage());
            }
        }
        return sb.toString();
    }

    public static ApiException create(ResponseException e) {
        switch (e.getHttpStatusCode()) {
            case 400: return new BadRequestException(e);
            case 401: return new AuthenticationException(e);
            case 403: return new ForbiddenException(e);
            case 404: return new NotFoundException(e);
            case 429: return new TooManyRequestsException(e);
            case 500: return new InternalApplicationException(e);
        }
        return new ApiException(e);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

}
