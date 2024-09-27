package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.currencycloud.client.model.ScreeningResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


public class ScreeningCompleteTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_screening_complete", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanScreeningComplete() throws Exception {

        final ScreeningResponse screeningResponse = client.complete("ae305c98-e46f-465a-b468-f92d39fad977",true,"Accepted");

        assertThat(screeningResponse.getTransactionId().toString(), equalTo("ae305c98-e46f-465a-b468-f92d39fad977"));
        assertThat(screeningResponse.getAccountId().toString(), equalTo("7a116d7d-6310-40ae-8d54-0ffbe41dc1c9"));
        assertThat(screeningResponse.getHouseAccountId().toString(), equalTo("7a116d7d-6310-40ae-8d54-0ffbe41dc1c9"));

        assertThat(screeningResponse.getResult().getReason(), equalTo("Accepted"));
        assertThat(screeningResponse.getResult().isAccepted(), equalTo(true));
    }
}
