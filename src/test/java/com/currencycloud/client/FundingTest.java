package com.currencycloud.client;

import com.currencycloud.client.model.FundingAccount;
import com.currencycloud.client.model.FundingAccounts;
import com.currencycloud.client.model.FundingTransaction;
import com.currencycloud.client.model.Pagination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class FundingTest extends TestSupport {
    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "acad59188ce6ddb54d4043bc4efb5f57");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testCanFindFundingAccount() {
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
        assertThat(account.getRoutingCode(), equalTo("010203"));
        assertThat(account.getRoutingCodeType(), equalTo("sort_code"));
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

    @Test
    public void testCanFindFundingAccountOnBehalfOf() {
        final String contact_id = "3b163e5d-2a6e-4f3d-aff8-e8fc161d3f00";
        client.onBehalfOfDo(contact_id, () -> {
            final FundingAccounts accountData = client.findFundingAccounts("EUR", null, null, null);
            final List<FundingAccount> accounts = accountData.getFundingAccounts();
            final Pagination pagination = accountData.getPagination();
            assertThat(accounts, notNullValue());
            assertThat(accounts, not(empty()));
            FundingAccount account = accounts.iterator().next();
            assertThat(account.getId(), equalTo("6fd009ae-a23c-4f0c-83bd-89c5037a500b"));
            assertThat(account.getAccountId(), equalTo("a124f825-ce6d-49e9-8662-b30fdb2c0493"));
            assertThat(account.getAccountNumber(), equalTo("GB10TCCL12345678901234"));
            assertThat(account.getAccountNumberType(), equalTo("iban"));
            assertThat(account.getAccountHolderName(), equalTo("Currencycloud Test"));
            assertThat(account.getBankName(), equalTo("The Currency Cloud Limited"));
            assertThat(account.getBankAddress(), equalTo("12 Steward Street, The Steward Building, London, E1 6FQ, GB"));
            assertThat(account.getBankCountry(), equalTo("GB"));
            assertThat(account.getCurrency(), equalTo("EUR"));
            assertThat(account.getPaymentType(), equalTo("priority"));
            assertThat(account.getRoutingCode(), equalTo("TCCLGB3L"));
            assertThat(account.getRoutingCodeType(), equalTo("bic_swift"));
            assertThat(account.getCreatedAt(), equalTo(parseDateTime("2020-06-25T14:15:22+00:00")));
            assertThat(account.getUpdatedAt(), equalTo(parseDateTime("2020-06-25T14:15:22+00:00")));
            assertThat(pagination.getTotalEntries(), equalTo(1));
            assertThat(pagination.getTotalPages(), equalTo(1));
            assertThat(pagination.getCurrentPage(), equalTo(1));
            assertThat(pagination.getPerPage(), equalTo(25));
            assertThat(pagination.getPreviousPage(), equalTo(-1));
            assertThat(pagination.getNextPage(), equalTo(-1));
            assertThat(pagination.getOrder(), equalTo("created_at"));
            assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
        });
    }

    @Test
    public void testCanGetFundingTransaction() {
        final FundingTransaction fundingTransaction = client.getFundingTransaction("b7981972-8e29-485b-8a4a-9643fc6ae3sa");
        FundingTransaction.SenderInformation sender = fundingTransaction.getSender();
        assertThat(fundingTransaction, notNullValue());
        assertThat(fundingTransaction.getId(), equalTo("4924919a-6c28-11ee-a3e3-63774946bad2"));
        assertThat(fundingTransaction.getAmount(), equalTo("1.11"));
        assertThat(fundingTransaction.getCurrency(), equalTo("USD"));
        assertThat(fundingTransaction.getRail(), equalTo("SEPA"));
        assertThat(fundingTransaction.getAdditionalInformation(), equalTo("ABCD20231016143117"));
        assertThat(fundingTransaction.getReceivingAccountRoutingCode(), equalTo("123456789"));
        assertThat(fundingTransaction.getValueDate(), equalTo(parseDateTime("2022-12-03T10:00:00+00:00")));
        assertThat(fundingTransaction.getReceivingAccountNumber(), equalTo("32847346"));
        assertThat(fundingTransaction.getReceivingAccountIban(), nullValue());
        assertThat(fundingTransaction.getCreatedAt(), equalTo(parseDateTime("2022-12-03T10:15:30+00:00")));
        assertThat(fundingTransaction.getUpdatedAt(), equalTo(parseDateTime("2022-12-03T10:15:30+00:00")));
        assertThat(fundingTransaction.getCompletedAt(), equalTo(parseDateTime("2022-12-03T10:15:30+00:00")));
        assertThat(sender, notNullValue());
        assertThat(sender.getAccountNumber(), equalTo("8119645406"));
        assertThat(sender.getAddress(), equalTo("Some street"));
        assertThat(sender.getBic(), nullValue());
        assertThat(sender.getCountry(), equalTo("GB"));
        assertThat(sender.getIban(), nullValue());
        assertThat(sender.getId(), equalTo("5c675fa4-fdf0-4ee6-b5bb-156b36765433"));
        assertThat(sender.getName(), equalTo("Test sender"));
        assertThat(sender.getRoutingCode(), nullValue());
    }

    @Test
    public void testCanGetFundingTransactionOnBehalfOf() {
        final String contact_id = "3b163e5d-2a6e-4f3d-aff8-e8fc161d3f00";
        client.onBehalfOfDo(contact_id, () -> {
            final FundingTransaction fundingTransaction = client.getFundingTransaction("b7981972-8e29-485b-8a4a-9643fc6ae3sb");
            FundingTransaction.SenderInformation sender = fundingTransaction.getSender();
            assertThat(fundingTransaction, notNullValue());
            assertThat(fundingTransaction.getId(), equalTo("4924919a-6c28-11ee-a3e3-63774946bad2"));
            assertThat(fundingTransaction.getAmount(), equalTo("1.11"));
            assertThat(fundingTransaction.getCurrency(), equalTo("USD"));
            assertThat(fundingTransaction.getRail(), equalTo("SEPA"));
            assertThat(fundingTransaction.getAdditionalInformation(), equalTo("ABCD20231016143117"));
            assertThat(fundingTransaction.getReceivingAccountRoutingCode(), equalTo("123456789"));
            assertThat(fundingTransaction.getValueDate(), equalTo(parseDateTime("2022-12-03T10:00:00+00:00")));
            assertThat(fundingTransaction.getReceivingAccountNumber(), equalTo("32847346"));
            assertThat(fundingTransaction.getReceivingAccountIban(), nullValue());
            assertThat(fundingTransaction.getCreatedAt(), equalTo(parseDateTime("2022-12-03T10:15:30+00:00")));
            assertThat(fundingTransaction.getUpdatedAt(), equalTo(parseDateTime("2022-12-03T10:15:30+00:00")));
            assertThat(fundingTransaction.getCompletedAt(), equalTo(parseDateTime("2022-12-03T10:15:30+00:00")));
            assertThat(sender, notNullValue());
            assertThat(sender.getAccountNumber(), equalTo("8119645406"));
            assertThat(sender.getAddress(), equalTo("Some street"));
            assertThat(sender.getBic(), nullValue());
            assertThat(sender.getCountry(), equalTo("GB"));
            assertThat(sender.getIban(), nullValue());
            assertThat(sender.getId(), equalTo("5c675fa4-fdf0-4ee6-b5bb-156b36765433"));
            assertThat(sender.getName(), equalTo("Test sender"));
            assertThat(sender.getRoutingCode(), nullValue());
        });
    }
}