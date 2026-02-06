package com.currencycloud.client;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class TestSupport extends JsonTestSupport {

    public static final Logger log = LoggerFactory.getLogger(CurrencyCloudClient.class);

    @Rule
    public final TestName name = new TestName();
    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(options().port(5555));
    @Rule
    public WireMockClassRule instanceRule = wiremock;

    protected CurrencyCloudClient prepareTestClient(String loginId, String apiKey, String authToken) {
        return prepareTestClient("http://localhost:5555", loginId, apiKey, authToken);
    }

    protected CurrencyCloudClient prepareTestClient(String host, String loginId, String apiKey, String authToken) {
        CurrencyCloudClient client = new CurrencyCloudClient(
                host,
                loginId,
                apiKey,
                CurrencyCloudClient.HttpClientConfiguration.builder().build());
        client.setAuthToken(authToken);
        return client;
    }
}
