package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class FundingTest extends BetamaxTestSupport {
    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "acad59188ce6ddb54d4043bc4efb5f57");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_find_funding_account", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindFundingAccount() throws Exception {
        final FundingAccounts accountData = client.findFundingAccounts("GBP", null, null, null);
        final List<FundingAccount> accounts = accountData.getFundingAccounts();
        final Pagination pagination = accountData.getPagination();
        assertThat(accounts, notNullValue());
        assertThat(accounts, not(empty()));
        FundingAccount account = accounts.iterator().next();
        assertThat(account.getId(), equalTo("b7981972-8e29-485b-8a4a-9643fc6ae3sa"));
        assertThat(account.getAccountId(), equalTo("8d98bdc8-e8e3-47dc-bd08-3dd0f4f7ea7b"));
        assertThat(account.getAccountNumber(), equalTo("012345678"));
        assertThat(account.getAccountNumberType(), equalTo("account_number"));
        assertThat(account.getAccountHolderName(), equalTo("Jon Doe"));
        assertThat(account.getBankName(), equalTo("Starling"));
        assertThat(account.getBankAddress(), equalTo("3rd floor, 2 Finsbury Avenue, London, EC2M 2PP, GB"));
        assertThat(account.getBankCountry(), equalTo("UK"));
        assertThat(account.getCurrency(), equalTo("GBP"));
        assertThat(account.getPaymentType(), equalTo("regular"));
        assertThat(account.getRegularRoutingCode(), equalTo("010203"));
        assertThat(account.getRegularRoutingCodeType(), equalTo("sort_code"));
        assertThat(account.getPriorityRoutingCode(), is(emptyOrNullString()));
        assertThat(account.getPriorityRoutingCodeType(), is(emptyOrNullString()));
        assertThat(account.getCreatedAt(), equalTo(parseDateTime("2018-05-14T14:18:30+00:00")));
        assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2018-05-14T14:19:30+00:00")));
        assertThat(pagination.getTotalEntries(), equalTo(1));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(5));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.desc));
    }
}