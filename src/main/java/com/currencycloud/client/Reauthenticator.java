package com.currencycloud.client;

import com.currencycloud.client.exception.AuthenticationException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class Reauthenticator extends AuthenticateInterceptor {

    Reauthenticator(CurrencyCloudClient client) {
        super(client);
    }

    @Override
    public Object aroundInvoke(InvocationHandler invocationHandler, Object proxy, Method method, Object[] args)
            throws Throwable {
        int reattemptsLeft = mayAutoAuthenticate(method) ? 2 : 0;

        do {
            try {
                log.trace("Attempting {}; will retry {} times after this.", method.getName(), reattemptsLeft);
                replaceAuthToken(method, args);
                return invocationHandler.invoke(proxy, method, args);
            } catch (AuthenticationException e) {
                log.info( "Attempt at {} failed with {}", method.getName(), e.toString());
                if (reattemptsLeft-- <= 0) {
                    throw e;
                }
                log.info("Reauthenticating.");
                client.authenticate();
                log.trace("client.authToken = {}", client.getAuthToken());
            }
        } while (true);
    }
}
