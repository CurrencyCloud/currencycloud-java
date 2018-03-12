package com.currencycloud.client;

import com.currencycloud.client.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeserialisationTest extends JsonTestSupport {

    @Test
    public void testContact() throws Exception {
        Contact contact = readJson(Contact.class);
        assertThat(contact.getLoginId(), equalTo("john.smith"));
        assertThat(contact.getId(), equalTo("543477161-91de-012f-e284-1e0030c7f352"));
        assertThat(contact.getYourReference(), equalTo("ACME12345"));
        assertThat(contact.getFirstName(), equalTo("John"));
        assertThat(contact.getLastName(), equalTo("Smith"));
        assertThat(contact.getAccountId(), equalTo("87077161-91de-012f-e284-1e0030c7f352"));
        assertThat(contact.getAccountName(), equalTo("Company PLC"));
        assertThat(contact.getStatus(), equalTo("enabled"));
        assertThat(contact.getPhoneNumber(), equalTo("06554 87845"));
        assertThat(contact.getMobilePhoneNumber(), equalTo("07564 534 54"));
        assertThat(contact.getLocale(), equalTo("en-US"));
        assertThat(contact.getTimezone(), equalTo("Europe/London"));
        assertThat(contact.getEmailAddress(), equalTo("john.smith@company.com"));
        assertThat(contact.getDateOfBirth(), equalTo(parseDate("1980-01-22")));
        assertThat(contact.getCreatedAt(), equalTo(parseDateTime("2014-01-12T00:00:00+00:00")));
        assertThat(contact.getUpdatedAt(), equalTo(parseDateTime("2014-01-12T00:00:00+00:00")));
    }

    @Test
    public void testPayer() throws Exception {
        Payer payer = readJson(Payer.class);
        assertThat(payer.getId(), equalTo("543477161-91de-012f-e284-1e0030c7f3123"));
        assertThat(payer.getLegalEntityType(), equalTo("company"));
        assertThat(payer.getCompanyName(), equalTo("Acme Corporation"));
        assertThat(payer.getFirstName(), equalTo(""));
        assertThat(payer.getLastName(), equalTo(""));
        assertThat(payer.getAddress(), equalTo(Arrays.asList("164 Bishopsgate", "London")));
        assertThat(payer.getCity(), equalTo("London"));
        assertThat(payer.getStateOrProvince(), equalTo(""));
        assertThat(payer.getCountry(), equalTo("GB"));
        assertThat(payer.getIdentificationType(), equalTo("incorporation_number"));
        assertThat(payer.getIdentificationValue(), equalTo("123123"));
        assertThat(payer.getPostcode(), equalTo("EC2M 4LX"));
        assertThat(payer.getDateOfBirth(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
        assertThat(payer.getCreatedAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
        assertThat(payer.getUpdatedAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
    }

    @Test
    public void testPayment() throws Exception {
        Payment payment = readJson(Payment.class);

        assertThat(payment.getId(), equalTo("543477161-91de-012f-e284-1e0030c7f3123"));
        assertThat(payment.getShortReference(), equalTo("140416-GGJBNQ001"));
        assertThat(payment.getBeneficiaryId(), equalTo("543477161-91de-012f-e284-1e0030c7f352"));
        assertThat(payment.getConversionId(), equalTo("049bab6d-fe2a-42e1-be0f-531c59f838ea"));
        assertThat(payment.getAmount(), equalTo(new BigDecimal("1250000.00")));
        assertThat(payment.getCurrency(), equalTo("GBP"));
        assertThat(payment.getStatus(), equalTo("ready_to_send"));
        assertThat(payment.getPaymentType(), equalTo("regular"));
        assertThat(payment.getReference(), equalTo("INVOICE 9876"));
        assertThat(payment.getReason(), equalTo("Salary for March"));
        assertThat(payment.getPaymentDate(), equalTo(parseDateTime("2014-01-12T00:00:00+00:00")));
        assertThat(payment.getTransferredAt(), equalTo(parseDateTime("2014-01-12T13:00:00+00:00")));
        assertThat(payment.getAuthorisationStepsRequired(), equalTo(0));
        assertThat(payment.getCreatorContactId(), equalTo("ab3477161-91de-012f-e284-1e0030c7f35c"));
        assertThat(payment.getLastUpdaterContactId(), equalTo("ab3477161-91de-012f-e284-1e0030c7f35c"));
        assertThat(payment.getFailureReason(), equalTo(""));
        assertThat(payment.getPayerId(), equalTo(""));
        assertThat(payment.getCreatedAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
        assertThat(payment.getUpdatedAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
    }

    @Test
    public void testTransactions() throws Exception {
        Transactions transactions = readJson(Transactions.class);

        assertThat(transactions.getPagination(), not(nullValue()));

        List<Transaction> txs = transactions.getTransactions();
        assertThat(txs, hasSize(1));

        Transaction tx = txs.iterator().next();

        assertThat(tx.getId(), equalTo("c5a990eb-d4d7-482f-bfb1-695261fb1e4d"));
        assertThat(tx.getBalanceId(), equalTo("c5f1f54e-d6d8-4140-8110-f5b99bbc80c3"));
        assertThat(tx.getAccountId(), equalTo("7b9757a8-eee9-4572-86e6-77f4d711eaa6"));
        assertThat(tx.getCurrency(), equalTo("USD"));
        assertThat(tx.getAmount(), equalTo(new BigDecimal("1000.00")));
        assertThat(tx.getBalanceAmount(), equalTo(new BigDecimal("2000.00")));
        assertThat(tx.getType(), equalTo("credit"));
        assertThat(tx.getAction(), equalTo("conversion"));
        assertThat(tx.getRelatedEntityType(), equalTo("conversion"));
        assertThat(tx.getRelatedEntityId(), equalTo("e93e322f-93aa-4d31-b050-449da723db0b"));
        assertThat(tx.getRelatedEntityShortReference(), equalTo("140416-GGJBNQ001"));
        assertThat(tx.getStatus(), equalTo("completed"));
        assertThat(tx.getReason(), equalTo("Reason for Transaction"));
        assertThat(tx.getSettlesAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
        assertThat(tx.getCreatedAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
        assertThat(tx.getUpdatedAt(), equalTo(parseDateTime("2014-01-12T12:24:19+00:00")));
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void testException() throws Exception {
        ResponseException ex = readJson(ResponseException.class);

        assertThat(ex.getErrorCode(), equalTo("account_create_failed"));

        Map<String, List<ErrorMessage>> messages = ex.getErrorMessages();
        assertThat(messages, aMapWithSize(3));

        List<ErrorMessage> legalEntityType = messages.get("legal_entity_type");

        assertThat(legalEntityType, hasSize(2));

        ErrorMessage range = legalEntityType.get(1);
        assertThat(range.getCode(), CoreMatchers.equalTo("legal_entity_type_not_in_range"));
        assertThat(range.getParams(), aMapWithSize(1));
        assertThat(range.getParams(), hasEntry("range", (Object)"individual, company"));
    }

    @Test
    public void testBeneficiary() throws Exception {
        Beneficiary beneficiary = readJson(Beneficiary.class);

        assertThat(beneficiary.getBeneficiaryAddress(), equalTo(Collections.singletonList("London, UK")));
        assertThat(beneficiary.getBankAddress(), equalTo(Arrays.asList("KAISERSTRASSE 16", "60261 FRANKFURT AM MAIN")));
    }

    public static <T> T readJson(Class<T> type) throws java.io.IOException {
        URL jsonUrl = DeserialisationTest.class.getResource(String.format("/json/%s.json", type.getSimpleName()));
        return new ObjectMapper().readValue(jsonUrl, type);
    }
}
