package com.currencycloud.client;

import com.currencycloud.client.model.Pagination;
import com.currencycloud.client.model.Payment;
import com.currencycloud.client.model.PaymentAuthorisation;
import com.currencycloud.client.model.PaymentAuthorisations;
import com.currencycloud.client.model.PaymentConfirmation;
import com.currencycloud.client.model.PaymentDeliveryDate;
import com.currencycloud.client.model.PaymentFee;
import com.currencycloud.client.model.PaymentFeeAssignment;
import com.currencycloud.client.model.PaymentFeeUnassignment;
import com.currencycloud.client.model.PaymentFees;
import com.currencycloud.client.model.PaymentSubmissionInfo;
import com.currencycloud.client.model.PaymentTrackingInfo;
import com.currencycloud.client.model.PaymentValidationResult;
import com.currencycloud.client.model.Payments;
import com.currencycloud.client.model.QuotePaymentFee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class PaymentsTest extends TestSupport {

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
    public void testCanCreate() {
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
        assertThat(payment.getInvoiceDate(), is(nullValue()));
        assertThat(payment.getInvoiceNumber(), is(nullValue()));
    }

    @Test
    public void testCanValidate() {
        Payment payment = Payment.create();
        payment.setCurrency("EUR");
        payment.setBeneficiaryId("60fbe8d3-f7d0-4124-9077-93d09fb2186a");
        payment.setAmount(new BigDecimal("788.44"));
        payment.setReason("Invoice");
        payment.setReference("REF-INV-1838");
        payment.setUniqueRequestId("a20bc586-b7a9-4316-9daf-d4ede4c0d3df");

        PaymentValidationResult validationResult = client.validatePayment(payment, null, null);

        assertThat(validationResult, notNullValue());
        assertThat(validationResult.getValidationResult(), equalTo("success"));
        assertThat(validationResult.isSCARequired(), equalTo(false));
        assertThat(validationResult.getScaId(), is(nullValue()));
        assertThat(validationResult.getScaType(), is(nullValue()));
    }

    @Test
    public void testCanValidateSca() {
        Payment payment = Payment.create();
        payment.setCurrency("EUR");
        payment.setBeneficiaryId("60fbe8d3-f7d0-4124-9077-93d09fb2186b");
        payment.setAmount(new BigDecimal("788.44"));
        payment.setReason("Invoice");
        payment.setReference("REF-INV-1838");
        payment.setUniqueRequestId("a20bc586-b7a9-4316-9daf-d4ede4c0d3dg");

        PaymentValidationResult validationResult = client.validatePayment(payment, null, null, true);

        assertThat(validationResult, notNullValue());
        assertThat(validationResult.getValidationResult(), equalTo("success"));
        assertThat(validationResult.isSCARequired(), equalTo(true));
        assertThat(validationResult.getScaId(), equalTo("ac1a5dd0-3978-013e-20dd-0affeb419f25"));
        assertThat(validationResult.getScaType(), equalTo("sms"));
    }

    @Test
    public void testCanValidateScaToAuthenticatedUser() {
        Payment payment = Payment.create();
        payment.setCurrency("EUR");
        payment.setBeneficiaryId("60fbe8d3-f7d0-4124-9077-93d09fb2186c");
        payment.setAmount(new BigDecimal("788.44"));
        payment.setReason("Invoice");
        payment.setReference("REF-INV-1838");
        payment.setUniqueRequestId("a20bc586-b7a9-4316-9daf-d4ede4c0d3dg");

        PaymentValidationResult validationResult = client.validatePayment(payment, null, null, true, true);

        assertThat(validationResult, notNullValue());
        assertThat(validationResult.getValidationResult(), equalTo("success"));
        assertThat(validationResult.isSCARequired(), equalTo(true));
        assertThat(validationResult.getScaId(), equalTo("ac1a5dd0-3978-013e-20dd-0affeb419f25"));
        assertThat(validationResult.getScaType(), equalTo("sms"));
    }

    @Test
    public void testCanCreateSca() {
        Payment payment = Payment.create();
        payment.setCurrency("EUR");
        payment.setBeneficiaryId("60fbe8d3-f7d0-4124-9077-93d09fb2186b");
        payment.setAmount(new BigDecimal("788.44"));
        payment.setReason("Invoice");
        payment.setReference("REF-INV-1838");
        payment.setUniqueRequestId("a20bc586-b7a9-4316-9daf-d4ede4c0d4eg");

        payment = client.createPayment(payment, null, true, "ac1a5dd0-3978-013e-20dd-0affeb419f25", "0123456");

        assertThat(payment, notNullValue());
        assertThat(payment.getId(), equalTo("778d2ba2-b2ec-4b39-b54c-0c3410525c97"));
    }

    @Test
    public void testCanUpdate() {
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
        assertThat(payment.getInvoiceDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getInvoiceNumber(), equalTo("35813"));
    }

    @Test
    public void testCanFind() {
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
        assertThat(payment.getInvoiceDate(), is(nullValue()));
        assertThat(payment.getInvoiceNumber(), is(nullValue()));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getTotalEntries(), equalTo(3));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
    }

    @Test
    public void testCanRetrieve() {
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
        assertThat(payment.getInvoiceDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getInvoiceNumber(), equalTo("35813"));

    }

    @Test
    public void testCanDelete() {
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
        assertThat(payment.getInvoiceDate(), equalTo(parseDate("2018-01-01")));
        assertThat(payment.getInvoiceNumber(), equalTo("35813"));
    }

    @Test
    public void testCanRetrieveNewSubmissionMT103() {
        PaymentSubmissionInfo submission = client.retrievePaymentSubmissionInfo("01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe");

        assertThat("MXGGYAGJULIIQKDV", equalTo(submission.getSubmissionRef()));
        assertThat(submission.getMessage().startsWith("{1:F01TCCLGB20AXXX0090000004}"), equalTo(true));
        assertThat(submission.getFormat(), equalTo("MT103"));
        assertThat(submission.getStatus(), equalTo("pending"));
    }

    @Test
    public void testCanRetrieveNewSubmissionPACS008() {
        PaymentSubmissionInfo submission = client.retrievePaymentSubmissionInfo("bea7b94c-e4c8-4629-b01f-9e6630264356");

        assertThat("GFYQQJHFWIUTPHFN", equalTo(submission.getSubmissionRef()));
        assertThat(submission.getMessage().startsWith("<?xml version="), equalTo(true));
        assertThat(submission.getFormat(), equalTo("PACS008"));
        assertThat(submission.getStatus(), equalTo("pending"));
    }


    @Test
    public void testCanAuthorisePayments() {
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
    public void testCanRetrieveConfirmation() {
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
    public void testGetPaymentDeliveryDate() {
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
    public void testGetPaymentFees() {
        final PaymentFees paymentFees = client.getPaymentFees(null);

        assertThat(paymentFees, notNullValue());
        assertThat(paymentFees.getPaymentFees(), notNullValue());
        assertThat(paymentFees.getPaymentFees().size(), equalTo(4));
        PaymentFee paymentFee = paymentFees.getPaymentFees().get(0);
        assertThat(paymentFee.getId(), equalTo("e7e1b6e5-c596-4ad1-b8d4-a7035185143a"));
        assertThat(paymentFee.getName(), equalTo("Fee Table CAD  5 - 10 - 15"));
        assertThat(paymentFee.getCurrency(), equalTo("CAD"));
        assertThat(paymentFee.getRegularAmount(), equalTo(new BigDecimal("5.00")));
        assertThat(paymentFee.getPrioritySharedAmount(), equalTo(new BigDecimal("10.00")));
        assertThat(paymentFee.getPriorityOursAmount(), equalTo(new BigDecimal("15.00")));
        assertThat(paymentFee.getOwnerAccountId(), equalTo("092ad4ee-6eb7-11eb-9439-0242ac130002"));
    }

    @Test
    public void testGetQuotePaymentFee() {

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

    @Test
    public void testGetPaymentTrackingInfo() {
        final PaymentTrackingInfo paymentTrackingInfo = client.getPaymentTrackingInfo("46ed4827-7b6f-4491-a06f-b548d5a7512d");
        assertThat(paymentTrackingInfo, notNullValue());
        assertThat(paymentTrackingInfo.getUetr(), equalTo("46ed4827-7b6f-4491-a06f-b548d5a7512d"));
        assertThat(paymentTrackingInfo.getTransactionStatus(), notNullValue());
        assertThat(paymentTrackingInfo.getTransactionStatus().getStatus(),equalTo("processing"));
        assertThat(paymentTrackingInfo.getTransactionStatus().getReason(),equalTo("transferred_and_tracked"));
        assertThat(paymentTrackingInfo.getInitiationTime(), equalTo(parseDateTime("2019-07-09T13:20:30+00:00")));
        assertThat(paymentTrackingInfo.getCompletionTime(), nullValue());
        assertThat(paymentTrackingInfo.getLastUpdateTime(), equalTo(parseDateTime("2019-07-10T15:39:08+00:00")));
        assertThat(paymentTrackingInfo.getPaymentEvents().size(), equalTo(7));
        final PaymentTrackingInfo.PaymentEvent paymentEvent6 = paymentTrackingInfo.getPaymentEvents().get(5);
        final PaymentTrackingInfo.PaymentEvent paymentEvent7 = paymentTrackingInfo.getPaymentEvents().get(6);
        assertThat(paymentEvent7, notNullValue());
        assertThat(paymentEvent7.getTrackerEventType(), equalTo("customer_credit_transfer_payment"));
        assertThat(paymentEvent7.getValid(), equalTo(true));
        assertThat(paymentEvent7.getTransactionStatus(), notNullValue());
        assertThat(paymentEvent7.getTransactionStatus().getStatus(),equalTo("processing"));
        assertThat(paymentEvent7.getTransactionStatus().getReason(),equalTo("transferred_and_tracked"));
        assertThat(paymentEvent7.getFundsAvailable(), nullValue());
        assertThat(paymentEvent7.getForwardedToAgent(), nullValue());
        assertThat(paymentEvent7.getFrom(), equalTo("BANABEBBXXX"));
        assertThat(paymentEvent7.getTo(), equalTo("BANAUS33XXX"));
        assertThat(paymentEvent7.getOriginator(), equalTo("BANABEBBXXX"));
        assertThat(paymentEvent7.getSerialParties(), notNullValue());
        assertThat(paymentEvent7.getSerialParties().getDebtor(), nullValue());
        assertThat(paymentEvent7.getSerialParties().getDebtorAgent(), equalTo("GPMRCH30"));
        assertThat(paymentEvent7.getSerialParties().getIntermediaryAgent1(), nullValue());
        assertThat(paymentEvent7.getSerialParties().getInstructingReimbursementAgent(), nullValue());
        assertThat(paymentEvent7.getSerialParties().getCreditorAgent(), equalTo("GPMRQAJ0"));
        assertThat(paymentEvent7.getSerialParties().getCreditor(), nullValue());
        assertThat(paymentEvent7.getSenderAcknowledgementReceipt(), equalTo(parseDateTime("2019-07-09T13:20:30+00:00")));
        assertThat(paymentEvent7.getInstructedAmount(), notNullValue());
        assertThat(paymentEvent7.getInstructedAmount().getCurrency(), equalTo("USD"));
        assertThat(paymentEvent7.getInstructedAmount().getAmount(), equalTo(new BigDecimal("745437.57")));
        assertThat(paymentEvent7.getConfirmedAmount(), nullValue());
        assertThat(paymentEvent7.getInterbankSettlementAmount(), notNullValue());
        assertThat(paymentEvent7.getInterbankSettlementAmount().getCurrency(), equalTo("USD"));
        assertThat(paymentEvent7.getInterbankSettlementAmount().getAmount(), equalTo(new BigDecimal("745437.57")));
        assertThat(paymentEvent7.getInterbankSettlementDate(), equalTo(parseDateTime("2019-07-09T00:00:00+00:00")));
        assertThat(paymentEvent6.getChargeAmount(), nullValue());
        assertThat(paymentEvent7.getChargeAmount(), empty());
        assertThat(paymentEvent7.getChargeType(), equalTo("shared"));
        assertThat(paymentEvent7.getForeignExchangeDetails(), nullValue());
        assertThat(paymentEvent7.getLastUpdateTime(), equalTo(parseDateTime("2019-07-09T13:20:50+00:00")));
    }

    @Test
    public void testCanAssignPaymentFee() {
        final String paymentFeeId = "06337511-861d-012f-860e-24003ab3f236";
        final String accountId = "eb118dc0-862c-012f-8648-24003ab3f236";

        final PaymentFeeAssignment paymentFeeAssignment = client.assignPaymentFee(paymentFeeId, accountId);

        assertThat(paymentFeeAssignment, notNullValue());
        assertThat(paymentFeeAssignment.getId(), equalTo(paymentFeeId));
        assertThat(paymentFeeAssignment.getAccountId(), equalTo(accountId));
    }

    @Test
    public void testCanUnassignPaymentFee() {
        final String accountId = "eb118dc0-862c-012f-8648-24003ab3f236";

        final PaymentFeeUnassignment paymentFeeUnassignment = client.unassignPaymentFee(accountId);

        assertThat(paymentFeeUnassignment, notNullValue());
        assertThat(paymentFeeUnassignment.getAccountId(), equalTo(accountId));
    }

    @Test
    public void testCanRetrieveWithEstimatedArrival() {
        Payment payment = client.retrievePayment("760d606d-51ad-418a-942c-0b0c0434e432", null);

        assertThat(payment, notNullValue());
        assertThat(payment.getId(), equalTo("760d606d-51ad-418a-942c-0b0c0434e432"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("20.00")));
        assertThat(payment.getCurrency(), equalTo("GBP"));
        assertThat(payment.getEstimatedArrival(), equalTo(parseDate("2021-11-10")));
    }
}
