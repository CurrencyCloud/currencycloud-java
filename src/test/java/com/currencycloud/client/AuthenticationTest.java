package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import org.junit.Test;

public class AuthenticationTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "can be closed", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanBeClosed() throws Exception {
        client.authenticate("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0");
        client.endSession();
    }

    @Test
    @Betamax(tape = "happens lazily", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testHappensLazily() throws Exception {
        client.authenticate("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0");
    }
}