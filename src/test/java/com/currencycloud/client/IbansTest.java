package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Iban;
import com.currencycloud.client.model.Ibans;
import com.currencycloud.client.model.Pagination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class IbansTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "8af871a5e8007f5073caceaf75d0bc72");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindIban() throws Exception {
        Ibans ibansData = client.findIbans(null, null);
        List<Iban> ibans = ibansData.getIbans();
        Pagination pagination = ibansData.getPagination();

        System.out.println("IbansData: " + ibansData.toString());

        Iban iban = ibans.iterator().next();
        assertThat(ibans.size(), is(3));
        assertThat(iban, is(notNullValue()));
        assertThat(iban.getId(), equalTo("8242d1f4-4555-4155-a9bf-30feee785121"));
        assertThat(iban.getCurrency(), equalTo("EUR"));
        assertThat(iban.getIbanCode(), equalTo("GB51TCCL00997961584807"));
        assertThat(iban.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(iban.getAccountHolderName(), equalTo("Development CM"));
        assertThat(pagination.getTotalEntries(), equalTo(3));
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
    public void testCanRetrieveIban() throws Exception {
        Iban ibanCondition = Iban.create();
        ibanCondition.setCurrency("GBP");
        Ibans ibansData = client.findIbans(ibanCondition, null);
        List<Iban> ibans = ibansData.getIbans();
        Pagination pagination = ibansData.getPagination();

        Iban iban = ibans.iterator().next();
        assertThat(ibans.size(), is(1));
        assertThat(iban, is(notNullValue()));
        assertThat(iban.getId(), equalTo("8242d1f4-4555-4155-a9bf-30feee785121"));
        assertThat(iban.getCurrency(), equalTo("GBP"));
        assertThat(iban.getIbanCode(), equalTo("GB51TCCL00997961584807"));
        assertThat(iban.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(iban.getAccountHolderName(), equalTo("Development CM"));
        assertThat(pagination.getTotalEntries(), equalTo(1));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getOrder(), equalTo("created_at"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
    }
}
