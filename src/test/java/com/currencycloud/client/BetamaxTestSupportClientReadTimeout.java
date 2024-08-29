package com.currencycloud.client;

public class BetamaxTestSupportClientReadTimeout extends BetamaxTestSupport {

    protected CurrencyCloudClient prepareTestClient(String loginId, String apiKey, String authToken) {
        CurrencyCloudClient client = new CurrencyCloudClient(
                "http://localhost:5555",
                loginId,
                apiKey,
                CurrencyCloudClient.HttpClientConfiguration.builder()
                        .httpConnTimeout(1500)
                        .httpReadTimeout(1)
                        .build());
        client.setAuthToken(authToken);
        return client;
    }
}
