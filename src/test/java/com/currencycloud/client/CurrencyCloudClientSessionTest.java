package com.currencycloud.client;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

public class CurrencyCloudClientSessionTest {

    @Test
    public void testRaisesAnErrorIfTheEnvironmentIsNotSet() {
        assertThrows(NullPointerException.class, () -> new CurrencyCloudClient((CurrencyCloudClient.Environment) null, null, null));
    }

    @Test
    public void testRaisesAnErrorIfTheLoginIdIsNotSet() {
        CurrencyCloudClient client = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, null, null);
        Exception exception = assertThrows(IllegalArgumentException.class, client::authenticate);
        assertThat(exception.getMessage(), containsString("apiKey must be set"));
    }

    @Test
    public void testRaisesAnErrorIfTheApiKeyIsNotSet() {
        CurrencyCloudClient client = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, "development@currencycloud.com", null);
        Exception exception = assertThrows(IllegalArgumentException.class, client::authenticate);
        assertThat(exception.getMessage(), containsString("apiKey must be set"));
    }

    @Test
    public void testRaisesAnErrorIfTheUrlNoAbsolute() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new CurrencyCloudClient("example.com", "development@currencycloud.com", "key"));
        assertThat(exception.getMessage(), equalTo("URI is not absolute"));
    }

    @Test
    public void testRaisesAnErrorIfTheUrlIncorrect() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new CurrencyCloudClient("example com", "development@currencycloud.com", "key"));
        assertThat(exception.getMessage(), containsString("Illegal character in path at index 7: example com"));
    }

    @Test
    public void testValidUrlAndPortAccepted() {
        new CurrencyCloudClient("http://localhost:8080", "development@currencycloud.com", "key");
    }
}
