package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Pagination;
import com.currencycloud.client.model.VirtualAccount;
import com.currencycloud.client.model.VirtualAccounts;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class VirtualAccountsTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "47abdf69f25ea4e8f57a7a7b54fbc42e");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindVirtualAccount() throws Exception {
        VirtualAccounts virtualAccountsData = client.findVirtualAccounts(null, null);
        List<VirtualAccount> virtualAccounts = virtualAccountsData.getVirtualAccounts();
        Pagination pagination = virtualAccountsData.getPagination();
        VirtualAccount virtualAccount = virtualAccounts.iterator().next();

        assertThat(virtualAccounts.size(), is(1));
        assertThat(virtualAccount, is(notNullValue()));
        assertThat(virtualAccount.getId(), equalTo("00d272ee-fae5-4f97-b425-993a2d6e3a46"));
        assertThat(virtualAccount.getVirtualAccountNumber(), equalTo("8303723297"));
        assertThat(virtualAccount.getAccountId(), equalTo("2090939e-b2f7-3f2b-1363-4d235b3f58af"));
        assertThat(virtualAccount.getAccountHolderName(), equalTo("Account-ZXOANNAMKPRQ"));
        assertThat(virtualAccount.getBankInstitutionName(), equalTo("Community Federal Savings Bank"));
        assertThat(virtualAccount.getBankInstitutionAddress(), equalTo("Seventh Avenue, New York, NY 10019, US"));
        assertThat(virtualAccount.getBankInstitutionCountry(), equalTo("United States"));
        assertThat(pagination.getTotalEntries(), equalTo(1));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(2));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
    }
}
