package com.currencycloud.client.exception;

import com.currencycloud.client.jackson.ExceptionDateSerializer;
import com.currencycloud.client.model.ErrorMessage;
import com.currencycloud.client.model.ResponseException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import si.mazi.rescu.InvocationAware;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ApiException extends CurrencyCloudException {

    private final String errorCode;
    private final List<ErrorMessage> errors;
    private final Response response;

    protected <E extends Throwable & InvocationAware> ApiException(
            String message,
            E cause,
            String errorCode, List<ErrorMessage> errors, Response response
    ) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errors = errors;
        this.response = response;
    }

    public ApiException(ResponseException e) {
        this(collectMessages(e), e, e.getErrorCode(), new ArrayList<ErrorMessage>(), new Response(e.getHttpStatusCode()));
        for (Map.Entry<String, List<ErrorMessage>> entry : e.getErrorMessages().entrySet()) {
            List<ErrorMessage> emsgs = entry.getValue();
            for (ErrorMessage em : emsgs) {
                errors.add(new ErrorMessage(entry.getKey(), em.getCode(), em.getMessage(), em.getParams()));
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

    public Response getResponse() {
        return response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
    public static class Response {
        public final int statusCode;

        @JsonSerialize(using = ExceptionDateSerializer.class)
        public final Date date = new Date();

        public Response(int statusCode) {
            this.statusCode = statusCode;
        }

    }
}
