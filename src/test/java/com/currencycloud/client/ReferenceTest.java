package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.ConversionDates;
import com.currencycloud.client.model.Currency;
import com.currencycloud.client.model.SettlementAccount;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ReferenceTest extends BetamaxTestSupport {

    @Override protected String getAuthToken() { return "1c9da5726314246acfb09ec5651307a5"; }

    @Test
    @Betamax(tape = "can_retrieve_beneficiary_required_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveBeneficiaryRequiredDetails() throws Exception {
        List<Map<String, String>> details = client.getBeneficiaryRequiredDetails("GBP", "GB", "GB");
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
        ConversionDates dates = client.getConversionDates("GBPUSD", null);

        assertThat(dates.getInvalidConversionDates(), not(anEmptyMap()));
        Date invalidConversionDate = dates.getInvalidConversionDates().keySet().iterator().next();
        assertThat(invalidConversionDate, equalTo(parseDate("2015-05-02")));
        assertThat(dates.getInvalidConversionDates().get(invalidConversionDate), equalTo("No trading on Saturday"));
        assertThat(dates.getFirstConversionDate(), equalTo(parseDate("2015-04-30")));
        assertThat(dates.getDefaultConversionDate(), equalTo(parseDate("2015-04-30")));
    }

    @Test
    @Betamax(tape = "can_retrieve_currencies", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveCurrencies() throws Exception {
        List<Currency> currencies = client.getCurrencies();
        assertThat(currencies, not(empty()));

        Currency currency = currencies.iterator().next();
        assertThat(currency.getCode(), equalTo("AED"));
        assertThat(currency.getName(), equalTo("United Arab Emirates Dirham"));
        assertThat(currency.getDecimalPlaces(), equalTo(2));
    }

    @Test
    @Betamax(tape = "can_retrieve_settlement_accounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSettlementAccounts() throws Exception {
        List<SettlementAccount> settlementAccounts = client.getSettlementAccounts("GBP");
        assertThat(settlementAccounts, not(empty()));

        SettlementAccount settlementAccount = settlementAccounts.iterator().next();
        assertThat(settlementAccount.getBankAccountHolderName(), equalTo("The Currency Cloud GBP - Client Seg A/C"));
        assertThat(settlementAccount.getBankAddress(), empty());
    }
}
