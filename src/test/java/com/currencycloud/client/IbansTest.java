package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Iban;
import com.currencycloud.client.model.Ibans;
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
    @Betamax(tape = "can_create", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCreateIban() throws Exception {
        Iban ibanCondition = Iban.create();
        ibanCondition.setCurrency("JPY");
        Iban iban = client.createIban(ibanCondition);

        assertThat(iban, is(notNullValue()));
        assertThat(iban.getId(), equalTo("01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe"));
        assertThat(iban.getIbanCode(), equalTo("GB51TCCL00997997989489"));
        assertThat(iban.getCurrency(), equalTo("JPY"));
        assertThat(iban.getAccountHolderName(), equalTo("Account-IGGLNHYTWFKI"));
        assertThat(iban.getBankInstitutionName(), equalTo("The Currency Cloud"));
        assertThat(iban.getBankInstitutionAddress(), equalTo("12 Steward Street, The Steward Building, London, E1 6FQ, GB"));
        assertThat(iban.getBankInstitutionCountry(), equalTo("United Kingdom"));
        assertThat(iban.getBicSwift(), equalTo("TCCLGB31"));
        assertThat(iban.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(iban.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }

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

    @Test
    @Betamax(tape = "can_find_subaccounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindSubAccountsIban() throws Exception {
        Iban ibanCondition = Iban.create();
        Pagination paginationCondition = new Pagination();
        Ibans ibansData = client.findSubAccountsIbans(ibanCondition, paginationCondition);
        List<Iban> ibans = ibansData.getIbans();
        JSONObject ibanJSON = (JSONObject) new JSONParser(JSONParser.MODE_RFC4627).parse(ibans.iterator().next().toString());

        assertThat(ibans, not(empty()));
        assertThat(ibans.size(), is(1));
        assertThat(ibanJSON.get("currency"), equalTo("EUR"));
        assertThat(ibanJSON.get("id"), equalTo("01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe"));
        assertThat(ibanJSON.get("ibanCode"), equalTo("GB51TCCL00997997989489"));
        assertThat(ibanJSON.get("accountId"), equalTo("87077161-91de-012f-e284-1e0030c7f352"));
        assertThat(ibanJSON.get("accountHolderName"), equalTo("Account-IGGLNHYTWFKI"));
    }

    @Test
    @Betamax(tape = "can_retrieve_subaccounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSubAccountIban() throws Exception {
        Ibans ibansData = client.retrieveSubAccountsIban("87077161-91de-012f-e284-1e0030c7f353", null);
        List<Iban> ibans = ibansData.getIbans();
        JSONObject ibanJSON = (JSONObject) new JSONParser(JSONParser.MODE_RFC4627).parse(ibans.iterator().next().toString());

        assertThat(ibans, not(empty()));
        assertThat(ibans.size(), is(1));
        assertThat(ibanJSON.getAsString("currency"), equalTo("JPY"));
        assertThat(ibanJSON.get("currency"), equalTo("JPY"));
        assertThat(ibanJSON.get("id"), equalTo("01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe"));
        assertThat(ibanJSON.get("ibanCode"), equalTo("GB51TCCL00997997989490"));
        assertThat(ibanJSON.get("accountId"), equalTo("87077161-91de-012f-e284-1e0030c7f353"));
        assertThat(ibanJSON.get("accountHolderName"), equalTo("Account-IGGLNHYTWFKI"));
    }
}
