package com.currencycloud.client;

import com.currencycloud.client.exception.ApiException;
import com.currencycloud.client.exception.UnexpectedException;
import com.currencycloud.client.model.ResponseException;
import si.mazi.rescu.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ExceptionTransformer implements Interceptor {

    @Override
    public Object aroundInvoke(InvocationHandler invocationHandler, Object proxy, Method method, Object[] args)
            throws Throwable {
        try {
            return invocationHandler.invoke(proxy, method, args);
        } catch (ResponseException e) {
            throw ApiException.create(e);
        } catch (ApiException | UnexpectedException e) {
            throw e;
        } catch (Throwable t) {
            throw new UnexpectedException("Error invoking " + method, t);
        }
    }
}
