package com.currencycloud.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Interceptor;

import javax.ws.rs.HeaderParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

abstract class AuthenticateInterceptor implements Interceptor {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected CurrencyCloudClient client;

    public AuthenticateInterceptor(CurrencyCloudClient client) {
        this.client = client;
    }

    protected boolean mayAutoAuthenticate(Method method) {
        return method.getAnnotation(NoAutoAuth.class) == null;
    }

    protected void replaceAuthToken(Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int iParam = 0; iParam < parameterAnnotations.length; iParam++) {
            Annotation[] paramAnns = parameterAnnotations[iParam];
            for (Annotation paramAnn : paramAnns) {
                if (paramAnn instanceof HeaderParam) {
                    if ("X-Auth-Token".equals(((HeaderParam) paramAnn).value())) {
                        args[iParam] = client.getAuthToken();
                        log.trace("Using {} as auth token.", client.getAuthToken());
                        return;
                    }
                }
            }
        }
    }
}
