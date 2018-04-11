package com.currencycloud.client.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.InvocationAware;
import si.mazi.rescu.RestInvocation;

import javax.annotation.Nullable;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This is the root of the Currency Cloud Exception hierarchy. It provides some information about the
 * HTTP request and the server response. The {@link #toString()} method returns YAML-formatted data.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonPropertyOrder({"platform", "request", "response", "errorCode", "errors"})
public abstract class CurrencyCloudException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(ApiException.class);
    private static final YAMLFactory YAML_FACTORY = new YAMLFactory();
    public static final String PLATFORM = String.format(
            "Java %s (%s)",
            System.getProperty("java.version"),
            System.getProperty("java.vendor")
    );
    private static final JacksonAnnotationIntrospector IGNORE_EXCEPTION_PROPERTIES = new JacksonAnnotationIntrospector() {
        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m) {
            Class<?> declaringClass = m.getDeclaringClass();
            return declaringClass.isAssignableFrom(RuntimeException.class)
                    || super.hasIgnoreMarker(m);
        }
    };

    private Request request;

    protected CurrencyCloudException(String message, Throwable cause) {
        super(message, cause);
        if (cause instanceof InvocationAware) {
            setInvocation(((InvocationAware)cause).getInvocation());
        }
    }

    protected void setInvocation(@Nullable RestInvocation invocation) {
        if (invocation != null) {
            Map<String, String> params = new LinkedHashMap<>();
            for (Class<? extends Annotation> paramAnn : Arrays.asList(FormParam.class, QueryParam.class)) {
                params.putAll(invocation.getParamsMap().get(paramAnn).asHttpHeaders());
            }
            this.request = new Request(params, invocation.getHttpMethod(), invocation.getInvocationUrl());
        }
    }

    public Request getRequest() {
        return request;
    }

    /** @return The runtime environment of the client, eg. "Java 1.7" */
    public String getPlatform() {
        return PLATFORM;
    }

    /**
     * @return YAML-formatted exception data
     */
    @Override
    public String toString() {
        try (
                StringWriter out = new StringWriter();
                PrintWriter writer = new PrintWriter(out)
        ) {
            ObjectMapper mapper = new ObjectMapper(YAML_FACTORY);
            writer.println(getClass().getSimpleName());
            mapper.setAnnotationIntrospector(IGNORE_EXCEPTION_PROPERTIES);
            mapper.writeValue(writer, this);
            return out.toString();
        } catch (Exception e) {
            log.warn("Error formatting exception as YAML: " + e);
            return super.toString();
        }
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Request {

        /** The parameters that were sent in the request (GET or POST) */
        public final Map<String, String> parameters;

        /** The HTTP method in lowercase (get, post, ...) */
        public final String verb;

        /** The full URL of the request */
        public final String url;

        private Request(Map<String, String> parameters, String httpMethod, String url) {
            this.parameters = parameters;
            this.verb = httpMethod.toLowerCase();
            this.url = url;
        }
    }
}
