package com.currencycloud.client;

import com.currencycloud.client.exception.UnexpectedException;
import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.AccountComplianceSettings;
import org.hamcrest.junit.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import si.mazi.rescu.AwareException;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;

public class AccountsWithClientReadTimeout extends TestSupportClientReadTimeout {

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
    public void testCanCreateAccount() {
        expectedException.expect(UnexpectedException.class);
        expectedException.expectCause(allOf(
                instanceOf(AwareException.class),
                hasProperty("message", containsString("java.net.SocketTimeoutException: Read timed out"))));

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

    @Test
    public void testCanCreateAccountWithEnhancedData() {
        expectedException.expect(UnexpectedException.class);
        expectedException.expectCause(allOf(
                instanceOf(AwareException.class),
                hasProperty("message", containsString("java.net.SocketTimeoutException: Read timed out"))));

        Account request = Account.create("Acme Ltd", "company", "12 Steward St", "London", "E1 6FQ", "GB");
        request.setBrand("currencycloud");
        request.setYourReference("POS-UID-23523");
        request.setStatus("enabled");
        request.setStateOrProvince("City of London");
        request.setSpreadTable("no_markup");
        request.setApiTrading(true);
        request.setOnlineTrading(true);
        request.setPhoneTrading(true);
        request.setIdentificationType("passport");
        request.setIdentificationValue("AE02315508BF");
        request.setTermsAndConditionsAccepted(true);
        request.setLegalEntitySubType("limited_company");
        AccountComplianceSettings accountComplianceSettings = new AccountComplianceSettings();
        accountComplianceSettings.setIndustryType("technology");
        accountComplianceSettings.setCustomerRisk("LOW");
        client.createAccount(request, accountComplianceSettings);
    }
}
