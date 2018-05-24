package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.PaymentSubmission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PaymentsTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() {
        log.debug("------------------------- " + name.getMethodName() + " -------------------------");
    }

    @Test
    @Betamax(tape = "can_retrieve_submission", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSubmission() throws Exception {
        PaymentSubmission sub = client.retrievePaymentSubmission("01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe");

        assertThat("MXGGYAGJULIIQKDV", equalTo(sub.getSubmissionRef()));
        assertThat(sub.getMt103().startsWith("{1:F01TCCLGB20AXXX0090000004}"), equalTo(true));
        assertThat(sub.getStatus(), equalTo("pending"));
    }

}
