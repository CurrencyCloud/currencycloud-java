package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class AuthenticationTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "can_use_just_a_token", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUseJustAToken() throws Exception {
        client.setAuthToken("7fbba909f66ee6721b2e20a5fa1ccae7");
        assertThat(client.findBeneficiaries(), notNullValue());
    }

    @Test
    @Betamax(tape = "can be closed", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanBeClosed() throws Exception {
        client.authenticate("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0");
        client.endSession();
    }

/*
    // todo: automatically handle session timeouts?
    @Test
    @Betamax(tape = "handles session timeout error")
    public void testHandlesSessionTimeoutError() throws Exception {
        client.setAuthToken("3907f05da86533710efc589d58f51f45");
        client.findBeneficiaries();
    }
*/
}