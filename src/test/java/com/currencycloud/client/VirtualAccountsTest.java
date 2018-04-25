package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.VirtualAccount;
import com.currencycloud.client.model.VirtualAccounts;
import com.currencycloud.client.model.Pagination;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveVirtualAccount() throws Exception {
        Pagination paginationCondition = new Pagination();
        paginationCondition.setOrder("created_at");
        paginationCondition.setOrderAscDesc(Pagination.SortOrder.desc);
        VirtualAccounts virtualAccountsData = client.findVirtualAccounts(null, paginationCondition);
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
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.desc));
    }

    @Test
    @Betamax(tape = "can_find_subaccounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindSubAccountsVirtualAccount() throws Exception {
        VirtualAccount virtualAccountCondition = VirtualAccount.create();
        Pagination paginationCondition = new Pagination();
        VirtualAccounts virtualAccountsData = client.findSubAccountsVirtualAccounts(virtualAccountCondition, paginationCondition);
        List<VirtualAccount> virtualAccounts = virtualAccountsData.getVirtualAccounts();
        JSONObject virtualAccountJSON = (JSONObject) new JSONParser(JSONParser.MODE_RFC4627).parse(virtualAccounts.iterator().next().toString());

        assertThat(virtualAccounts, not(empty()));
        assertThat(virtualAccounts.size(), is(1));
        assertThat(virtualAccountJSON.get("id"), equalTo("00d272ee-fae5-4f97-b425-993a2d6e3a46"));
        assertThat(virtualAccountJSON.get("accountId"), equalTo("2090939e-b2f7-3f2b-1363-4d235b3f58af"));
        assertThat(virtualAccountJSON.get("virtualAccountNumber"), equalTo("8303723297"));
        assertThat(virtualAccountJSON.get("accountHolderName"), equalTo("Account-ZXOANNAMKPRQ"));
        assertThat(virtualAccountJSON.get("bankInstitutionName"), equalTo("Community Federal Savings Bank"));
        assertThat(virtualAccountJSON.get("bankInstitutionAddress"), equalTo("Seventh Avenue, New York, NY 10019, US"));
        assertThat(virtualAccountJSON.get("bankInstitutionCountry"), equalTo("United States"));

    }

    @Test
    @Betamax(tape = "can_retrieve_subaccounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSubAccountVirtualAccount() throws Exception {
        VirtualAccounts virtualAccountsData = client.retrieveSubAccountsVirtualAccount("87077161-91de-012f-e284-1e0030c7f353", null);
        List<VirtualAccount> virtualAccounts = virtualAccountsData.getVirtualAccounts();
        JSONObject virtualAccountJSON = (JSONObject) new JSONParser(JSONParser.MODE_RFC4627).parse(virtualAccounts.iterator().next().toString());

        assertThat(virtualAccounts, not(empty()));
        assertThat(virtualAccounts.size(), is(1));
        assertThat(virtualAccountJSON.get("id"), equalTo("00d272ee-fae5-4f97-b425-993a2d6e3a46"));
        assertThat(virtualAccountJSON.get("accountId"), equalTo("2090939e-b2f7-3f2b-1363-4d235b3f58af"));
        assertThat(virtualAccountJSON.get("virtualAccountNumber"), equalTo("8303723297"));
        assertThat(virtualAccountJSON.get("accountHolderName"), equalTo("Account-ZXOANNAMKPRQ"));
        assertThat(virtualAccountJSON.get("bankInstitutionName"), equalTo("Community Federal Savings Bank"));
        assertThat(virtualAccountJSON.get("bankInstitutionAddress"), equalTo("Seventh Avenue, New York, NY 10019, US"));
        assertThat(virtualAccountJSON.get("bankInstitutionCountry"), equalTo("United States"));
    }
}
