package com.currencycloud.client;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CurrencyCloudClientSessionTest {

    @Test
    public void testRaisesAnErrorIfTheEnvironmentIsNotSet() throws Exception {
        try {
            CurrencyCloudClient client = new CurrencyCloudClient((CurrencyCloudClient.Environment) null, null, null);
            client.authenticate();
            throw new AssertionError("Should have failed.");
        } catch (NullPointerException ignored) { }
    }

    @Test
    public void testRaisesAnErrorIfTheLoginIdIsNotSet() throws Exception {
        CurrencyCloudClient client = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, null, null);
        try {
            client.authenticate();
            throw new AssertionError("Should have failed.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("apiKey must be set"));
        }
    }

    @Test
    public void testRaisesAnErrorIfTheApiKeyIsNotSet() throws Exception {
        CurrencyCloudClient client = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, "development@currencycloud.com", null);
        try {
            client.authenticate();
            throw new AssertionError("Should have failed.");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("apiKey"));
            assertThat(e.getMessage(), containsString("must be set"));
        }
    }
}
