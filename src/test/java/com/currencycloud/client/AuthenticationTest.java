package com.currencycloud.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class AuthenticationTest extends TestSupport {

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testHappensLazily() {
        CurrencyCloudClient client = prepareTestClient("development@currencycloud.com", "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbee1", null);

        assertThat(client.findBeneficiaries(null, null), notNullValue());
        assertThat(client.getAuthToken(), equalTo("57ef449f6316f2f54dfec37c2006fe50"));
    }

    @Test
    public void testCanUseJustAToken() {
        CurrencyCloudClient client = prepareTestClient(null, null, "7fbba909f66ee6721b2e20a5fa1ccae7");

        assertThat(client.findBeneficiaries(null, null), notNullValue());
    }

    @Test
    public void testCanBeClosed() {
        CurrencyCloudClient client = prepareTestClient(
                "development@currencycloud.com",
                "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbee2",
                null
        );

        client.authenticate();
        client.endSession();
    }

    @Test
    public void testHandlesSessionTimeoutError() {
        CurrencyCloudClient client = prepareTestClient(
                "development@currencycloud.com",
                "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef",
                "3068d3ff160ab0636648d98b4e4e10ad"
        );

        client.findBeneficiaries(null, null);
    }
}
