package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TransactionsTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "334cbfdb9ba9bfb6ffd499b0c6af6b12");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFind() throws Exception {
        Transactions transactionsData = client.findTransactions(null, null);
        List<Transaction> transactions = transactionsData.getTransactions();
        Transaction transaction = transactions.iterator().next();
        Pagination pagination = transactionsData.getPagination();

        assertThat(transactions, notNullValue());
        assertThat(transactions.size(), Matchers.equalTo(pagination.getTotalEntries()));
        assertThat(transactions.get(0).getId(), Matchers.equalTo("85280ea5-ba77-414b-af1f-18283d4f140c"));
        assertThat(transactions.get(1).getId(), Matchers.equalTo("1d6b5786-b080-4571-87c3-5b053e519387"));
        assertThat(transactions.get(2).getId(), Matchers.equalTo("70a479eb-a301-43d0-a241-5a9652e24079"));
        assertThat(transaction.getId(), Matchers.equalTo("85280ea5-ba77-414b-af1f-18283d4f140c"));
        assertThat(transaction.getBalanceId(), Matchers.equalTo("209ca23c-6ac3-442f-a666-aacd98b33c8b"));
        assertThat(transaction.getAccountId(), Matchers.equalTo("72970a7c-7921-431c-b95f-3438724ba16f"));
        assertThat(transaction.getCurrency(), Matchers.equalTo("USD"));
        assertThat(transaction.getAmount(), Matchers.equalTo(new BigDecimal("100000.00")));
        assertThat(transaction.getBalanceAmount(), Matchers.equalTo(new BigDecimal("100000.00")));
        assertThat(transaction.getType(), equalTo("credit"));
        assertThat(transaction.getRelatedEntityType(), equalTo("inbound_funds"));
        assertThat(transaction.getRelatedEntityId(), equalTo("85ab4e71-5a94-48eb-8ce6-9b829e102195"));
        assertThat(transaction.getRelatedEntityShortReference(), is(emptyOrNullString()));
        assertThat(transaction.getStatus(), equalTo("completed"));
        assertThat(transaction.getReason(), is(emptyOrNullString()));
        assertThat(transaction.getSettlesAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getCompletedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getAction(), equalTo("funding"));
        assertThat(pagination.getPerPage(), Matchers.equalTo(25));
        assertThat(pagination.getOrder(), Matchers.equalTo("default"));
        assertThat(pagination.getTotalEntries(), Matchers.equalTo(3));
        assertThat(pagination.getCurrentPage(), Matchers.equalTo(1));
        assertThat(pagination.getNextPage(), Matchers.equalTo(-1));
        assertThat(pagination.getPreviousPage(), Matchers.equalTo(-1));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieve() throws Exception {
        Transaction transaction = client.retrieveTransaction("85280ea5-ba77-414b-af1f-18283d4f140c");

        assertThat(transaction.getId(), equalTo("85280ea5-ba77-414b-af1f-18283d4f140c"));
        assertThat(transaction.getBalanceId(), equalTo("209ca23c-6ac3-442f-a666-aacd98b33c8b"));
        assertThat(transaction.getAccountId(), equalTo("72970a7c-7921-431c-b95f-3438724ba16f"));
        assertThat(transaction.getCurrency(), equalTo("USD"));
        assertThat(transaction.getAmount(), equalTo(new BigDecimal("100000.00")));
        assertThat(transaction.getBalanceAmount(), equalTo(new BigDecimal("100000.00")));
        assertThat(transaction.getType(), equalTo("credit"));
        assertThat(transaction.getRelatedEntityType(), equalTo("inbound_funds"));
        assertThat(transaction.getRelatedEntityId(), equalTo("85ab4e71-5a94-48eb-8ce6-9b829e102195"));
        assertThat(transaction.getRelatedEntityShortReference(), is(emptyOrNullString()));
        assertThat(transaction.getStatus(), equalTo("completed"));
        assertThat(transaction.getReason(), is(emptyOrNullString()));
        assertThat(transaction.getSettlesAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getCompletedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transaction.getAction(), equalTo("funding"));
    }

    @Test
    @Betamax(tape = "can_retrieve_sender_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveSenderDetails() throws Exception {
        SenderDetails details = client.retrieveSenderDetails("e68301d3-5b04-4c1d-8f8b-13a9b8437040");

        assertThat(details.getId(), equalTo("e68301d3-5b04-4c1d-8f8b-13a9b8437040"));
        assertThat(details.getAmount(), equalTo(new BigDecimal("1701.51")));
        assertThat(details.getCurrency(), equalTo("EUR"));
        assertThat(details.getAdditionalInformation(), equalTo("USTRD-0001"));
        assertThat(details.getValueDate(), equalTo(parseDateTime("2018-07-04T00:00:00+00:00")));
        assertThat(details.getSender(), equalTo("FR7615589290001234567890113, CMBRFR2BARK, Debtor, FR, Centre ville"));
        assertThat(details.getReceivingAccountNumber(), is(emptyOrNullString()));
        assertThat(details.getReceivingAccountIban(), equalTo("GB99OXPH94665099600083"));
        assertThat(details.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(details.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }
}
