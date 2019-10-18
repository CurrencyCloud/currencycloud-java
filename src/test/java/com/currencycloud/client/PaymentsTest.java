package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    @Betamax(tape = "can_create", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCreate() throws Exception {
        Payment payment = Payment.create();
        payment.setCurrency("EUR");
        payment.setBeneficiaryId("60fbe8d3-f7d0-4124-9077-93d09fb2186a");
        payment.setAmount(new BigDecimal("788.44"));
        payment.setReason("Invoice");
        payment.setReference("REF-INV-1838");
        payment.setUniqueRequestId("a20bc586-b7a9-4316-9daf-d4ede4c0d4ee");

        payment = client.createPayment(payment, null);

        assertThat(payment, notNullValue());
        assertThat(payment.getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("788.44")));
        assertThat(payment.getBeneficiaryId(), equalTo("60fbe8d3-f7d0-4124-9077-93d09fb2186a"));
        assertThat(payment.getCurrency(), equalTo("EUR"));
        assertThat(payment.getReference(), equalTo("REF-INV-1838"));
        assertThat(payment.getReason(), equalTo("Invoice"));
        assertThat(payment.getStatus(), equalTo("ready_to_send"));
        assertThat(payment.getCreatorContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getPaymentType(), equalTo("regular"));
        assertThat(payment.getPaymentDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getTransferredAt(), is(nullValue()));
        assertThat(payment.getAuthorisationStepsRequired(), equalTo(0));
        assertThat(payment.getLastUpdaterContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getShortReference(), equalTo("180925-BXRBZZ001"));
        assertThat(payment.getConversionId(), is(nullValue()));
        assertThat(payment.getFailureReason(), is(emptyString()));
        assertThat(payment.getPayerId(), equalTo("68561f01-b5d8-4fad-9bcb-d1712a1bc0c8"));
        assertThat(payment.getPayerDetailsSource(), equalTo("account"));
        assertThat(payment.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getPaymentGroupId(), is(nullValue()));
        assertThat(payment.getUniqueRequestId(), equalTo("a20bc586-b7a9-4316-9daf-d4ede4c0d4ee"));
        assertThat(payment.getFailureReturnedAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(payment.getUltimateBeneficiaryName(), is(nullValue()));
        assertThat(payment.getPurposeCode(), is(nullValue()));
        assertThat(payment.getChargeType(), equalTo("ours"));
    }

    @Test
    @Betamax(tape = "can_update", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUpdate() throws Exception {
        Payment payment = Payment.create();
        payment.setId("778d2ba2-b2ec-4b39-b54c-0c3410525c97");
        payment.setUltimateBeneficiaryName("Francesco Bianco");
        payment.setChargeType("shared");

        payment = client.updatePayment(payment, null);

        assertThat(payment, notNullValue());
        assertThat(payment.getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("788.44")));
        assertThat(payment.getBeneficiaryId(), equalTo("60fbe8d3-f7d0-4124-9077-93d09fb2186a"));
        assertThat(payment.getCurrency(), equalTo("EUR"));
        assertThat(payment.getReference(), equalTo("REF-INV-1838"));
        assertThat(payment.getReason(), equalTo("Invoice"));
        assertThat(payment.getStatus(), equalTo("ready_to_send"));
        assertThat(payment.getCreatorContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getPaymentType(), equalTo("regular"));
        assertThat(payment.getPaymentDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getTransferredAt(), is(nullValue()));
        assertThat(payment.getAuthorisationStepsRequired(), equalTo(0));
        assertThat(payment.getLastUpdaterContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getShortReference(), equalTo("180925-BXRBZZ001"));
        assertThat(payment.getConversionId(), is(nullValue()));
        assertThat(payment.getFailureReason(), is(emptyString()));
        assertThat(payment.getPayerId(), equalTo("68561f01-b5d8-4fad-9bcb-d1712a1bc0c8"));
        assertThat(payment.getPayerDetailsSource(), equalTo("account"));
        assertThat(payment.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getPaymentGroupId(), is(nullValue()));
        assertThat(payment.getUniqueRequestId(), equalTo("a20bc586-b7a9-4316-9daf-d4ede4c0d4ee"));
        assertThat(payment.getFailureReturnedAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(payment.getUltimateBeneficiaryName(), equalTo("Francesco Bianco"));
        assertThat(payment.getPurposeCode(), is(nullValue()));
        assertThat(payment.getChargeType(), equalTo("shared"));
    }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFind() throws Exception {
        Payments paymentsData = client.findPayments(null, null);
        List<Payment> payments = paymentsData.getPayments();
        Payment payment = payments.iterator().next();
        Pagination pagination = paymentsData.getPagination();

        assertThat(payments.size(), equalTo(pagination.getTotalEntries()));
        assertThat(payment, notNullValue());
        assertThat(payments.get(0).getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
        assertThat(payments.get(1).getId(), equalTo("730a737e-d43e-4e75-809a-82a2f4cccaae"));
        assertThat(payments.get(2).getId(), equalTo("ee6f48c0-69e7-4ccc-a49d-0899629fe9a0"));
        assertThat(payment.getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("788.44")));
        assertThat(payment.getBeneficiaryId(), equalTo("60fbe8d3-f7d0-4124-9077-93d09fb2186a"));
        assertThat(payment.getCurrency(), equalTo("EUR"));
        assertThat(payment.getReference(), equalTo("REF-INV-1838"));
        assertThat(payment.getReason(), equalTo("Invoice"));
        assertThat(payment.getStatus(), equalTo("ready_to_send"));
        assertThat(payment.getCreatorContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getPaymentType(), equalTo("regular"));
        assertThat(payment.getPaymentDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getTransferredAt(), is(nullValue()));
        assertThat(payment.getAuthorisationStepsRequired(), equalTo(0));
        assertThat(payment.getLastUpdaterContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getShortReference(), equalTo("180925-BXRBZZ001"));
        assertThat(payment.getConversionId(), is(nullValue()));
        assertThat(payment.getFailureReason(), is(emptyString()));
        assertThat(payment.getPayerId(), equalTo("68561f01-b5d8-4fad-9bcb-d1712a1bc0c8"));
        assertThat(payment.getPayerDetailsSource(), equalTo("account"));
        assertThat(payment.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getPaymentGroupId(), is(nullValue()));
        assertThat(payment.getUniqueRequestId(), equalTo("a20bc586-b7a9-4316-9daf-d4ede4c0d4ee"));
        assertThat(payment.getFailureReturnedAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(payment.getUltimateBeneficiaryName(), equalTo("Francesco Bianco"));
        assertThat(payment.getPurposeCode(), is(nullValue()));
        assertThat(payment.getChargeType(), equalTo("ours"));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getTotalEntries(), equalTo(3));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieve() throws Exception {
        Payment payment = client.retrievePayment("778d2ba2-b2ec-4b39-b54c-0c3410525c97", null);

        assertThat(payment, notNullValue());
        assertThat(payment.getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("788.44")));
        assertThat(payment.getBeneficiaryId(), equalTo("60fbe8d3-f7d0-4124-9077-93d09fb2186a"));
        assertThat(payment.getCurrency(), equalTo("EUR"));
        assertThat(payment.getReference(), equalTo("REF-INV-1838"));
        assertThat(payment.getReason(), equalTo("Invoice"));
        assertThat(payment.getStatus(), equalTo("ready_to_send"));
        assertThat(payment.getCreatorContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getPaymentType(), equalTo("regular"));
        assertThat(payment.getPaymentDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getTransferredAt(), is(nullValue()));
        assertThat(payment.getAuthorisationStepsRequired(), equalTo(0));
        assertThat(payment.getLastUpdaterContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getShortReference(), equalTo("180925-BXRBZZ001"));
        assertThat(payment.getConversionId(), is(nullValue()));
        assertThat(payment.getFailureReason(), is(emptyString()));
        assertThat(payment.getPayerId(), equalTo("68561f01-b5d8-4fad-9bcb-d1712a1bc0c8"));
        assertThat(payment.getPayerDetailsSource(), equalTo("account"));
        assertThat(payment.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getPaymentGroupId(), is(nullValue()));
        assertThat(payment.getUniqueRequestId(), equalTo("a20bc586-b7a9-4316-9daf-d4ede4c0d4ee"));
        assertThat(payment.getFailureReturnedAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(payment.getUltimateBeneficiaryName(), equalTo("Francesco Bianco"));
        assertThat(payment.getPurposeCode(), is(nullValue()));
        assertThat(payment.getChargeType(), equalTo("ours"));
    }

    @Test
    @Betamax(tape = "can_delete", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanDelete() throws Exception {
        Payment payment = client.deletePayment("778d2ba2-b2ec-4b39-b54c-0c3410525c97");

        assertThat(payment, notNullValue());
        assertThat(payment.getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("788.44")));
        assertThat(payment.getBeneficiaryId(), equalTo("60fbe8d3-f7d0-4124-9077-93d09fb2186a"));
        assertThat(payment.getCurrency(), equalTo("EUR"));
        assertThat(payment.getReference(), equalTo("REF-INV-1838"));
        assertThat(payment.getReason(), equalTo("Invoice"));
        assertThat(payment.getStatus(), equalTo("ready_to_send"));
        assertThat(payment.getCreatorContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getPaymentType(), equalTo("regular"));
        assertThat(payment.getPaymentDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getTransferredAt(), is(nullValue()));
        assertThat(payment.getAuthorisationStepsRequired(), equalTo(0));
        assertThat(payment.getLastUpdaterContactId(), equalTo("a66ca63f-e668-47af-8bb9-74363240d781"));
        assertThat(payment.getShortReference(), equalTo("180925-BXRBZZ001"));
        assertThat(payment.getConversionId(), is(nullValue()));
        assertThat(payment.getFailureReason(), is(emptyString()));
        assertThat(payment.getPayerId(), equalTo("68561f01-b5d8-4fad-9bcb-d1712a1bc0c8"));
        assertThat(payment.getPayerDetailsSource(), equalTo("account"));
        assertThat(payment.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(payment.getPaymentGroupId(), is(nullValue()));
        assertThat(payment.getFailureReturnedAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(payment.getUltimateBeneficiaryName(), equalTo("Francesco Bianco"));
        assertThat(payment.getPurposeCode(), is(nullValue()));
        assertThat(payment.getChargeType(), equalTo("ours"));
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

    @Test
    @Betamax(tape = "can_retrieve_confirmation", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveConfirmation() throws Exception {
        PaymentConfirmation confirmation = client.retrievePaymentConfirmation("e6b30f2d-0088-4d99-bb47-c6b136fcf447");

        assertThat(confirmation, notNullValue());
        assertThat(confirmation.getId(), equalTo("e6b30f2d-0088-4d99-bb47-c6b136fcf447"));
        assertThat(confirmation.getPaymentId(), equalTo("796e0d7d-bae6-4d8a-b217-3cf9ee80a350"));
        assertThat(confirmation.getAccountId(), equalTo("72970a7c-7921-431c-b95f-3438724ba16f"));
        assertThat(confirmation.getShortReference(), equalTo("PC-2436231-LYODVS"));
        assertThat(confirmation.getStatus(), equalTo("completed"));
        assertThat(confirmation.getConfirmationUrl(),  is(not(emptyString())));
        assertThat(confirmation.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(confirmation.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(confirmation.getExpiresAt(), equalTo(parseDateTime("2018-01-03T00:00:00+00:00")));
    }

    @Test
    @Betamax(tape = "can_get_payment_delivery_date", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testGetPaymentDeliveryDate() throws Exception {
        final Date paymentDate = parseDate("2019-05-29");
        final String paymentType = "regular";
        final String currency = "GBP";
        final String bankCountry = "GB";
        final PaymentDeliveryDate paymentDeliveryDate = client.getPaymentDeliveryDate(paymentDate, paymentType, currency, bankCountry);
        assertThat(paymentDeliveryDate, notNullValue());
        assertThat(paymentDeliveryDate.getPaymentDate(), equalTo(paymentDate));
        assertThat(paymentDeliveryDate.getPaymentType(), equalTo(paymentType));
        assertThat(paymentDeliveryDate.getCurrency(), equalTo(currency));
        assertThat(paymentDeliveryDate.getBankCountry(), equalTo(bankCountry));
        assertThat(paymentDeliveryDate.getPaymentDeliveryDate(), equalTo(parseDateTime("2019-05-29T00:00:00+00:00")));
        assertThat(paymentDeliveryDate.getPaymentCutoffTime(), equalTo(parseDateTime("2019-05-29T14:30:00+00:00")));
    }

    @Test
    @Betamax(tape = "can_get_quote_payment_fee", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testGetQuotePaymentFee() throws Exception {

        final String authAccountId = "0534aaf2-2egg-0134-2f36-10b11cd33cfb";
        final BigDecimal feeAmount = new BigDecimal("10.00");
        final String feeCurrency = "EUR";
        final String accountId = null;
        final String paymentCurrency = "USD";
        final String paymentDestinationCountry = "US";
        final String paymentType = "regular";
        final String chargeType = null;

        final QuotePaymentFee quotePaymentFee = client.getQuotePaymentFee(accountId, paymentCurrency, paymentDestinationCountry, paymentType, chargeType);
        assertThat(quotePaymentFee, notNullValue());
        assertThat(quotePaymentFee.getAccountId(), equalTo(authAccountId));
        assertThat(quotePaymentFee.getChargeType(), nullValue());
        assertThat(quotePaymentFee.getFeeAmount(), equalTo(feeAmount));
        assertThat(quotePaymentFee.getFeeCurrency(), equalTo(feeCurrency));
        assertThat(quotePaymentFee.getPaymentCurrency(), equalTo(paymentCurrency));
        assertThat(quotePaymentFee.getPaymentDestinationCountry(), equalTo(paymentDestinationCountry));
        assertThat(quotePaymentFee.getPaymentType(), equalTo(paymentType));
    }
}
