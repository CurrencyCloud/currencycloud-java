package com.currencycloud.client;

import com.currencycloud.client.model.Pagination;
import com.currencycloud.client.model.WithdrawalAccount;
import com.currencycloud.client.model.WithdrawalAccountFunds;
import com.currencycloud.client.model.WithdrawalAccounts;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class WithdrawalAccountsTest extends TestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "47abdf69f25ea4e8f57a7a7b54fbc42e");
    }

    @Before
    @After
    public void methodName() {
        log.debug("------------------------- " + name.getMethodName() + " -------------------------");
    }

    @Test
    public void testCanFindWithdrawalAccount() {
        WithdrawalAccounts withdrawalAccounts = client.findWithdrawalAccounts("72970a7c-7921-431c-b95f-3438724ba16f", null);
        List<WithdrawalAccount> accounts = withdrawalAccounts.getWithdrawalAccounts();
        Pagination pagination = withdrawalAccounts.getPagination();

        assertThat(accounts.size(), is(1));
        WithdrawalAccount account = accounts.get(0);

        assertThat(account, is(notNullValue()));
        assertThat(account.getId(), equalTo("0886ac00-6ab6-41a6-b0e1-8d3faf2e0de2"));
        assertThat(account.getAccountName(), equalTo("currencycloud"));
        assertThat(account.getAccountHolderName(), equalTo("The Currency Cloud"));
        assertThat(account.getAccountHolderDOB(), CoreMatchers.is(nullValue(Date.class)));
        assertThat(account.getRoutingCode(), equalTo("123456789"));
        assertThat(account.getAccountNumber(), equalTo("01234567890"));
        assertThat(account.getCurrency(), equalTo("USD"));
        assertThat(account.getAccountId(), equalTo("72970a7c-7921-431c-b95f-3438724ba16f"));
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
    public void testCanFindWithdrawalAccount2() {
        WithdrawalAccounts withdrawalAccounts = client.findWithdrawalAccounts(null, null);
        List<WithdrawalAccount> accounts = withdrawalAccounts.getWithdrawalAccounts();
        Pagination pagination = withdrawalAccounts.getPagination();

        assertThat(accounts.size(), is(2));

        WithdrawalAccount account1 = accounts.get(0);
        assertThat(account1, is(notNullValue()));
        assertThat(account1.getId(), equalTo("0886ac00-6ab6-41a6-b0e1-8d3faf2e0de2"));
        assertThat(account1.getAccountName(), equalTo("currencycloud"));
        assertThat(account1.getAccountHolderName(), equalTo("The Currency Cloud"));
        assertThat(account1.getAccountHolderDOB(), CoreMatchers.is(nullValue(Date.class)));
        assertThat(account1.getRoutingCode(), equalTo("123456789"));
        assertThat(account1.getAccountNumber(), equalTo("01234567890"));
        assertThat(account1.getCurrency(), equalTo("USD"));
        assertThat(account1.getAccountId(), equalTo("72970a7c-7921-431c-b95f-3438724ba16f"));

        WithdrawalAccount account2 = accounts.get(1);
        assertThat(account2, is(notNullValue()));
        assertThat(account2.getId(), equalTo("0886ac00-6ab6-41a6-b0e1-8d3faf2e0de3"));
        assertThat(account2.getAccountName(), equalTo("currencycloud2"));
        assertThat(account2.getAccountHolderName(), equalTo("The Currency Cloud 2"));
        assertThat(account2.getAccountHolderDOB(), equalTo(parseDate("1990-07-20")));
        assertThat(account2.getRoutingCode(), equalTo("223456789"));
        assertThat(account2.getAccountNumber(), equalTo("01234567892"));
        assertThat(account2.getCurrency(), equalTo("GBP"));
        assertThat(account2.getAccountId(), equalTo("72970a7c-7921-431c-b95f-3438724ba16e"));

        assertThat(pagination.getTotalEntries(), equalTo(2));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
    }

    @Test
    public void testCanPullFunds() {
        WithdrawalAccountFunds funds = client.withdrawalAccountsPullFunds("0886ac00-6ab6-41a6-b0e1-8d3faf2e0de2",
                "PullFunds1", new BigDecimal(100.0));
        assertThat(funds.getId(), equalTo("e2e6b7aa-c9e8-4625-96a6-b97d4baab758"));
        assertThat(funds.getWithdrawalAccountId(), equalTo("0886ac00-6ab6-41a6-b0e1-8d3faf2e0de2"));
        assertThat(funds.getAmount(), equalTo(new BigDecimal("100.00")));
        assertThat(funds.getReference(), equalTo("PullFunds1"));
        assertThat(funds.getCreatedAt(), equalTo(parseDateTime("2020-06-29T08:02:31+00:00")));
    }
}