package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class AuthenticationTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "happens_lazily", match = {MatchRule.method, MatchRule.uri, MatchRule.body, MatchRule.headers})
    public void testHappensLazily() throws Exception {
        CurrencyCloudClient client = prepareTestClient("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0", null);

        assertThat(client.findBeneficiaries(null, null), notNullValue());
        assertThat(client.getAuthToken(), equalTo("57ef449f6316f2f54dfec37c2006fe50"));
    }

    @Test
    @Betamax(tape = "can_use_just_a_token", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUseJustAToken() throws Exception {
        CurrencyCloudClient client = prepareTestClient(null, null, "7fbba909f66ee6721b2e20a5fa1ccae7");

        assertThat(client.findBeneficiaries(null, null), notNullValue());
    }

    @Test
    @Betamax(tape = "can be closed", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanBeClosed() throws Exception {
        CurrencyCloudClient client = prepareTestClient(
                "rjnienaber@gmail.com",
                "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0",
                null
        );

        client.authenticate();
        client.endSession();
    }

    @Test
    @Betamax(tape = "handles session timeout error", match = {MatchRule.method, MatchRule.uri, MatchRule.body, MatchRule.headers})
    public void testHandlesSessionTimeoutError() throws Exception {
        CurrencyCloudClient client = prepareTestClient(
                "rjnienaber@gmail.com",
                "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0",
                "3068d3ff160ab0636648d98b4e4e10ad" // The Ruby test has "3907f05da86533710efc589d58f51f45", but this does not match the yaml
        );

        client.findBeneficiaries(null, null);
    }
}