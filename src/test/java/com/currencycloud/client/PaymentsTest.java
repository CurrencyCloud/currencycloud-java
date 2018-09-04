package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.PaymentAuthorisation;
import com.currencycloud.client.model.PaymentAuthorisations;
import com.currencycloud.client.model.PaymentSubmission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        PaymentSubmission submission = client.retrievePaymentSubmission("01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe");

        assertThat("MXGGYAGJULIIQKDV", equalTo(submission.getSubmissionRef()));
        assertThat(submission.getMt103().startsWith("{1:F01TCCLGB20AXXX0090000004}"), equalTo(true));
        assertThat(submission.getStatus(), equalTo("pending"));
    }

    @Test
    @Betamax(tape = "can_authorise_payments", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanAuthorisePayments() throws Exception {
		List<String> paymentIds =  new ArrayList<>();
        paymentIds.add("8e3aeeb8-deeb-4665-96de-54b880a953ac");
        paymentIds.add("f16cafe4-1f8f-472e-99d9-8c828918d4f8");
        paymentIds.add("d025f90f-a23c-46f9-979a-35a9f98d9491");
        PaymentAuthorisations authorisationsData = client.authorisePayment(paymentIds);
        List<PaymentAuthorisation> authorisations = authorisationsData.getPaymentAuthorisations();

		assertThat(authorisations.size(), equalTo(3));
		assertThat(authorisations.get(0), notNullValue());
        assertThat(authorisations.get(0).getPaymentId(), equalTo("8e3aeeb8-deeb-4665-96de-54b880a953ac"));
        assertThat(authorisations.get(1).getPaymentId(), equalTo("f16cafe4-1f8f-472e-99d9-8c828918d4f8"));
        assertThat(authorisations.get(2).getPaymentId(), equalTo("d025f90f-a23c-46f9-979a-35a9f98d9491"));
        assertThat(authorisations.get(0).getPaymentStatus(), equalTo("awaiting_authorisation"));
        assertThat(authorisations.get(0).isUpdated(), equalTo(false));
        assertThat(authorisations.get(0).getError(), emptyOrNullString());
        assertThat(authorisations.get(0).getAuthStepsRequired(), equalTo(0));
        assertThat(authorisations.get(0).getAuthStepsTaken(), equalTo(1));
        assertThat(authorisations.get(0).getShortReference(), equalTo("180802-YKGDVV001"));

    }

}
