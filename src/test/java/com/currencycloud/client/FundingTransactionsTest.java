package com.currencycloud.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.currencycloud.client.model.FundingTransaction;
import com.currencycloud.client.model.FundingTransactionSender;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FundingTransactionsTest extends TestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "334cbfdb9ba9bfb6ffd499b0c6af6b12");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testCanGetFundingTransaction() throws Exception {
        FundingTransaction fundingTransaction = client.getFundingTransaction("4924919a-6c28-11ee-a3e3-63774946bad2");
        FundingTransactionSender fundingTransactionSender = fundingTransaction.getSender();

        assertThat(fundingTransaction.getId(), equalTo("4924919a-6c28-11ee-a3e3-63774946bad2"));
        assertThat(fundingTransaction.getAmount(), equalTo(new BigDecimal("1.11")));
        assertThat(fundingTransaction.getCurrency(), equalTo("USD"));
        assertThat(fundingTransaction.getRail(), equalTo("SEPA"));
        assertThat(fundingTransaction.getAdditionalInformation(), equalTo("CFST20231016143117"));
        assertThat(fundingTransaction.getReceivingAccountRoutingCode(), is(emptyOrNullString()));
        assertThat(fundingTransaction.getValueDate(), equalTo(parseDateTime("2023-10-16T00:00:00+00:00")));
        assertThat(fundingTransaction.getReceivingAccountNumber(), equalTo("8302996933"));
        assertThat(fundingTransaction.getReceivingAccountIban(), is(emptyOrNullString()));
        assertThat(fundingTransaction.getCreatedAt(), equalTo(parseDateTime("2023-10-16T13:31:18+00:00")));
        assertThat(fundingTransaction.getUpdatedAt(), equalTo(parseDateTime("2023-10-16T13:31:18+00:00")));

        assertThat(fundingTransactionSender.getSenderId(),  equalTo("5c675fa4-fdf0-4ee6-b5bb-156b36765433"));
        assertThat(fundingTransactionSender.getSenderAddress(),  equalTo("test"));
        assertThat(fundingTransactionSender.getSenderCountry(),  equalTo("GB"));
        assertThat(fundingTransactionSender.getSenderName(),  equalTo("test"));
        assertThat(fundingTransactionSender.getSenderBic(),  is(emptyOrNullString()));
        assertThat(fundingTransactionSender.getSenderIban(),  is(emptyOrNullString()));
        assertThat(fundingTransactionSender.getSenderAccountNumber(),  is(emptyOrNullString()));
        assertThat(fundingTransactionSender.getSenderRoutingCode(),  is(emptyOrNullString()));
    }
}
