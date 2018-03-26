package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Iban;
import com.currencycloud.client.model.Ibans;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class IbansTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "8af871a5e8007f5073caceaf75d0bc72");
    }

    @Test
    @Betamax(tape = "can_create", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCreate() throws Exception {
        Iban iban = Iban.create();
        iban.setCurrency("JPY");
        iban = client.createIban(iban);

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
    public void testCanFind() throws Exception {
        Ibans ibansData = client.retrieveIbans(null, null);
        List<Iban> ibans = ibansData.getIbans();

        assertThat(ibans, not(nullValue()));
        assertThat(ibans.size(), is(3));
        assertThat(ibans.toString(), containsString("currency='EUR'"));
        assertThat(ibans.toString(), containsString("id='8242d1f4-4555-4155-a9bf-30feee785121'"));
        assertThat(ibans.toString(), containsString("ibanCode='GB51TCCL00997961584807'"));
        assertThat(ibans.toString(), containsString("accountId='e277c9f9-679f-454f-8367-274b3ff977ff'"));
        assertThat(ibans.toString(), containsString("accountHolderName='Development CM'"));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieve() throws Exception {
        Ibans ibansData = client.retrieveIbans("GBP", null);
        List<Iban> ibans = ibansData.getIbans();

        assertThat(ibans, not(nullValue()));
        assertThat(ibans.size(), is(1));
        assertThat(ibans.toString(), containsString("currency='GBP'"));
        assertThat(ibans.toString(), containsString("id='8242d1f4-4555-4155-a9bf-30feee785121'"));
        assertThat(ibans.toString(), containsString("ibanCode='GB51TCCL00997961584807'"));
        assertThat(ibans.toString(), containsString("accountId='e277c9f9-679f-454f-8367-274b3ff977ff'"));
        assertThat(ibans.toString(), containsString("accountHolderName='Development CM'"));
    }

    @Test
    @Betamax(tape = "can_find_subaccounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindSubAccounts() throws Exception {
        Ibans ibansData = client.findSubAccountsIbans(null, null);
        List<Iban> ibans = ibansData.getIbans();

        assertThat(ibans, not(nullValue()));
        assertThat(ibans.size(), is(1));
        assertThat(ibans.toString(), containsString("currency='EUR'"));
        assertThat(ibans.toString(), containsString("id='01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe'"));
        assertThat(ibans.toString(), containsString("ibanCode='GB51TCCL00997997989489'"));
        assertThat(ibans.toString(), containsString("accountId='87077161-91de-012f-e284-1e0030c7f352'"));
        assertThat(ibans.toString(), containsString("accountHolderName='Account-IGGLNHYTWFKI'"));
    }

    @Test
    @Betamax(tape = "can_retrieve_subaccounts", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSubAccount() throws Exception {
        Ibans ibansData = client.retrieveSubAccountsIban("87077161-91de-012f-e284-1e0030c7f353", null);
        List<Iban> ibans = ibansData.getIbans();

        assertThat(ibans, not(nullValue()));
        assertThat(ibans.size(), is(1));
        assertThat(ibans.toString(), containsString("currency='JPY'"));
        assertThat(ibans.toString(), containsString("id='01d8c0bc-7f0c-4cdd-bc7e-ef81f68500fe'"));
        assertThat(ibans.toString(), containsString("ibanCode='GB51TCCL00997997989490'"));
        assertThat(ibans.toString(), containsString("accountId='87077161-91de-012f-e284-1e0030c7f353'"));
        assertThat(ibans.toString(), containsString("accountHolderName='Account-IGGLNHYTWFKI'"));
    }
}
