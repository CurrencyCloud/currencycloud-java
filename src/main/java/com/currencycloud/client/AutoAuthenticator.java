package com.currencycloud.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class AutoAuthenticator extends AuthenticateInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AutoAuthenticator.class);

    AutoAuthenticator(CurrencyCloudClient client) {
        super(client);
    }

    @Override
    public Object aroundInvoke(InvocationHandler invocationHandler, Object proxy, Method method, Object[] args)
            throws Throwable {
        if (client.getAuthToken() == null && mayAutoAuthenticate(method)) {
            log.trace("Attempting lazy authentication for {}.", method);
            client.authenticate();
            replaceAuthToken(method, args);
        }

        return invocationHandler.invoke(proxy, method, args);
    }
}
