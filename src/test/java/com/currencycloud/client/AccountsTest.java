package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountsTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

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
        account = client.createAccount(account);

        assertThat(account, is(notNullValue()));
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
        assertThat(account.getTermsAndConditionsAccepted(), equalTo(true));
    }

    @Test
    @Betamax(tape = "can_current", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveCurrentAccount() throws Exception {
        Account account = client.currentAccount();

        assertThat(account, is(notNullValue()));
        assertThat(account.getId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(account.getAccountName(), equalTo("Development CM"));
        assertThat(account.getBrand(), equalTo("currencycloud"));
        assertThat(account.getYourReference(), is(emptyOrNullString()));
        assertThat(account.getStatus(), equalTo("enabled"));
        assertThat(account.getStreet(), equalTo("12 Steward Street"));
        assertThat(account.getCity(), equalTo("London"));
        assertThat(account.getStateOrProvince(), is(emptyOrNullString()));
        assertThat(account.getCountry(), equalTo("GB"));
        assertThat(account.getPostalCode(), equalTo("E1 6FQ"));
        assertThat(account.getSpreadTable(), equalTo("no_markup"));
        assertThat(account.getLegalEntityType(), equalTo("company"));
        assertThat(account.getIdentificationType(), equalTo("incorporation_number"));
        assertThat(account.getIdentificationValue(), equalTo("123456789"));
        assertThat(account.getShortReference(), equalTo("180213-00007"));
        assertThat(account.getApiTrading(), is(true));
        assertThat(account.getOnlineTrading(), is(true));
        assertThat(account.getPhoneTrading(), is(true));
        assertThat(account.getProcessThirdPartyFunds(), is(false));
        assertThat(account.getSettlementType(), equalTo("net"));
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));

    }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindAccount() throws Exception {
        Accounts accountData = client.findAccounts(null, null);
        List<Account> accounts = accountData.getAccounts();
        Pagination pagination = accountData.getPagination();

        assertThat(accounts, not(empty()));
        Account account = accounts.iterator().next();
        assertThat(account.getId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(account.getAccountName(), equalTo("Development CM"));
        assertThat(account.getBrand(), equalTo("currencycloud"));
        assertThat(account.getYourReference(), is(emptyOrNullString()));
        assertThat(account.getStatus(), equalTo("enabled"));
        assertThat(account.getStreet(), equalTo("12 Steward Street"));
        assertThat(account.getCity(), equalTo("London"));
        assertThat(account.getStateOrProvince(), is(emptyOrNullString()));
        assertThat(account.getCountry(), equalTo("GB"));
        assertThat(account.getPostalCode(), equalTo("E1 6FQ"));
        assertThat(account.getSpreadTable(), equalTo("no_markup"));
        assertThat(account.getLegalEntityType(), equalTo("company"));
        assertThat(account.getIdentificationType(), equalTo("incorporation_number"));
        assertThat(account.getIdentificationValue(), equalTo("123456789"));
        assertThat(account.getShortReference(), equalTo("180213-00007"));
        assertThat(account.getApiTrading(), is(true));
        assertThat(account.getOnlineTrading(), is(true));
        assertThat(account.getPhoneTrading(), is(true));
        assertThat(account.getProcessThirdPartyFunds(), is(false));
        assertThat(account.getSettlementType(), equalTo("net"));
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getBankAccountVerified(), equalTo("yes"));
        assertThat(pagination.getTotalEntries(), equalTo(1));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveAccount() throws Exception {
        Account account = client.retrieveAccount("e277c9f9-679f-454f-8367-274b3ff977ff");

        assertThat(account, is(notNullValue()));
        assertThat(account.getId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(account.getAccountName(), equalTo("Development CM"));
        assertThat(account.getBrand(), equalTo("currencycloud"));
        assertThat(account.getYourReference(), is(emptyOrNullString()));
        assertThat(account.getStatus(), equalTo("enabled"));
        assertThat(account.getStreet(), equalTo("12 Steward Street"));
        assertThat(account.getCity(), equalTo("London"));
        assertThat(account.getStateOrProvince(), is(emptyOrNullString()));
        assertThat(account.getCountry(), equalTo("GB"));
        assertThat(account.getPostalCode(), equalTo("E1 6FQ"));
        assertThat(account.getSpreadTable(), equalTo("no_markup"));
        assertThat(account.getLegalEntityType(), equalTo("company"));
        assertThat(account.getIdentificationType(), equalTo("incorporation_number"));
        assertThat(account.getIdentificationValue(), equalTo("123456789"));
        assertThat(account.getShortReference(), equalTo("180213-00007"));
        assertThat(account.getApiTrading(), is(true));
        assertThat(account.getOnlineTrading(), is(true));
        assertThat(account.getPhoneTrading(), is(true));
        assertThat(account.getProcessThirdPartyFunds(), is(false));
        assertThat(account.getSettlementType(), equalTo("net"));
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }

    @Test
    @Betamax(tape = "can_update", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUpdateAccount() throws Exception {
        Account accountCondition = Account.create();
        accountCondition.setId("e277c9f9-679f-454f-8367-274b3ff977ff");
        accountCondition.setYourReference("CCY-863032");
        accountCondition.setStateOrProvince("London");
        Account account = client.updateAccount(accountCondition);

        assertThat(account, is(notNullValue()));
        assertThat(account.getId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(account.getAccountName(), equalTo("Development CM"));
        assertThat(account.getBrand(), equalTo("currencycloud"));
        assertThat(account.getYourReference(), equalTo("CCY-863032"));
        assertThat(account.getStatus(), equalTo("enabled"));
        assertThat(account.getStreet(), equalTo("12 Steward Street"));
        assertThat(account.getCity(), equalTo("London"));
        assertThat(account.getStateOrProvince(), equalTo("London"));
        assertThat(account.getCountry(), equalTo("GB"));
        assertThat(account.getPostalCode(), equalTo("E1 6FQ"));
        assertThat(account.getSpreadTable(), equalTo("no_markup"));
        assertThat(account.getLegalEntityType(), equalTo("company"));
        assertThat(account.getIdentificationType(), equalTo("incorporation_number"));
        assertThat(account.getIdentificationValue(), equalTo("123456789"));
        assertThat(account.getShortReference(), equalTo("180213-00007"));
        assertThat(account.getApiTrading(), is(true));
        assertThat(account.getOnlineTrading(), is(true));
        assertThat(account.getPhoneTrading(), is(true));
        assertThat(account.getProcessThirdPartyFunds(), is(false));
        assertThat(account.getSettlementType(), equalTo("net"));
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }

    @Test
    @Betamax(tape = "can_retrieve_accounts_payment_charge_settings", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveAccountsPaymentChargeSettings() throws Exception {
        final AccountPaymentChargesSettings accountChargeSettings = client.retrieveAccountsPaymentChargeSettings("e277c9f9-679f-454f-8367-274b3ff977ff");
        assertThat(accountChargeSettings, is(notNullValue()));
        assertThat(accountChargeSettings.getPaymentChargesSettings(), is(notNullValue()));
        assertThat(accountChargeSettings.getPaymentChargesSettings().size(), equalTo(2));
        final AccountPaymentChargesSetting chargeSetting0 = accountChargeSettings.getPaymentChargesSettings().get(0);
        assertThat(chargeSetting0, is(notNullValue()));
        assertThat(chargeSetting0.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(chargeSetting0.getChargeSettingsId(), equalTo("090baf6d-5cfd-4bfd-9b7b-ad3f8a310995"));
        assertThat(chargeSetting0.getChargeType(), equalTo("ours"));
        assertThat(chargeSetting0.isEnabled(), is(false));
        assertThat(chargeSetting0.isDefault(), is(false));
        final AccountPaymentChargesSetting chargeSetting1 = accountChargeSettings.getPaymentChargesSettings().get(1);
        assertThat(chargeSetting1, is(notNullValue()));
        assertThat(chargeSetting1.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(chargeSetting1.getChargeSettingsId(), equalTo("87203052-24d5-4af4-b88f-3bb35de4c6ea"));
        assertThat(chargeSetting1.getChargeType(), equalTo("shared"));
        assertThat(chargeSetting1.isEnabled(), is(true));
        assertThat(chargeSetting1.isDefault(), is(true));
    }

    @Test
    @Betamax(tape = "can_update_accounts_payment_charge_settings", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUpdateAccountsPaymentChargeSettings() throws Exception {
        final AccountPaymentChargesSetting setting = new  AccountPaymentChargesSetting();
        setting.setAccountId("e277c9f9-679f-454f-8367-274b3ff977ff");
        setting.setChargeSettingsId("090baf6d-5cfd-4bfd-9b7b-ad3f8a310995");
        setting.setChargeType("ours");
        setting.setEnabled(false);
        setting.setDefault(true);
        final AccountPaymentChargesSetting updatedSetting = client.updateAccountsPaymentChargeSetting(setting);
        assertThat(updatedSetting, is(notNullValue()));
        assertThat(updatedSetting.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(updatedSetting.getChargeSettingsId(), equalTo("090baf6d-5cfd-4bfd-9b7b-ad3f8a310995"));
        assertThat(updatedSetting.getChargeType(), equalTo("ours"));
        assertThat(updatedSetting.isEnabled(), is(false));
        assertThat(updatedSetting.isDefault(), is(true));

    }

    @Test
    @Betamax(tape = "can_find_verified", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindVerifiedAccount() throws Exception {
        Account account = Account.create();
        account.setBankAccountVerified("yes");
        Pagination pagination = Pagination.builder().pages(1, 2).build();
        Accounts accountData = client.findAccounts(account, pagination);
        List<Account> accounts = accountData.getAccounts();
        Pagination paginationResponse = accountData.getPagination();
        assertThat(accounts, not(empty()));
        assertThat(accounts.size(), equalTo(2));
        assertThat(accounts.get(0).getBankAccountVerified(), equalTo("yes"));
        assertThat(accounts.get(1).getBankAccountVerified(), equalTo("yes"));
        assertThat(paginationResponse.getTotalEntries(), equalTo(17));
        assertThat(paginationResponse.getTotalPages(), equalTo(9));
        assertThat(paginationResponse.getCurrentPage(), equalTo(1));
        assertThat(paginationResponse.getPerPage(), equalTo(2));
        assertThat(paginationResponse.getPreviousPage(), equalTo(-1));
        assertThat(paginationResponse.getNextPage(), equalTo(2));
        assertThat(paginationResponse.getOrder(), equalTo("created_at"));
        assertThat(paginationResponse.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
    }
}
