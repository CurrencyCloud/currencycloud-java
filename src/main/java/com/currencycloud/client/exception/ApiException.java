package com.currencycloud.client.exception;

import com.currencycloud.client.Utils;
import com.currencycloud.client.model.ErrorMessage;
import com.currencycloud.client.model.ResponseException;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import si.mazi.rescu.InvocationAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiException extends CurrencyCloudException {

    private final String errorCode;
    private final List<ErrorMessage> errors;
    private final Response response;

    protected <E extends Throwable & InvocationAware> ApiException(
            String message,
            E cause,
            String errorCode,
            List<ErrorMessage> errors,
            Response response
    ) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errors = errors;
        this.response = response;
    }

    public ApiException(ResponseException e) {
        this(collectMessages(e), e, e.getErrorCode(), new ArrayList<ErrorMessage>(), new Response(e.getHttpStatusCode(), e.getResponseHeaders()));
        if (e.getErrorMessages() != null) {
            for (Map.Entry<String, List<ErrorMessage>> entry : e.getErrorMessages().entrySet()) {
                List<ErrorMessage> emsgs = entry.getValue();
                for (ErrorMessage em : emsgs) {
                    errors.add(new ErrorMessage(entry.getKey(), em.getCode(), em.getMessage(), em.getParams()));
                }
            }
        }
    }

    static String collectMessages(ResponseException e) {
        if (e.getErrorMessages() == null) {
            return e.getMessage();
        }
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

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonPropertyOrder({"statusCode", "date", "requestId"})
    public static class Response {

        /** The HTTP response status code (eg. 404 for Not Found).  */
        public final int statusCode;

        /** The request ID, as returned by the server. */
        public final String requestId;

        /** The request date/time, as returned by the server. */
        public final String date;

        private Response(int statusCode, Map<String, List<String>> responseHeaders) {
            this.statusCode = statusCode;
            this.requestId = get(responseHeaders, "X-Request-Id");
            this.date = get(responseHeaders, "Date");
        }

        private static String get(Map<String, List<String>> responseHeaders, String date) {
            if (responseHeaders != null) {
                List<String> all = responseHeaders.get(date);
                if (all != null && !all.isEmpty()) {
                    return Utils.joinInverse(all, ", ");
                }
            }
            return null;
        }
    }
}
