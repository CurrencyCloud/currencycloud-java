package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.BeneficiariesData;
import com.currencycloud.client.model.Beneficiary;
import com.currencycloud.client.model.Pagination;
import org.junit.Test;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public class ActionsTest extends BetamaxTestSupport {

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    // todo: serialize collections correctly in method body
    @Test
    @Betamax(tape = "can_validate_beneficiaries", match = {MatchRule.method, MatchRule.uri/*, MatchRule.body*/})
    public void testCanValidateBeneficiaries() throws Exception {
        client.setAuthToken("4df5b3e5882a412f148dcd08fa4e5b73");
        List<String> paymentTypes = Collections.singletonList("regular");
        Beneficiary beneficiary = client.validateBeneficiary(
                "GB", "GBP", null, "12345678", "sort_code", "123456",
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                paymentTypes
        );
        Assert.assertEquals(beneficiary.getPaymentTypes(), paymentTypes);
        Assert.assertEquals(beneficiary.getBankCountry(), "GB");
        Assert.assertEquals(beneficiary.getBankName(), "HSBC BANK PLC");
        Assert.assertEquals(beneficiary.getCurrency(), "GBP");
        Assert.assertEquals(beneficiary.getAccountNumber(), "12345678");
        Assert.assertEquals(beneficiary.getRoutingCodeType1(), "sort_code");
        Assert.assertEquals(beneficiary.getBeneficiaryAddress(), Collections.emptyList());
        Assert.assertEquals(beneficiary.getRoutingCodeValue1(), "123456");
        Assert.assertEquals(beneficiary.getBankAddress(), Arrays.asList("5 Wimbledon Hill Rd", "Wimbledon", "London"));
        Assert.assertNull(beneficiary.getBankAccountType());
    }

    // todo: serialize collections correctly in method body
    @Test
    @Betamax(tape = "can_create", match = {MatchRule.method, MatchRule.uri/*, MatchRule.body*/})
    public void testCanCreate() throws Exception {
        Beneficiary beneficiary = client.createBeneficiary(
                "Test User", "GB", "GBP", "Test User", null, null, null, "12345678", "sort_code", "123456",
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                Collections.singletonList("regular")
        );

        assertThat(beneficiary.getId(), equalTo("081596c9-02de-483e-9f2a-4cf55dcdf98c"));
        assertThat(beneficiary.getBankAccountHolderName(), equalTo("Test User"));
        assertThat(beneficiary.getPaymentTypes(), hasItem("regular"));
        assertThat(beneficiary.getCreatedAt(), equalTo(parseDate("2015-04-25T09:21:00+00:00")));
        assertThat(beneficiary.getUpdatedAt(), equalTo(parseDate("2015-04-25T09:21:00+00:00")));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieve() throws Exception {
        Beneficiary beneficiary = client.retrieveBeneficiary("081596c9-02de-483e-9f2a-4cf55dcdf98c");

        assertThat(beneficiary.getId(), equalTo("081596c9-02de-483e-9f2a-4cf55dcdf98c"));
        assertThat(beneficiary.getBankAccountHolderName(), equalTo("Test User"));
        assertThat(beneficiary.getPaymentTypes(), hasItem("regular"));
        assertThat(beneficiary.getCreatedAt(), equalTo(parseDate("2015-04-25T09:21:00+00:00")));
        assertThat(beneficiary.getUpdatedAt(), equalTo(parseDate("2015-04-25T09:21:00+00:00")));
    }

    @Test
    @Betamax(tape = "can_first", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFirst() throws Exception {
        Beneficiary beneficiary = client.firstBeneficiary(
                "Test User", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null);

        assertThat(beneficiary.getId(), equalTo("081596c9-02de-483e-9f2a-4cf55dcdf98c"));
        assertThat(beneficiary.getBankAccountHolderName(), equalTo("Test User"));
        assertThat(beneficiary.getPaymentTypes(), hasItem("regular"));
        assertThat(beneficiary.getCreatedAt(), equalTo(parseDate("2015-04-25T09:21:00+00:00")));
        assertThat(beneficiary.getUpdatedAt(), equalTo(parseDate("2015-04-25T10:58:21+00:00")));
    }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFind() throws Exception {
        BeneficiariesData beneficiariesData = client.findBeneficiaries();

        List<Beneficiary> beneficiaries = beneficiariesData.getBeneficiaries();
        assertThat(beneficiaries, not(empty()));
        assertThat(beneficiaries.size(), is(1));

        Pagination pagination = beneficiariesData.getPagination();
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
    @Betamax(tape = "can_update", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUpdate() throws Exception {
        Beneficiary beneficiary = client.updateBeneficiary(
                "081596c9-02de-483e-9f2a-4cf55dcdf98c", "Test User 2",
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null
        );

        assertThat(beneficiary.getId(), equalTo("081596c9-02de-483e-9f2a-4cf55dcdf98c"));
        assertThat(beneficiary.getBankAccountHolderName(), equalTo("Test User 2"));
    }

    private Date parseDate(String str) {
        try {
            return timeFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse time: " + str);
        }
    }
}