package com.currencycloud.client;

import com.currencycloud.client.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Interceptor;

import javax.ws.rs.HeaderParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ReauthenticateInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(ReauthenticateInterceptor.class);

    private CurrencyCloudClient client;

    ReauthenticateInterceptor(CurrencyCloudClient client) {
        this.client = client;
    }

    @Override
    public Object aroundInvoke(InvocationHandler invocationHandler, Object proxy, Method method, Object[] args)
            throws Throwable {
        int reattemptsLeft = 2;
        if (method.getAnnotation(NoAutoAuth.class) != null) {
            reattemptsLeft = 0;
        }
        do {
            try {
                log.trace("Attempting {}; will retry {} times after this.", method.getName(), reattemptsLeft);
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                replaceAuthToken(args, parameterAnnotations);
                return invocationHandler.invoke(proxy, method, args);
            } catch (AuthenticationException e) {
                log.trace( "Attempt at {} failed with {}", method.getName(), e.toString());
                if (reattemptsLeft-- <= 0) {
                    throw e;
                }
                log.trace("Reauthenticating.");
                client.authenticate();
                log.trace("client.authToken = {}", client.getAuthToken());
            }
        } while (true);
    }

    private void replaceAuthToken(Object[] args, Annotation[][] parameterAnnotations) {
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
