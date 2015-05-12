package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import org.junit.Test;

public class AuthenticationTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "can be closed")
    public void testCanBeClosed() throws Exception {
        client.authenticate("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0");
        client.endSession();
    }

    @Test
    @Betamax(tape = "happens lazily")
    public void testHappensLazily() throws Exception {
        client.authenticate("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0");
    }
}