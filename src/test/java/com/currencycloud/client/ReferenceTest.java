package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ReferenceTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_retrieve_beneficiary_required_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveBeneficiaryRequiredDetails() throws Exception {
        List<Map<String, String>> details = client.beneficiaryRequiredDetails("GBP", "GB", "GB");
        assertThat(details, not(empty()));

        Map<String, String> detail = details.iterator().next();
        assertThat(detail.get("payment_type"), equalTo("priority"));
        assertThat(detail.get("payment_type"), equalTo("priority"));
        assertThat(detail.get("beneficiary_entity_type"), equalTo("individual"));
        assertThat(detail.get("beneficiary_address"), equalTo("^.{1,255}"));
        assertThat(detail.get("beneficiary_city"), equalTo("^.{1,255}"));
        assertThat(detail.get("beneficiary_country"), equalTo("^[A-z]{2}$"));
        assertThat(detail.get("beneficiary_first_name"), equalTo("^.{1,255}"));
        assertThat(detail.get("beneficiary_last_name"), equalTo("^.{1,255}"));
        assertThat(detail.get("acct_number"), equalTo("^[0-9A-Z]{1,50}$"));
        assertThat(detail.get("sort_code"), equalTo("^\\d{6}$"));
    }

    @Test
    @Betamax(tape = "can_retrieve_conversion_dates", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveConversionDates() throws Exception {
        ConversionDates dates = client.conversionDates("GBPUSD", null);

        assertThat(dates.getInvalidConversionDates(), not(anEmptyMap()));
        Date invalidConversionDate = dates.getInvalidConversionDates().keySet().iterator().next();
        assertThat(invalidConversionDate, equalTo(parseDate("2015-05-02")));
        assertThat(dates.getInvalidConversionDates().get(invalidConversionDate), equalTo("No trading on Saturday"));
        assertThat(dates.getFirstConversionDate(), equalTo(parseDate("2015-04-30")));
        assertThat(dates.getDefaultConversionDate(), equalTo(parseDate("2015-04-30")));
    }
    
    @Test
    @Betamax(tape = "can_retrieve_payment_dates", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrievePaymentDates() throws Exception {
        PaymentDates dates = client.paymentDates("GBP", null);

        assertThat(dates.getInvalidPaymentDates(), not(anEmptyMap()));
        Date invalidPaymentDate = dates.getInvalidPaymentDates().keySet().iterator().next();
        assertThat(invalidPaymentDate, equalTo(parseDate("2017-01-14")));
        assertThat(dates.getInvalidPaymentDates().get(invalidPaymentDate), equalTo("No trading on Saturday"));
        assertThat(dates.getFirstPaymentDate(), equalTo(parseDate("2017-01-16")));
    }

    @Test
    @Betamax(tape = "can_retrieve_currencies", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveCurrencies() throws Exception {
        List<Currency> currencies = client.currencies();
        assertThat(currencies, not(empty()));

        Currency currency = currencies.iterator().next();
        assertThat(currency.getCode(), equalTo("AED"));
        assertThat(currency.getName(), equalTo("United Arab Emirates Dirham"));
        assertThat(currency.getDecimalPlaces(), equalTo(2));
    }

    @Test
    @Betamax(tape = "can_retrieve_settlement_accounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSettlementAccounts() throws Exception {
        List<SettlementAccount> settlementAccounts = client.settlementAccounts("GBP", null);
        assertThat(settlementAccounts, not(empty()));

        SettlementAccount settlementAccount = settlementAccounts.iterator().next();
        assertThat(settlementAccount.getBankAccountHolderName(), equalTo("The Currency Cloud GBP - Client Seg A/C"));
        assertThat(settlementAccount.getBankAddress(), empty());
    }

    @Test
    @Betamax(tape = "can_retrieve_payer_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrievePayerDetails() throws Exception {
        List<PayerRequiredDetail> requiredDetails = client.payerRequiredDetails("GB", null, null);
        assertThat(requiredDetails, not(empty()));
        assertThat(requiredDetails.size(), equalTo(4));

        PayerRequiredDetail payerDetail = requiredDetails.iterator().next();
        assertThat(payerDetail.getPayerEntityType(), equalTo("company"));
        assertThat(payerDetail.getPaymentType(), equalTo("priority"));
        assertThat(payerDetail.getRequiredFields().size(), equalTo(5));
        assertThat(payerDetail.getPayerIdentificationType(), equalTo("incorporation_number"));
    }
}
