package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Balance;
import com.currencycloud.client.model.Balances;
import com.currencycloud.client.model.Pagination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BalancesTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testRetrieveBalance() throws Exception {
        Balance balance = client.retrieveBalance("GBP");

        assertThat(balance, is(notNullValue()));
        assertThat(balance.getId(), equalTo("5a998e06-3eb7-46d6-ba58-f749864159ce"));
        assertThat(balance.getAmount(), equalTo(new BigDecimal("999866.78")));
        assertThat(balance.getCreatedAt(), equalTo(parseDateTime("2014-12-04T09:50:35+00:00")));
        assertThat(balance.getUpdatedAt(), equalTo(parseDateTime("2015-03-23T14:33:37+00:00")));
    }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindBalance() throws Exception {
        Balance balanceCondition = Balance.create();
        balanceCondition.setAmountFrom(new BigDecimal("0.00"));
        balanceCondition.setAmountTo(new BigDecimal("9999999.99"));
        balanceCondition.setScope("own");
        Pagination paginationCondition = new Pagination();
        paginationCondition.setPerPage(5);
        paginationCondition.setOrder("amount");
        paginationCondition.setOrderAscDesc(Pagination.SortOrder.desc);
        Balances balancesData = client.findBalances(balanceCondition, paginationCondition);
        List<Balance> balances = balancesData.getBalances();
        Pagination pagination = balancesData.getPagination();

        Balance balance = balances.iterator().next();
        assertThat(balances, not(empty()));
        assertThat(balance.getId(), equalTo("26dcd57b-598d-4c5c-9347-781145d512b0"));
        assertThat(balance.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(balance.getCurrency(), equalTo("EUR"));
        assertThat(balance.getAmount(), equalTo(new BigDecimal("1120002.76")));
        assertThat(balance.getCreatedAt(), equalTo(parseDateTime("2018-02-13T10:53:29+00:00")));
        assertThat(balance.getUpdatedAt(), equalTo(parseDateTime("2018-02-21T18:35:12+00:00")));
        assertThat(pagination.getTotalEntries(), equalTo(4));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(5));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getOrder(), equalTo("amount"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.desc));
    }
}
