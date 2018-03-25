package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

public class ActionsTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Test
    @Betamax(tape = "can_create_account", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCreateAccount() throws Exception {
        Account account = Account.create("Acme Ltd", "company", "12 Steward St", "London", "E1 6FQ", "GB");
        account.setBrand("currencycloud");
        account.setYourReference("POS-UID-23523");
        account.setStatus("enabled");
        account.setStateOrProvince("City of London");
        account.setSpreadTable("no_markup");
        account.setApiTrading(true);
        account.setOnlineTrading(true);
        account.setPhoneTrading(true);
        account.setIdentificationType("passport");
        account.setIdentificationValue("AE02315508BF");

        account = client.createAccount(account);

        assertThat(account.getId(), equalTo("b7de235a-ff5d-4252-83c2-06a605267fea"));
        assertThat(account.getLegalEntityType(), equalTo("company"));
        assertThat(account.getAccountName(), equalTo("Acme Ltd"));
        assertThat(account.getBrand(), equalTo("currencycloud"));
        assertThat(account.getYourReference(), equalTo("POS-UID-23523"));
        assertThat(account.getStatus(), equalTo("enabled"));
        assertThat(account.getStreet(), equalTo("12 Steward St"));
        assertThat(account.getCity(), equalTo("London"));
        assertThat(account.getStateOrProvince(), equalTo("City of London"));
        assertThat(account.getCountry(), equalTo("GB"));
        assertThat(account.getPostalCode(), equalTo("E1 6FQ"));
        assertThat(account.getSpreadTable(), equalTo("no_markup"));
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getIdentificationType(), equalTo("passport"));
        assertThat(account.getIdentificationValue(), equalTo("AE02315508BF"));
        assertThat(account.getShortReference(), equalTo("110104-00004"));
        assertThat(account.getApiTrading(), equalTo(true));
        assertThat(account.getOnlineTrading(), equalTo(true));
        assertThat(account.getPhoneTrading(), equalTo(true));
    }

    @Test
    @Betamax(tape = "can_current", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCurrent() throws Exception {
        Account account = client.currentAccount();

        assertThat(account.getId(), equalTo("8ec3a69b-02d1-4f09-9a6b-6bd54a61b3a8"));
        assertThat(account.getPostalCode(), nullValue());
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2015-04-24T15:57:55+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2015-04-24T15:57:55+00:00")));
    }

    @Test
    @Betamax(tape = "can_use_currency_to_retrieve_balance", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUseCurrencyToRetrieveBalance() throws Exception {
        Balance balance = client.retrieveBalance("GBP");

        assertThat(balance.getId(), equalTo("5a998e06-3eb7-46d6-ba58-f749864159ce"));
        assertThat(balance.getAmount(), equalTo(new BigDecimal("999866.78")));
        assertThat(balance.getCreatedAt(), equalTo(parseDateTime("2014-12-04T09:50:35+00:00")));
        assertThat(balance.getUpdatedAt(), equalTo(parseDateTime("2015-03-23T14:33:37+00:00")));
    }
}
