package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;

import com.currencycloud.client.model.Payment;
import com.currencycloud.client.model.PaymentAuthorisation;
import com.currencycloud.client.model.PaymentAuthorisations;
import com.currencycloud.client.model.PaymentSubmission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.List;

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

    @Test
    @Betamax(tape = "can_authorise_payments", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanAuthorisePayments() throws Exception {
        Payment payment = Payment.create();
        payment.setId("1");
		List<Payment> payments =  Arrays.asList(payment );
		PaymentAuthorisations authorisations = client.authorisePayment(payments);

		assertThat(authorisations, notNullValue());
		assertThat(authorisations.getAuthorisations(), notNullValue());
        assertThat(authorisations.getAuthorisations().size(), equalTo(1));
        PaymentAuthorisation auth = authorisations.getAuthorisations().get(0);
        assertThat(auth.getId(), equalTo("1"));
        assertThat(auth.getPaymentStatus(), equalTo("approved"));
        assertThat(auth.isUpdated(), equalTo(true));
        assertThat(auth.getAuthStepsRequired(), equalTo(5));
        assertThat(auth.getAuthStepsTaken(), equalTo(3));
        assertThat(auth.getShortReference(), equalTo("TEST"));

    }

    @Test
    @Betamax(tape = "error_when_authorised_with_incorrect_login", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testThrowInvalidAuthorisation() throws Exception {
        Payment payment = Payment.create();
        payment.setId("6f5f99d1b860fc47e8a186e3dce0d3f9");
		List<Payment> payments =  Arrays.asList(payment );
		PaymentAuthorisations authorisations = client.authorisePayment(payments);

		assertThat(authorisations, notNullValue());
		assertThat(authorisations.getAuthorisations(), notNullValue());
        assertThat(authorisations.getAuthorisations().size(), equalTo(1));
        PaymentAuthorisation auth = authorisations.getAuthorisations().get(0);
        assertThat(auth.getId(), equalTo("6f5f99d1b860fc47e8a186e3dce0d3f9"));
        assertThat(auth.getError(), equalTo("You cannot authorise this Payment as it was created by you."));

    }

}
