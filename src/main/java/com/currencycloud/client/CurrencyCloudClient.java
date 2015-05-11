package com.currencycloud.client;

import com.currencycloud.client.model.CurrencyCloudException;
import si.mazi.rescu.RestProxyFactory;

public class CurrencyCloudClient {

    private final CurrencyCloud api;
    private String authToken;

    public CurrencyCloudClient() {
        this(Environment.production);
    }

    public CurrencyCloudClient(Environment environment) {
        api = RestProxyFactory.createProxy(CurrencyCloud.class, environment.url);
    }

    public void authenticate(String loginId, String apiKey) throws CurrencyCloudException {
        authToken = api.authenticate(loginId, apiKey).getAuthToken();
    }

    public void endSession() throws CurrencyCloudException {
        api.endSession(authToken);
        authToken = null;
    }

    public static enum Environment {
        production("https://api.thecurrencycloud.com"),
        demo("https://devapi.thecurrencycloud.com")
        ;
        private final String url;

        Environment(String url) {
            this.url = url;
        }
    }
}
