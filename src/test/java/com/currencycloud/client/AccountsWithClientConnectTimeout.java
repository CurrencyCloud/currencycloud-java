package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.BetamaxTestSupportClientConnectTimeout;
import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.exception.UnexpectedException;
import com.currencycloud.client.model.Account;
import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import si.mazi.rescu.AwareException;

import static org.hamcrest.Matchers.*;

public class AccountsWithClientConnectTimeout extends BetamaxTestSupportClientConnectTimeout {

    private CurrencyCloudClient client;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "acad59188ce6ddb54d4043bc4efb5f57");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_create", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCreateAccount() throws Exception {
        expectedException.expect(UnexpectedException.class);
        expectedException.expectCause(allOf(
                instanceOf(AwareException.class),
                hasProperty("message", containsString("java.net.SocketTimeoutException: connect timed out"))));

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
        account.setTermsAndConditionsAccepted(true);
        client.createAccount(account);
    }
}
