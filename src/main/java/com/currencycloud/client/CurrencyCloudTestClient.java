package com.currencycloud.client;

/**
 * This is a specialised implementation of the CurrencyCloudClient which allows the default
 * API url to be manually set for testing purposes. This will allows the client to be directed
 * to servers other than the official Currencycloud demo and production servers for the purpose
 * of testing
 */
public class CurrencyCloudTestClient extends CurrencyCloudClient {

    public CurrencyCloudTestClient(String url, String loginId, String apiKey) {
        this(url, loginId, apiKey, HttpClientConfiguration.builder().build());
    }

    public CurrencyCloudTestClient(String url, String loginId, String apiKey, HttpClientConfiguration httpClientConfiguration) {
        super(url, loginId, apiKey, httpClientConfiguration);
    }
}
